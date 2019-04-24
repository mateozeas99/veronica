package com.rolandopalermo.facturacion.ec.persistence.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnTransformer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "digital_cert")
public class DigitalCert {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "digital_cert_generator")
	@SequenceGenerator(name = "digital_cert_generator", sequenceName = "digital_cert_seq", allocationSize = 50)
	@Column(name = "digital_cert_id", updatable = false, nullable = false)
	private long id;

	@Column
	@ColumnTransformer(
			read = "pgp_sym_decrypt("
					+ "password::bytea, "
					+ "current_setting('encrypt.key')"
					+ ")", 
			write = "pgp_sym_encrypt("
					+ "?, "
					+ "current_setting('encrypt.key')"
					+ ")"
			)
	private String password;

	@Column
	private byte[] digitalCert;

	@Column
	private String owner;
	
	@Column
	private boolean active;
	
	@Column
	private Date insertDate;

}