package com.kratonsolution.cis.dm;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="work_step")
public class WorkStep extends Documents
{	
	@ManyToOne
	@JoinColumn(name="fk_employee")
	private Employee employee;
	
	public WorkStep(){}
}
