package com.rolandopalermo.facturacion.ec.bo.v1_0;

import static com.rolandopalermo.facturacion.ec.common.util.Constantes.CREATED;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rolandopalermo.facturacion.ec.common.exception.ResourceNotFoundException;
import com.rolandopalermo.facturacion.ec.common.exception.VeronicaException;
import com.rolandopalermo.facturacion.ec.common.util.DateUtils;
import com.rolandopalermo.facturacion.ec.common.util.FileUtils;
import com.rolandopalermo.facturacion.ec.common.util.SignerUtils;
import com.rolandopalermo.facturacion.ec.dto.v1_0.bol.GuiaIdDTO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.bol.GuiaRemisionDTO;
import com.rolandopalermo.facturacion.ec.mapper.bol.GuiaRemisionMapper;
import com.rolandopalermo.facturacion.ec.modelo.guia.Destinatario;
import com.rolandopalermo.facturacion.ec.modelo.guia.GuiaRemision;
import com.rolandopalermo.facturacion.ec.persistence.entity.Bol;
import com.rolandopalermo.facturacion.ec.persistence.entity.Consignee;
import com.rolandopalermo.facturacion.ec.persistence.entity.DigitalCert;
import com.rolandopalermo.facturacion.ec.persistence.repository.BolRepository;
import com.rolandopalermo.facturacion.ec.persistence.repository.ConsigneeRepository;
import com.rolandopalermo.facturacion.ec.persistence.repository.DigitalCertRepository;

@Service("bolBO")
public class BolBO {
	@Autowired
	private GuiaRemisionMapper guiaMapper;

	@Autowired
	private DigitalCertRepository digitalCertRepository;

	@Autowired
	private BolRepository bolRepository;

	@Autowired
	private ConsigneeRepository consigneeRepository;

	public GuiaIdDTO createBol(GuiaRemisionDTO guiaRemisionDTO) throws ResourceNotFoundException, VeronicaException {

		GuiaRemision guia = guiaMapper.convert(guiaRemisionDTO);
		byte[] xmlContent;
		xmlContent = FileUtils.convertirObjAXML(guia);
		String rucNumber = guia.getInfoTributaria().getRuc();
		List<DigitalCert> certificados = digitalCertRepository.findByOwner(rucNumber);
		if (certificados == null || certificados.isEmpty()) {
			throw new ResourceNotFoundException(
					String.format("No existe un certificado digital asociado al RUC %S", rucNumber));
		}
		byte[] signedXMLContent = SignerUtils.signXML(xmlContent, certificados.get(0).getDigitalCert(),
				certificados.get(0).getPassword());
		Bol bol = new Bol();
		bol.setAccessKey(guia.getInfoTributaria().getClaveAcceso());
		bol.setSriVersion(guia.getVersion());
		bol.setXmlContent(new String(signedXMLContent));
		bol.setSupplierId(guia.getInfoTributaria().getRuc());
		bol.setShipperRuc(guia.getInfoGuiaRemision().getRucTransportista());
		bol.setRegistrationNumber(guia.getInfoGuiaRemision().getPlaca());
		bol.setIssueDate(DateUtils.getFechaFromStringddMMyyyy(guia.getInfoGuiaRemision().getFechaIniTransporte()));
		bol.setBolNumber(guia.getInfoTributaria().getSecuencial());
		bol.setInternalStatusId(CREATED);
		bolRepository.save(bol);
		GuiaIdDTO guiaIdDTO = new GuiaIdDTO();
		guiaIdDTO.setClaveAcceso(bol.getAccessKey());

		for (Destinatario desti : guia.getDestinatario()) {
			Consignee consignee = new Consignee();
			consignee.setConsignneId(desti.getIdentificacionDestinatario());
			consignee.setCustomDocNumber(desti.getDocAduaneroUnico());
			consignee.setReferenceDocCod(desti.getCodDocSustento());
			consignee.setReferenceDocNumber(desti.getNumDocSustento());
			consignee.setReferenceDocAuthNumber(desti.getNumAutDocSustento());
			consignee.setBol(bol);
			consigneeRepository.save(consignee);

		}

		return guiaIdDTO;

	}
}
