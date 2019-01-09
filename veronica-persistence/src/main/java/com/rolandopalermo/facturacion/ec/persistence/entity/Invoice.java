package com.rolandopalermo.facturacion.ec.persistence.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.hibernate.validator.constraints.NotEmpty;

import com.rolandopalermo.facturacion.ec.persistence.type.XMLType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "invoice")
@TypeDefs(value = { @TypeDef(name = "XMLType", typeClass = XMLType.class) })
public class Invoice {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invoice_generator")
	@SequenceGenerator(name="invoice_generator", sequenceName = "invoice_seq", allocationSize=50)
	@Column(name = "invoice_id", updatable = false, nullable = false)
	private long invoiceId;

	@Column
	@NotEmpty
	private long internalStatusId;

	@Column
	@NotEmpty
	private String accessKey;

	@Column
	@NotEmpty
	private String sriVersion;

	@Column
	@NotEmpty
	@Type(type = "XMLType")
	private String xmlContent;

	@Column
	@NotEmpty
	private String supplierId;

	@Column
	@NotEmpty
	private String customerId;

	@Column
	@NotEmpty
	private Date issueDate;
	
	@Column
	@NotEmpty
	private String invoiceNumber;
	
	@Column
	@Type(type = "XMLType")
	private String xmlAuthorization;
	
	@Column
	private boolean isDeleted;
	
	@Column
	private Timestamp authorizationDate;

}