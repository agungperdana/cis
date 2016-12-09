package com.kratonsolution.cis.dm;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="jeniskelamin")
public class Gender implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	private String id = "00000";
	
	@Column(name="lakilaki")
	private Integer male;
	
	@Column(name="perempuan")
	private Integer female;
	
	@Version
	private Long version;
	
	public Gender(){}
}
