package com.kratonsolution.cis.dm;

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
@Table(name="education")
public class Education extends Documents
{
	@Column(name="name")
	private String name;
	
	@Column(name="year_start")
	private Integer yearStart;
	
	@Column(name="year_end")
	private Integer yearEnd;
	
	@ManyToOne
	@JoinColumn(name="fk_employee")
	private Employee employee;

	@Enumerated(EnumType.STRING)
	@Column(name="level")
	private EducationLevel level = EducationLevel.S1;
}
