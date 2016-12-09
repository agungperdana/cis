package com.shara.hr.dm;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="documents")
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class Documents
{
	@Id
	protected String id = UUID.randomUUID().toString();

	@Column(name="start")
	protected Date start;
	
	@Column(name="end")
	protected Date end;
	
	@Column(name="note")
	protected String note;
	
	@Version
	protected Long version;
	
	@OneToMany(mappedBy="document",cascade=CascadeType.ALL,orphanRemoval=true,fetch=FetchType.EAGER)
	protected Set<DocumentImage> images = new HashSet<>();
	
	public Documents(){}
	
	public abstract Employee getEmployee();
}
