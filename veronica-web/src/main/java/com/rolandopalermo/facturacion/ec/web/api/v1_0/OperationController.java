package com.rolandopalermo.facturacion.ec.web.api.v1_0;

import static com.rolandopalermo.facturacion.ec.common.util.Constantes.API_DOC_ANEXO_1;

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
import com.rolandopalermo.facturacion.ec.dto.v1_0.VeronicaResponseDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value = "/api/v1.0/operaciones")
@Api(description = "Realiza operacines generales sobre Verónica")
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

}