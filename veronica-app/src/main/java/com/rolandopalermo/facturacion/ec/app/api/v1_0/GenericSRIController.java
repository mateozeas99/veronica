package com.rolandopalermo.facturacion.ec.app.api.v1_0;

import com.rolandopalermo.facturacion.ec.dto.v1_0.ComprobanteDTO;
import com.rolandopalermo.facturacion.ec.dto.ComprobanteIdDTO;
import com.rolandopalermo.facturacion.ec.dto.VeronicaResponseDTO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.sri.RespuestaComprobanteDTO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.sri.RespuestaSolicitudDTO;
import com.rolandopalermo.facturacion.ec.modelo.Comprobante;
import com.rolandopalermo.facturacion.ec.persistence.entity.BaseSRIEntity;
import com.rolandopalermo.facturacion.ec.service.v1_0.GenericSRIService;
import org.springframework.beans.factory.annotation.Autowired;
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

import javax.validation.Valid;
import java.io.ByteArrayInputStream;

public class GenericSRIController<DTO extends ComprobanteDTO, MODEL extends Comprobante, DOMAIN extends BaseSRIEntity> {

    @Autowired(required = true)
    private GenericSRIService<DTO, MODEL, DOMAIN> service;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> create(@Valid @RequestBody DTO dto) {
        VeronicaResponseDTO<Object> response = new VeronicaResponseDTO<>();
        ComprobanteIdDTO facturaIdDTO;
        facturaIdDTO = service.create(dto);
        response.setSuccess(true);
        response.setResult(facturaIdDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping(value = "{claveAcceso}/enviar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> post(@Valid @PathVariable String claveAcceso) {
        VeronicaResponseDTO<Object> response = new VeronicaResponseDTO<>();
        RespuestaSolicitudDTO respuestaSolicitudDTO = service.post(claveAcceso);
        response.setSuccess(true);
        response.setResult(respuestaSolicitudDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(value = "{claveAcceso}/autorizar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> apply(@Valid @PathVariable String claveAcceso) {
        VeronicaResponseDTO<Object> response = new VeronicaResponseDTO<>();
        RespuestaComprobanteDTO respuestaComprobanteDTO = service.apply(claveAcceso);
        response.setSuccess(true);
        response.setResult(respuestaComprobanteDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(value = "{claveAcceso}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> delete(@Valid @PathVariable String claveAcceso) {
        VeronicaResponseDTO<Object> response = new VeronicaResponseDTO<>();
        service.delete(claveAcceso);
        response.setSuccess(true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "{claveAcceso}/archivos/pdf")
    public ResponseEntity<Object> generateRIDE(@Valid @PathVariable("claveAcceso") String claveAcceso) {
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
    }

    @GetMapping(value = "{claveAcceso}/archivos/xml", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Object> getXML(@Valid @PathVariable("claveAcceso") String claveAcceso) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/xml; charset=UTF-8");
        return (new ResponseEntity<Object>(service.getXML(claveAcceso), headers, HttpStatus.OK));
    }

}