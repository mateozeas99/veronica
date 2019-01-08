package com.rolandopalermo.facturacion.ec.web.api.v1_0;

import static com.rolandopalermo.facturacion.ec.common.util.Constantes.API_DOC_ANEXO_1;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rolandopalermo.facturacion.ec.bo.v1_0.InvoiceBO;
import com.rolandopalermo.facturacion.ec.common.exception.InternalServerException;
import com.rolandopalermo.facturacion.ec.common.exception.VeronicaException;
import com.rolandopalermo.facturacion.ec.dto.v1_0.VeronicaResponseDTO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.invoice.FacturaDTO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.invoice.FacturaIdDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value = "/api/v1.0/facturas")
@Api(description = "")
public class InvoiceController {
	
	@Autowired
	private InvoiceBO invoiceBO;
	
	private static final Logger logger = Logger.getLogger(InvoiceController.class);
	
	@ApiOperation(value = "Crea una factura electrónica y la almacena en base de datos")
	@PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> createInvoice(@Valid @ApiParam(value = API_DOC_ANEXO_1, required = true) @RequestBody FacturaDTO facturaDTO) {
		try {
			VeronicaResponseDTO<Object> response = new VeronicaResponseDTO<>();
			FacturaIdDTO facturaIdDTO;
			facturaIdDTO = invoiceBO.createInvoice(facturaDTO);
			response.setSuccess(true);
			response.setResult(facturaIdDTO);
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch (VeronicaException e) {
			logger.error("createInvoice", e);
			throw new InternalServerException(e.getMessage());
		}
		
	}
	
}