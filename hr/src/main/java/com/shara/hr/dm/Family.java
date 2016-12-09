package com.shara.hr.dm;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="family")
public class Family extends Documents
{
	@Column(name="name")
	private String name;
	
	@Column(name="type")
	@Enumerated(EnumType.STRING)
	private FamilyType type = FamilyType.ISTRI;
	
	@Column(name="number")
	private Integer number;
	
	@Column(name="birth_place")
	private String birthPlace;

	@Column(name="birth_date")
	private Date birthDate;
	
	@ManyToOne
	@JoinColumn(name="fk_employee")
	private Employee employee;
	
	public Family(){}
}
