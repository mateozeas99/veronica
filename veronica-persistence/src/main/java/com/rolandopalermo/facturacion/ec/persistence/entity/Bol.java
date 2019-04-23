package com.rolandopalermo.facturacion.ec.persistence.entity;

import java.io.Serializable;
import java.util.ArrayList;
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
public class Bol extends BaseSRIEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bol_generator")
	@SequenceGenerator(name = "bol_generator", sequenceName = "bol_seq", allocationSize = 50)
	@Column(name = "bol_id", updatable = false, nullable = false)
	private long bolId;

	@Column
	private String supplierId;

	@Column
	private String bolNumber;

	@Column
	private String shipperRuc;

	@Column
	private String registrationNumber;

	@OneToMany(mappedBy = "bol", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Consignee> consignees = new ArrayList<>();
	
	public void addConsignee(Consignee consignee) {
		consignees.add(consignee);
		consignee.setBol(this);
	}

	public void removeConsignee(Consignee consignee) {
		consignees.remove(consignee);
		consignee.setBol(null);
	}

}