package com.rolandopalermo.facturacion.ec.web.api.v1_0;

import static com.rolandopalermo.facturacion.ec.common.util.Constants.API_DOC_ANEXO_1;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rolandopalermo.facturacion.ec.dto.v1_0.EmpresaDTO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.VeronicaResponseDTO;
import com.rolandopalermo.facturacion.ec.persistence.entity.Supplier;
import com.rolandopalermo.facturacion.ec.service.v1_0.GenericCRUDService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value = "/api/v1.0/empresas")
@Api(description = "Gestiona las empresas habilitadas a facturar con Verónica")
public class SupplierController {

	@Autowired
	private GenericCRUDService<Supplier, EmpresaDTO> supplierService;

	@ApiOperation(value = "Registra datos de una nueva empresa")
	@PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> insertSupplier(
			@Valid @ApiParam(value = API_DOC_ANEXO_1, required = true) @RequestBody EmpresaDTO empresaDTO) {
		VeronicaResponseDTO<Object> response = new VeronicaResponseDTO<>();
		supplierService.saveOrUpdate(empresaDTO);
		response.setSuccess(true);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@ApiOperation(value = "Retorna la información asociada a una empresa")
	@GetMapping(value = "{numeroIdentificacion}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Object> getBolsBySupplier(
			@Valid @ApiParam(value = "Número de identificación de la empresa", required = true) @PathVariable("numeroIdentificacion") String numeroIdentificacion) {
		VeronicaResponseDTO<List<EmpresaDTO>> response = new VeronicaResponseDTO<>();
		EmpresaDTO empresa = new EmpresaDTO();
		empresa.setNumeroIdentificacion(numeroIdentificacion);
		List<EmpresaDTO> listSuppliers = supplierService.findAll(empresa);
		if (!StringUtils.isEmpty(listSuppliers)) {
			response.setResult(listSuppliers);
			response.setSuccess(true);
			return (new ResponseEntity<Object>(response, HttpStatus.OK));
		} else {
			return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
		}
	}

}