package com.rolandopalermo.facturacion.ec.persistence.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.rolandopalermo.facturacion.ec.persistence.entity.Consignee;


public interface ConsigneeRepository extends JpaRepository<Consignee, Long> {

}