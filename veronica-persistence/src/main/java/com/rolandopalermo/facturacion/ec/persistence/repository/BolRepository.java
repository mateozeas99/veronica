package com.rolandopalermo.facturacion.ec.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rolandopalermo.facturacion.ec.persistence.entity.Bol;

@Repository
public interface BolRepository extends JpaRepository<Bol, Long> {

	public List<Bol> findByAccessKeyAndIsDeleted(String accessKey, boolean isDeleted);

}