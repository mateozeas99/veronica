package com.rolandopalermo.facturacion.ec.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rolandopalermo.facturacion.ec.persistence.entity.DigitalCert;

public interface DigitalCertRepository extends JpaRepository<DigitalCert, Long> {

	public List<DigitalCert> findByOwnerAndPasswordAndActive(String owner, String password, boolean active);

}