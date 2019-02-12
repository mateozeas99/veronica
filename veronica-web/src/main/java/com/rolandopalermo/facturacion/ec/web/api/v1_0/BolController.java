package com.rolandopalermo.facturacion.ec.web.api.v1_0;

import static com.rolandopalermo.facturacion.ec.common.util.Constantes.API_DOC_ANEXO_1;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rolandopalermo.facturacion.ec.bo.v1_0.BolBO;
import com.rolandopalermo.facturacion.ec.bo.v1_0.InvoiceBO;
import com.rolandopalermo.facturacion.ec.bo.v1_0.RideBO;
import com.rolandopalermo.facturacion.ec.bo.v1_0.SriBO;
import com.rolandopalermo.facturacion.ec.common.exception.InternalServerException;
import com.rolandopalermo.facturacion.ec.common.exception.VeronicaException;
import com.rolandopalermo.facturacion.ec.dto.v1_0.VeronicaResponseDTO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.bol.GuiaIdDTO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.bol.GuiaRemisionDTO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.invoice.FacturaDTO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.invoice.FacturaIdDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value = "/api/v1.0/guia-remision")
@Api(description = "Gestiona el ciclo de vida de una guia-remision electrónica")
public class BolController {
	@Autowired
	private BolBO bollBO;

	private static final Logger logger = LogManager.getLogger(SriBO.class);

	@ApiOperation(value = "Crea una guia-remision electrónica y la almacena en base de datos")
	@PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> createbol(
			@Valid @ApiParam(value = API_DOC_ANEXO_1, required = true) @RequestBody GuiaRemisionDTO guiaDTO) {
		try {
			VeronicaResponseDTO<Object> response = new VeronicaResponseDTO<>();
			GuiaIdDTO guiaIdDTO;
			guiaIdDTO = bollBO.createBol(guiaDTO);
			response.setSuccess(true);
			response.setResult(guiaIdDTO);
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch (VeronicaException e) {
			logger.error("createInvoice", e);
			throw new InternalServerException(e.getMessage());
		}
	}
}
