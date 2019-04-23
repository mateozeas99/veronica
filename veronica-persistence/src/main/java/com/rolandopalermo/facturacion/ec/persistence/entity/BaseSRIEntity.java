package com.rolandopalermo.facturacion.ec.persistence.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Type;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseSRIEntity {

	@Column
	private String accessKey;

	@Column
	private String sriVersion;

	@Column
	private Date issueDate;

	@Column
	@Type(type = "XMLType")
	private String xmlContent;

	@Column
	private Timestamp authorizationDate;

	@Column
	private long internalStatusId;

	@Column
	@Type(type = "XMLType")
	private String xmlAuthorization;

	@Column
	private boolean isDeleted;

}