package com.rolandopalermo.facturacion.ec.web.api.v1_0;

import static com.rolandopalermo.facturacion.ec.common.util.Constants.API_DOC_ANEXO_1;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rolandopalermo.facturacion.ec.bo.v1_0.OperationBO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.CertificadoDigitalDTO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.MetodoPagoDTO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.TipoDocumentoRetenidoDTO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.TipoImpuestoDTO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.VeronicaResponseDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value = "/api/v1.0/operaciones")
@Api(description = "Realiza operaciones generales sobre Verónica")
public class OperationController {

	@Autowired
	private OperationBO operationBO;
	
	@ApiOperation(value = "Almacena un certificado digital asociado a número de RUC")
	@PostMapping(value = "certificado-digital", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> insertDigitalCert(@Valid @ApiParam(value = API_DOC_ANEXO_1, required = true) @RequestBody CertificadoDigitalDTO certificadoDigital) {
		VeronicaResponseDTO<Object> response = new VeronicaResponseDTO<>();
		operationBO.saveDigitalCert(certificadoDigital);
		response.setSuccess(true);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@ApiOperation(value = "Crea un nuevo tipo de impuesto")
	@PostMapping(value = "tipo-impuesto", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> insertTaxType(@Valid @ApiParam(value = API_DOC_ANEXO_1, required = true) @RequestBody TipoImpuestoDTO tipoImpuestoDTO) {
		VeronicaResponseDTO<Object> response = new VeronicaResponseDTO<>();
		operationBO.saveTaxType(tipoImpuestoDTO);
		response.setSuccess(true);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@ApiOperation(value = "Crea un nuevo tipo de documento retenido")
	@PostMapping(value = "tipo-documento-retenido", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> insertWithheldReceiptType(@Valid @ApiParam(value = API_DOC_ANEXO_1, required = true) @RequestBody TipoDocumentoRetenidoDTO tipoDocumentoRetenidoDTO) {
		VeronicaResponseDTO<Object> response = new VeronicaResponseDTO<>();
		operationBO.saveWithheldReceiptType(tipoDocumentoRetenidoDTO);
		response.setSuccess(true);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@ApiOperation(value = "Crea un nuevo método de pago")
	@PostMapping(value = "metodo-pago", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> insertPaymentMethod(@Valid @ApiParam(value = API_DOC_ANEXO_1, required = true) @RequestBody MetodoPagoDTO metodoPagoDTO) {
		VeronicaResponseDTO<Object> response = new VeronicaResponseDTO<>();
		operationBO.savePaymentMethod(metodoPagoDTO);
		response.setSuccess(true);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

}