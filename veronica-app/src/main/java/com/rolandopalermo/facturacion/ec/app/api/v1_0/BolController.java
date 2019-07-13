package com.rolandopalermo.facturacion.ec.app.api.v1_0;

import com.rolandopalermo.facturacion.ec.dto.v1_0.bol.GuiaRemisionDTO;
import com.rolandopalermo.facturacion.ec.modelo.guia.GuiaRemision;
import com.rolandopalermo.facturacion.ec.persistence.entity.Bol;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.rolandopalermo.facturacion.ec.common.util.Constants.API_DOC_ANEXO_1;
import static com.rolandopalermo.facturacion.ec.common.util.Constants.URI_API_V1_BOL;

@RestController
@RequestMapping(value = {URI_API_V1_BOL})
@Api(description = "Gestiona el ciclo de vida de una guía de remisión electrónica")
public class BolController extends GenericSRIController<GuiaRemisionDTO, GuiaRemision, Bol> {

    @ApiOperation(value = "Crea una guía de remisión electrónica y la almacena en base de datos")
    public ResponseEntity<Object> createBol(
            @Valid @ApiParam(value = API_DOC_ANEXO_1, required = true) @RequestBody GuiaRemisionDTO guiaDTO) {
        return super.create(guiaDTO);
    }

    @ApiOperation(value = "Envía una guía de remisión electrónica al SRI y actualiza su estado en base de datos")
    public ResponseEntity<Object> postBillOFLanding(
            @Valid @ApiParam(value = "Clave de acceso del comprobante electrónico", required = true) @PathVariable String claveAcceso) {
        return super.post(claveAcceso);
    }

    @ApiOperation(value = "Autoriza una guía de remisión electrónica y actualiza su estado en base de datos")
    public ResponseEntity<Object> applyBillOFLanding(
            @Valid @ApiParam(value = "Clave de acceso del comprobante electrónico", required = true) @PathVariable String claveAcceso) {
        return super.apply(claveAcceso);
    }

    @ApiOperation(value = "Elimina una guía de remisión electrónica de la base de datos")
    public ResponseEntity<Object> deleteBillOFLanding(
            @Valid @ApiParam(value = "Clave de acceso del comprobante electrónico", required = true) @PathVariable String claveAcceso) {
        return super.delete(claveAcceso);
    }

    @ApiOperation(value = "Retorna la representación PDF de una guía de remisión electrónica")
    public ResponseEntity<Object> generateRIDE(
            @Valid @ApiParam(value = "Clave de acceso del comprobante electrónico", required = true) @PathVariable("claveAcceso") String claveAcceso) {
        return super.generateRIDE(claveAcceso);
    }

    @ApiOperation(value = "Retorna la representación XML de una guía de remisión electrónica")
    public ResponseEntity<Object> getXML(
            @Valid @ApiParam(value = "Clave de acceso del comprobante electrónico", required = true) @PathVariable("claveAcceso") String claveAcceso) {
        return super.getXML(claveAcceso);
    }

}