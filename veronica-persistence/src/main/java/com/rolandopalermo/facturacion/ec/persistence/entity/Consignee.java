package com.rolandopalermo.facturacion.ec.persistence.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "consignne")
public class Consignee {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "consignne_generator")
	@SequenceGenerator(name = "consignne_generator", sequenceName = "consignne_seq", allocationSize = 50)
	@Column(name = "id", updatable = false, nullable = false)
	private long id;

	@Column
	private String consignneId;

	@Column
	private String customDocNumber;

	@Column
	private String referenceDocCod;

	@Column
	private String referenceDocNumber;

	@Column
	private String referenceDocAuthNumber;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
	@JoinColumn(name = "access_key", referencedColumnName = "accessKey")
	private Bol bol;

}