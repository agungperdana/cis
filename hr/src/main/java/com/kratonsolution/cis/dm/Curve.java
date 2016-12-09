package com.kratonsolution.cis.dm;

import java.io.Serializable;
import java.util.UUID;

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
@Table(name="kurva")
public class Curve implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	private String id = UUID.randomUUID().toString();

	@Column(name="smu",unique=true)
	private Integer smu;
	
	@Column(name="d1",unique=true)
	private Integer d1;
	
	@Column(name="d2",unique=true)
	private Integer d2;
	
	@Column(name="d3",unique=true)
	private Integer d3;
	
	@Column(name="d4",unique=true)
	private Integer d4;
	
	@Column(name="s1",unique=true)
	private Integer s1;
	
	@Column(name="s2",unique=true)
	private Integer s2;
	
	@Column(name="s3",unique=true)
	private Integer s3;
	
	@Version
	private Long version;
	
	public Curve(){}
}
