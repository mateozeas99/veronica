package com.rolandopalermo.facturacion.ec.app.api.v1_0;

import com.rolandopalermo.facturacion.ec.dto.v1_0.withholding.RetencionDTO;
import com.rolandopalermo.facturacion.ec.modelo.retencion.ComprobanteRetencion;
import com.rolandopalermo.facturacion.ec.persistence.entity.Withholding;
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
import static com.rolandopalermo.facturacion.ec.common.util.Constants.URI_API_V1_WH;

@RestController
@RequestMapping(value = {URI_API_V1_WH})
@Api(description = "Gestiona el ciclo de vida de una retención")
public class WithHoldingController extends GenericSRIController<RetencionDTO, ComprobanteRetencion, Withholding> {

    @ApiOperation(value = "Crea un comprobante de retención y lo almacena en base de datos")
    public ResponseEntity<Object> createWithHolding(
            @Valid @ApiParam(value = API_DOC_ANEXO_1, required = true) @RequestBody RetencionDTO retencionDTO) {
        return super.create(retencionDTO);
    }

    @ApiOperation(value = "Envía un comprobante de retención al SRI y actualiza su estado en base de datos")
    public ResponseEntity<Object> postWithHolding(
            @Valid @ApiParam(value = "Clave de acceso del comprobante electrónico", required = true) @PathVariable String claveAcceso) {
        return super.post(claveAcceso);
    }

    @ApiOperation(value = "Autoriza un comprobante de retención y actualiza su estado en base de datos")
    public ResponseEntity<Object> applyWithHolding(
            @Valid @ApiParam(value = "Clave de acceso del comprobante electrónico", required = true) @PathVariable String claveAcceso) {
        return super.apply(claveAcceso);
    }

    @ApiOperation(value = "Elimina un comprobante de retención de la base de datos")
    public ResponseEntity<Object> deleteWithHolding(
            @Valid @ApiParam(value = "Clave de acceso del comprobante electrónico", required = true) @PathVariable String claveAcceso) {
        return super.delete(claveAcceso);
    }

    @ApiOperation(value = "Retorna la representación PDF de un comprobante de retención")
    public ResponseEntity<Object> generateRIDE(
            @Valid @ApiParam(value = "Clave de acceso del comprobante electrónico", required = true) @PathVariable("claveAcceso") String claveAcceso) {
        return super.generateRIDE(claveAcceso);
    }

    @ApiOperation(value = "Retorna la representación XML de un comprobante de retención")
    public ResponseEntity<Object> getXML(
            @Valid @ApiParam(value = "Clave de acceso del comprobante electrónico", required = true) @PathVariable("claveAcceso") String claveAcceso) {
        return super.getXML(claveAcceso);
    }

}