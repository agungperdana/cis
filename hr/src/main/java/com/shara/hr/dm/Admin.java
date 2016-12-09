package com.shara.hr.dm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="admin")
public class Admin extends Employee
{
	private static final long serialVersionUID = 1L;

	@Column(name="note")
	private String note;
	
	@Column(name="email")
	private String email;
	
	@Column(name="password")
	private String password;
	
	@Transient
	private String type = "ADMINISTRATION";
	
	public Admin(){}
	
	@Override
	public String getDisplayName()
	{
		return "ADMIN";
	}
}
