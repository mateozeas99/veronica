package com.rolandopalermo.facturacion.ec.app.api;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.rolandopalermo.facturacion.ec.common.util.Constants.URI_PUBLIC;

@RestController
@RequestMapping(value = URI_PUBLIC)
public class HealthController {

    @ApiOperation(value = "Verifica el estado de la aplicación web para CI")
    @GetMapping(value = "health", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> checkHealth() {
        return new ResponseEntity<>("", HttpStatus.OK);
    }

}