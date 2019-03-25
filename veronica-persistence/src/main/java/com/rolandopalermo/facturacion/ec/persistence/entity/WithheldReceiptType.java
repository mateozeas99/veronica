package com.rolandopalermo.facturacion.ec.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "withheld_receipt_type")
public class WithheldReceiptType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "withheld_receipt_type_id", updatable = false, nullable = false)
	private long withheldReceiptTypeId;

	@Column
	private String code;
	
	@Column
	private String description;

}