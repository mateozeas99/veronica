package com.rolandopalermo.facturacion.ec.app.api.v1_0;

import static com.rolandopalermo.facturacion.ec.common.util.Constants.API_DOC_ANEXO_1;

import java.io.ByteArrayInputStream;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.InputStreamResource;
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

import com.rolandopalermo.facturacion.ec.common.exception.InternalServerException;
import com.rolandopalermo.facturacion.ec.common.exception.VeronicaException;
import com.rolandopalermo.facturacion.ec.dto.v1_0.ComprobanteIdDTO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.VeronicaResponseDTO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.sri.RespuestaComprobanteDTO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.sri.RespuestaSolicitudDTO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.withholding.RetencionDTO;
import com.rolandopalermo.facturacion.ec.service.v1_0.GenericSRIService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value = "/api/v1.0/retenciones")
@Api(description = "Gestiona el ciclo de vida de una retención")
public class WithHoldingController {

	@Autowired(required = true)
	@Qualifier("withHoldingServiceImpl")
	private GenericSRIService service;

	private static final Logger logger = LogManager.getLogger(WithHoldingController.class);

	@ApiOperation(value = "Crea un comprobante de retención y lo almacena en base de datos")
	@PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> createWithHolding(
			@Valid @ApiParam(value = API_DOC_ANEXO_1, required = true) @RequestBody RetencionDTO retencionDTO) {
		try {
			VeronicaResponseDTO<Object> response = new VeronicaResponseDTO<>();
			ComprobanteIdDTO retencionIdDTO;
			retencionIdDTO = service.create(retencionDTO);
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
	public ResponseEntity<Object> postWithHolding(
			@Valid @ApiParam(value = "Clave de acceso del comprobante electrónico", required = true) @PathVariable String claveAcceso) {
		VeronicaResponseDTO<Object> response = new VeronicaResponseDTO<>();
		RespuestaSolicitudDTO respuestaSolicitudDTO = service.post(claveAcceso);
		response.setSuccess(true);
		response.setResult(respuestaSolicitudDTO);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "Autoriza un comprobante de retención y actualiza su estado en base de datos")
	@PutMapping(value = "{claveAcceso}/autorizar", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> applyWithHolding(
			@Valid @ApiParam(value = "Clave de acceso del comprobante electrónico", required = true) @PathVariable String claveAcceso) {
		try {
			VeronicaResponseDTO<Object> response = new VeronicaResponseDTO<>();
			RespuestaComprobanteDTO respuestaComprobanteDTO = service.apply(claveAcceso);
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
	public ResponseEntity<Object> deleteWithHolding(
			@Valid @ApiParam(value = "Clave de acceso del comprobante electrónico", required = true) @PathVariable String claveAcceso) {
		VeronicaResponseDTO<Object> response = new VeronicaResponseDTO<>();
		service.delete(claveAcceso);
		response.setSuccess(true);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "Retorna la representación PDF de un comprobante de retención")
	@GetMapping(value = "{claveAcceso}/archivos/pdf")
	public ResponseEntity<Object> generateRIDE(
			@Valid @ApiParam(value = "Clave de acceso del comprobante electrónico", required = true) @PathVariable("claveAcceso") String claveAcceso) {
		try {
			VeronicaResponseDTO<Object> response = new VeronicaResponseDTO<>();
			byte[] pdfContent = service.getPDF(claveAcceso);
			response.setSuccess(true);
			HttpHeaders headers = new HttpHeaders();
			headers.add("content-disposition", "inline; filename=" + claveAcceso + ".pdf");
			headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
			headers.add("Pragma", "no-cache");
			headers.add("Expires", "0");
			return ResponseEntity.ok().headers(headers).contentLength(pdfContent.length)
					.contentType(MediaType.parseMediaType("application/octet-stream"))
					.body(new InputStreamResource(new ByteArrayInputStream(pdfContent)));
		} catch (VeronicaException e) {
			logger.error("generateRIDE", e);
			throw new InternalServerException(e.getMessage());
		}
	}

	@ApiOperation(value = "Retorna la representación XML de un comprobante de retención")
	@GetMapping(value = "{claveAcceso}/archivos/xml", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Object> getXML(
			@Valid @ApiParam(value = "Clave de acceso del comprobante electrónico", required = true) @PathVariable("claveAcceso") String claveAcceso) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, "application/xml; charset=UTF-8");
		return (new ResponseEntity<Object>(service.getXML(claveAcceso), headers, HttpStatus.OK));
	}

}