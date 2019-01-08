package com.rolandopalermo.facturacion.ec.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "internal_status")
public class InternalStatus {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "internal_status_id", updatable = false, nullable = false)
	private long internalStatusId;

	@Column
	@NotEmpty
	private String description;

}