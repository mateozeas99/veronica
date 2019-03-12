package com.rolandopalermo.facturacion.ec.web.api.v1_0;

import static com.rolandopalermo.facturacion.ec.common.util.Constants.API_DOC_ANEXO_1;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rolandopalermo.facturacion.ec.bo.v1_0.SriBO;
import com.rolandopalermo.facturacion.ec.bo.v1_0.WithHoldingBO;
import com.rolandopalermo.facturacion.ec.common.exception.InternalServerException;
import com.rolandopalermo.facturacion.ec.common.exception.VeronicaException;
import com.rolandopalermo.facturacion.ec.dto.v1_0.VeronicaResponseDTO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.sri.RespuestaComprobanteDTO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.sri.RespuestaSolicitudDTO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.withholding.RetencionDTO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.withholding.RetencionIdDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value = "/api/v1.0/retenciones")
@Api(description = "Gestiona el ciclo de vida de una retención")
public class WithHoldingController {
	
	@Autowired
	private WithHoldingBO withHoldingBO;
	
	@Autowired
	private SriBO sriBO;
	
	private static final Logger logger = LogManager.getLogger(SriBO.class);
	
	@ApiOperation(value = "Crea un comprobante de retención y lo almacena en base de datos")
	@PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> createWithHolding(@Valid @ApiParam(value = API_DOC_ANEXO_1, required = true) @RequestBody RetencionDTO retencionDTO) {
		try {
			VeronicaResponseDTO<Object> response = new VeronicaResponseDTO<>();
			RetencionIdDTO retencionIdDTO;
			retencionIdDTO = withHoldingBO.createWithHolding(retencionDTO);
			response.setSuccess(true);
			response.setResult(retencionIdDTO);
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch (VeronicaException e) {
			logger.error("createWithHolding", e);
			throw new InternalServerException(e.getMessage());
		}
	}
	
	@ApiOperation(value = "Envía un comprobante de retención al SRI y actualiza su estado en base de datos")
	@PutMapping(value = "{claveAcceso}/enviar", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> postWithHolding(@Valid @ApiParam(value = "Clave de acceso del comprobante electrónico", required = true) @PathVariable String claveAcceso) {
		VeronicaResponseDTO<Object> response = new VeronicaResponseDTO<>();
		RespuestaSolicitudDTO respuestaSolicitudDTO = sriBO.postWithHolding(claveAcceso);
		response.setSuccess(true);
		response.setResult(respuestaSolicitudDTO);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Autoriza un comprobante de retención y actualiza su estado en base de datos")
	@PutMapping(value = "{claveAcceso}/autorizar", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> applyWithHolding(@Valid @ApiParam(value = "Clave de acceso del comprobante electrónico", required = true) @PathVariable String claveAcceso) {
		try {
			VeronicaResponseDTO<Object> response = new VeronicaResponseDTO<>();
			RespuestaComprobanteDTO respuestaComprobanteDTO = sriBO.applyWithHolding(claveAcceso);
			response.setSuccess(true);
			response.setResult(respuestaComprobanteDTO);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (VeronicaException e) {
			logger.error("applyWithHolding", e);
			throw new InternalServerException(e.getMessage());
		}
	}

	@ApiOperation(value = "Elimina un comprobante de retención de la base de datos")
	@DeleteMapping(value = "{claveAcceso}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> deleteWithHolding(@Valid @ApiParam(value = "Clave de acceso del comprobante electrónico", required = true) @PathVariable String claveAcceso) {
		try {
			VeronicaResponseDTO<Object> response = new VeronicaResponseDTO<>();
			withHoldingBO.deleteWithHolding(claveAcceso);
			response.setSuccess(true);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (VeronicaException e) {
			logger.error("deleteWithHolding", e);
			throw new InternalServerException(e.getMessage());
		}
	}
	
	@ApiOperation(value = "Retorna la representación XML de un comprobante de retención")
	@GetMapping(value = "{claveAcceso}/archivos/xml", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Object> getXML(@Valid @ApiParam(value = "Clave de acceso del comprobante electrónico", required = true) @PathVariable("claveAcceso") String claveAcceso) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.add(HttpHeaders.CONTENT_TYPE, "application/xml; charset=UTF-8");
			return  (new ResponseEntity<Object>(withHoldingBO.getXML(claveAcceso), headers, HttpStatus.OK));
		} catch (VeronicaException e) {
			logger.error("getXML", e);
			throw new InternalServerException(e.getMessage());
		}
	}
	
}