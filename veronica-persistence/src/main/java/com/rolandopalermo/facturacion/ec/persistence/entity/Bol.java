package com.rolandopalermo.facturacion.ec.persistence.entity;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@Table(name = "bol")
@TypeDefs(value = { @TypeDef(name = "XMLType", typeClass = XMLType.class) })
public class Bol {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bol_generator")
	@SequenceGenerator(name="bol_generator", sequenceName = "bol_seq", allocationSize=50)
	@Column(name = "bol_id", updatable = false, nullable = false)
	private long bolId;

	@Column
	private long internalStatusId;

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
	private Date issueDate;
	
	@Column
	private String bolNumber;
	
	@Column
	@Type(type = "XMLType")
	private String xmlAuthorization;
	
	@Column
	private boolean isDeleted;
	
	@Column
	private Timestamp authorizationDate;
	
	@Column
	private String shipperRuc;
	
	@Column
	private String registrationNumber;
	
	@OneToMany(mappedBy="bol",
			   cascade= {CascadeType.PERSIST, CascadeType.MERGE,
						 CascadeType.DETACH, CascadeType.REFRESH})
	private List<Consignee> consignees;
	

}