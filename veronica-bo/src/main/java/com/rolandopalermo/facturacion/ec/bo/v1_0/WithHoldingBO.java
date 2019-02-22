package com.rolandopalermo.facturacion.ec.bo.v1_0;

import static com.rolandopalermo.facturacion.ec.common.util.Constants.CREATED;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rolandopalermo.facturacion.ec.common.exception.ResourceNotFoundException;
import com.rolandopalermo.facturacion.ec.common.exception.VeronicaException;
import com.rolandopalermo.facturacion.ec.common.util.DateUtils;
import com.rolandopalermo.facturacion.ec.common.util.FileUtils;
import com.rolandopalermo.facturacion.ec.common.util.SignerUtils;
import com.rolandopalermo.facturacion.ec.dto.v1_0.withholding.RetencionDTO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.withholding.RetencionIdDTO;
import com.rolandopalermo.facturacion.ec.mapper.withholding.RetencionMapper;
import com.rolandopalermo.facturacion.ec.modelo.retencion.ComprobanteRetencion;
import com.rolandopalermo.facturacion.ec.persistence.entity.DigitalCert;
import com.rolandopalermo.facturacion.ec.persistence.entity.WithHolding;
import com.rolandopalermo.facturacion.ec.persistence.repository.DigitalCertRepository;
import com.rolandopalermo.facturacion.ec.persistence.repository.WithHoldingRepository;

@Service("withHoldingBO")
public class WithHoldingBO {
	
	@Autowired
	private RetencionMapper retencionMapper;
	
	@Autowired
	private DigitalCertRepository digitalCertRepository;
	
	@Autowired
	private WithHoldingRepository withHoldingRepository;
	
	public RetencionIdDTO createWithHolding(RetencionDTO retencionDTO) throws ResourceNotFoundException, VeronicaException {
		ComprobanteRetencion comprobanteRetencion = retencionMapper.convert(retencionDTO);
		byte[] xmlContent;
		xmlContent = FileUtils.convertirObjAXML(comprobanteRetencion);
		String rucNumber = comprobanteRetencion.getInfoTributaria().getRuc();
		List<DigitalCert> certificados = digitalCertRepository.findByOwner(rucNumber);
		if (certificados == null || certificados.isEmpty()) {
			throw new ResourceNotFoundException(
					String.format("No existe un certificado digital asociado al RUC %S", rucNumber));
		}
		byte[] signedXMLContent = SignerUtils.signXML(xmlContent, certificados.get(0).getDigitalCert(),
				certificados.get(0).getPassword());
		WithHolding withHolding = new WithHolding();
		withHolding.setAccessKey(comprobanteRetencion.getInfoTributaria().getClaveAcceso());
		withHolding.setSriVersion(comprobanteRetencion.getVersion());
		withHolding.setXmlContent(new String(signedXMLContent));
		withHolding.setSupplierId(comprobanteRetencion.getInfoTributaria().getRuc());
		withHolding.setCustomerId(comprobanteRetencion.getInfoCompRetencion().getIdentificacionSujetoRetenido());
		withHolding.setIssueDate(
				DateUtils.getFechaFromStringddMMyyyy(comprobanteRetencion.getInfoCompRetencion().getFechaEmision()));
		withHolding.setInternalStatusId(CREATED);
		withHoldingRepository.save(withHolding);
		RetencionIdDTO retencionIdDTO = new RetencionIdDTO();
		retencionIdDTO.setClaveAcceso(withHolding.getAccessKey());
		return retencionIdDTO;
	}
	
	public void deleteWithHolding(String claveAcceso) throws ResourceNotFoundException, VeronicaException {
		List<WithHolding> WithHoldings = withHoldingRepository.findByAccessKeyAndIsDeleted(claveAcceso, false);
		if (WithHoldings == null || WithHoldings.isEmpty()) {
			throw new ResourceNotFoundException(String.format("No se pudo encontrar la retención con clave de acceso %s", claveAcceso));
		}
		WithHolding withHolding = WithHoldings.get(0);
		withHolding.setDeleted(true);
		withHoldingRepository.save(withHolding);
	}
	
	public String getXML(String claveAcceso) throws ResourceNotFoundException, VeronicaException {
		List<WithHolding> WithHoldings = withHoldingRepository.findByAccessKeyAndIsDeleted(claveAcceso, false);
		if (WithHoldings == null || WithHoldings.isEmpty()) {
			throw new ResourceNotFoundException(String.format("No se pudo encontrar la retención con clave de acceso %s", claveAcceso));
		}
		WithHolding withHolding = WithHoldings.get(0);
		return withHolding.getXmlContent();
	}
	
}