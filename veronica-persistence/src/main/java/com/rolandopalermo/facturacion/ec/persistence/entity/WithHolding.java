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

import com.rolandopalermo.facturacion.ec.persistence.type.XMLType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "with_holding")
@TypeDefs(value = { @TypeDef(name = "XMLType", typeClass = XMLType.class) })
public class WithHolding {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "with_holding_generator")
	@SequenceGenerator(name="with_holding_generator", sequenceName = "with_holding_seq", allocationSize=50)
	@Column(name = "with_holding_id", updatable = false, nullable = false)
	private long withHoldingId;
	
	@Column
	private String accessKey;

	@Column
	private String sriVersion;
	
	@Column
	@Type(type = "XMLType")
	private String xmlContent;
	
	@Column
	private String supplierId;

	@Column
	private String customerId;
	
	@Column
	private Date issueDate;
	
	@Column
	private long internalStatusId;
	
	@Column
	@Type(type = "XMLType")
	private String xmlAuthorization;
	
	@Column
	private boolean isDeleted;
	
	@Column
	private Timestamp authorizationDate;
	
}