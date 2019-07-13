package com.rolandopalermo.facturacion.ec.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.rolandopalermo.facturacion.ec.persistence.type.XMLType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "debit_memo")
@TypeDefs(value = { @TypeDef(name = "XMLType", typeClass = XMLType.class) })
public class DebitMemo extends BaseSRIEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "debit_memo_generator")
	@SequenceGenerator(name = "debit_memo_generator", sequenceName = "debit_memo_seq", allocationSize = 50)
	@Column(name = "debit_memo_id", updatable = false, nullable = false)
	private long debitMemoId;

	@Column
	private String supplierId;

	@Column
	private String customerId;

	@Column
	private String debitMemoNumber;

}