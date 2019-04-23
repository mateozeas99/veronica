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
@Table(name = "credit_memo")
@TypeDefs(value = { @TypeDef(name = "XMLType", typeClass = XMLType.class) })
public class CreditMemo extends BaseSRIEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "credit_memo_generator")
	@SequenceGenerator(name="credit_memo_generator", sequenceName = "credit_memo_seq", allocationSize=50)
	@Column(name = "credit_memo_id", updatable = false, nullable = false)
	private long creditMemoId;

	@Column
	private String supplierId;

	@Column
	private String customerId;
	
	@Column
	private String creditMemoNumber;
	
}