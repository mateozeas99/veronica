package com.rolandopalermo.facturacion.ec.service.v1_0.impl;

import static com.rolandopalermo.facturacion.ec.common.util.Constants.APPLIED;
import static com.rolandopalermo.facturacion.ec.common.util.Constants.INVALID;
import static com.rolandopalermo.facturacion.ec.common.util.Constants.POSTED;
import static com.rolandopalermo.facturacion.ec.common.util.Constants.REJECTED;
import static com.rolandopalermo.facturacion.ec.common.util.Constants.SRI_APPLIED;
import static com.rolandopalermo.facturacion.ec.common.util.Constants.SRI_REJECTED;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.rolandopalermo.facturacion.ec.common.converter.JaxbConverter;
import com.rolandopalermo.facturacion.ec.common.exception.ResourceNotFoundException;
import com.rolandopalermo.facturacion.ec.common.exception.VeronicaException;
import com.rolandopalermo.facturacion.ec.common.util.DateUtils;
import com.rolandopalermo.facturacion.ec.common.util.SignerUtils;
import com.rolandopalermo.facturacion.ec.dto.v1_0.ComprobanteDTO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.ComprobanteIdDTO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.ListaComprobantesDTO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.sri.AutorizacionDTO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.sri.RespuestaComprobanteDTO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.sri.RespuestaSolicitudDTO;
import com.rolandopalermo.facturacion.ec.mapper.Mapper;
import com.rolandopalermo.facturacion.ec.mapper.sri.RespuestaComprobanteMapper;
import com.rolandopalermo.facturacion.ec.mapper.sri.RespuestaSolicitudMapper;
import com.rolandopalermo.facturacion.ec.modelo.Comprobante;
import com.rolandopalermo.facturacion.ec.persistence.entity.BaseSRIEntity;
import com.rolandopalermo.facturacion.ec.persistence.entity.DigitalCert;
import com.rolandopalermo.facturacion.ec.persistence.repository.BaseSRIRepository;
import com.rolandopalermo.facturacion.ec.persistence.repository.DigitalCertRepository;
import com.rolandopalermo.facturacion.ec.ride.RIDEGenerator;
import com.rolandopalermo.facturacion.ec.service.v1_0.GenericSRIService;
import com.rolandopalermo.facturacion.ec.soap.client.AutorizacionComprobanteProxy;
import com.rolandopalermo.facturacion.ec.soap.client.EnvioComprobantesProxy;

import autorizacion.ws.sri.gob.ec.RespuestaComprobante;

@Service
public abstract class GenericSRIServiceImpl<DTO extends ComprobanteDTO, MODEL extends Comprobante, DOMAIN extends BaseSRIEntity>
		implements GenericSRIService<DTO, MODEL, DOMAIN> {

	@Autowired
	private DigitalCertRepository digitalCertRepository;

	@Value("${sri.wsdl.recepcion}")
	private String wsdlRecepcion;

	@Value("${sri.wsdl.autorizacion}")
	private String wsdlAutorizacion;

	@Autowired
	private RespuestaComprobanteMapper respuestaComprobanteMapper;

	@Autowired
	private RespuestaSolicitudMapper respuestaSolicitudMapper;

	@Autowired
	private RIDEGenerator rideGenerator;

	@Autowired
	private BaseSRIRepository<DOMAIN, Long> repository;
	
	@Autowired
	private Mapper<DTO, MODEL> mapper;
	
	protected JaxbConverter jaxbConverter;

	private static final Logger logger = LogManager.getLogger(GenericSRIServiceImpl.class);

	@Override
	public ComprobanteIdDTO create(DTO transportObject) throws ResourceNotFoundException, VeronicaException {
		try {
			ComprobanteIdDTO comprobanteIdDTO = new ComprobanteIdDTO();
			MODEL comprobante = mapper.convert(transportObject);
			File temp = File.createTempFile(UUID.randomUUID().toString(), ".xml");
			String filePath = temp.getAbsolutePath();
			jaxbConverter.convertFromObjectToXML(comprobante, filePath);
			Path path = Paths.get(filePath);
			byte[] xmlContent = Files.readAllBytes(path);
			if (!temp.delete()) {
				throw new VeronicaException("No se pudo eliminar el archivo temporal.");
			}
			String rucNumber = comprobante.getInfoTributaria().getRuc();
			List<DigitalCert> certificates = digitalCertRepository.findByOwner(rucNumber);
			if (certificates == null || certificates.isEmpty()) {
				throw new ResourceNotFoundException(
						String.format("No existe un certificado digital asociado al RUC %S", rucNumber));
			}
			byte[] signedXMLContent = SignerUtils.signXML(xmlContent, certificates.get(0).getDigitalCert(),
					certificates.get(0).getPassword());
			DOMAIN domainObject = toEntity(comprobante, new String(signedXMLContent));
			repository.save(domainObject);
			comprobanteIdDTO.setClaveAcceso(comprobante.getInfoTributaria().getClaveAcceso());
			return comprobanteIdDTO;
		} catch (Exception e) {
			logger.error("create", e);
			throw new VeronicaException(e.getMessage());
		}
	}

	@Override
	public RespuestaSolicitudDTO post(String accessKey) {
		List<DOMAIN> comprobantes = repository.findByAccessKeyAndIsDeleted(accessKey, false);
		if (comprobantes == null || comprobantes.isEmpty()) {
			throw new ResourceNotFoundException(
					String.format("No se pudo encontrar el comprobante con clave de acceso %s", accessKey));
		}
		DOMAIN domainObject = comprobantes.get(0);
		byte[] xmlContent = domainObject.getXmlContent().getBytes();
		if (xmlContent == null) {
			throw new ResourceNotFoundException(
					String.format("El contenido del comprobante con clave de acceso %s es nulo", accessKey));
		}
		RespuestaSolicitudDTO respuestaSolicitudDTO = postReceipt(xmlContent);
		if (respuestaSolicitudDTO.getEstado().compareTo(SRI_REJECTED) == 0) {
			domainObject.setInternalStatusId(REJECTED);
		} else {
			domainObject.setInternalStatusId(POSTED);
		}
		repository.save(domainObject);
		return respuestaSolicitudDTO;
	}

	@Override
	public RespuestaComprobanteDTO apply(String accessKey) throws ResourceNotFoundException, VeronicaException {
		MODEL modelObject;
		DOMAIN domainObject;
		RespuestaComprobanteDTO respuestaComprobanteDTO = applyReceipt(accessKey);
		AutorizacionDTO autorizacion = respuestaComprobanteDTO.getAutorizaciones().get(0);
		try {
			modelObject = (MODEL) jaxbConverter.convertFromXMLToObject(autorizacion.getComprobante());
		} catch (Exception e) {
			logger.error("apply", e);
			throw new ResourceNotFoundException(String.format(
					"No se puede procesar el comprobante con clave de acceso %s", autorizacion.getComprobante()));
		}
		Timestamp timestamp = new Timestamp(respuestaComprobanteDTO.getTimestamp());
		List<DOMAIN> receipts = repository.findByAccessKeyAndIsDeleted(accessKey, false);
		if (receipts == null || receipts.isEmpty()) {
			domainObject = toEntity(modelObject, autorizacion.getComprobante());
		} else {
			domainObject = receipts.get(0);
		}
		domainObject.setXmlAuthorization(respuestaComprobanteDTO.getContentAsXML());
		domainObject.setInternalStatusId(autorizacion.getEstado().compareTo(SRI_APPLIED) == 0 ? APPLIED : INVALID);
		domainObject.setAuthorizationDate(timestamp);
		repository.save(domainObject);
		return respuestaComprobanteDTO;
	}

	@Override
	public void delete(String accessKey) {
		List<DOMAIN> receipts = repository.findByAccessKeyAndIsDeleted(accessKey, false);
		if (receipts == null || receipts.isEmpty()) {
			throw new ResourceNotFoundException(
					String.format("No se pudo encontrar el comprobante con clave de acceso %s", accessKey));
		}
		DOMAIN domainObject = receipts.get(0);
		domainObject.setDeleted(true);
		repository.save(domainObject);
	}

	@Override
	public String getXML(String accessKey) {
		List<DOMAIN> receipts = repository.findByAccessKeyAndIsDeleted(accessKey, false);
		if (receipts == null || receipts.isEmpty()) {
			throw new ResourceNotFoundException(
					String.format("No se pudo encontrar el comprobante con clave de acceso %s", accessKey));
		}
		DOMAIN domainObject = receipts.get(0);
		return domainObject.getXmlContent();
	}

	@Override
	public byte[] getPDF(String accessKey) throws ResourceNotFoundException, VeronicaException {
		List<DOMAIN> receipts = repository.findByAccessKeyAndIsDeleted(accessKey, false);
		if (receipts == null || receipts.isEmpty()) {
			apply(accessKey);
			receipts = repository.findByAccessKeyAndIsDeleted(accessKey, false);
			if (receipts == null || receipts.isEmpty()) {
				throw new ResourceNotFoundException(
						String.format("No se pudo encontrar el comprobante con clave de acceso %s", accessKey));
			}
		}
		final DOMAIN domainObject = receipts.get(0);
		return rideGenerator.buildPDF(domainObject.getXmlContent(), accessKey,
				DateUtils.convertirTimestampToDate(domainObject.getAuthorizationDate()));
	}
	
	@Override
	public ListaComprobantesDTO findAllBySupplierId(String supplierId) {
		ListaComprobantesDTO response = new ListaComprobantesDTO();
		List<String> receipts = repository.findBySupplierIdAndIsDeleted(supplierId, false);
		List<String> lstAccessKey = Optional.ofNullable(receipts)
				.map(List::stream)
				.orElseGet(Stream::empty)
				.collect(Collectors.toList());
		response.setComprobantes(lstAccessKey);
		return response;
	}

	public abstract void setJaxbConverter(JaxbConverter jaxbConverter);
	
	public JaxbConverter getJaxbConverter() {
		return jaxbConverter;
	}

	private RespuestaSolicitudDTO postReceipt(byte[] xmlContent) {
		EnvioComprobantesProxy proxy;
		RespuestaSolicitudDTO respuestaSolicitudDTO = null;
		try {
			proxy = new EnvioComprobantesProxy(wsdlRecepcion);
			respuestaSolicitudDTO = respuestaSolicitudMapper.convert(proxy.enviarComprobante(xmlContent));
		} catch (MalformedURLException e) {
			logger.error("enviarComprobante", e);
			throw new ResourceNotFoundException(
					String.format("El archivo WSDL en la ruta %s no está disponible", wsdlRecepcion));
		}
		return respuestaSolicitudDTO;
	}

	private RespuestaComprobanteDTO applyReceipt(String claveAcceso)
			throws ResourceNotFoundException, VeronicaException {
		AutorizacionComprobanteProxy proxy;
		try {
			proxy = new AutorizacionComprobanteProxy(wsdlAutorizacion);
		} catch (MalformedURLException e) {
			logger.error("autorizarComprobante", e);
			throw new ResourceNotFoundException(
					String.format("El archivo WSDL en la ruta %s no está disponible", wsdlAutorizacion));
		}
		RespuestaComprobante respuestaComprobante = proxy.autorizacionIndividual(claveAcceso);
		if (respuestaComprobante == null || respuestaComprobante.getAutorizaciones() == null
				|| respuestaComprobante.getAutorizaciones().getAutorizacion() == null
				|| respuestaComprobante.getAutorizaciones().getAutorizacion().isEmpty()) {
			throw new ResourceNotFoundException(
					String.format("No se puede autorizar el comprobante con clave de acceso %s", claveAcceso));
		}
		RespuestaComprobanteDTO respuestaComprobanteDTO = respuestaComprobanteMapper.convert(respuestaComprobante);
		return respuestaComprobanteDTO;
	}

}