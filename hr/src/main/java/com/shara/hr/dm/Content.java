package com.shara.hr.dm;

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
@Table(name="konten")
public class Content implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	private String id = UUID.randomUUID().toString();

	@Column(name="beranda")
	private String beranda;
	
	@Column(name="profile")
	private String profile;
	
	@Column(name="kontak")
	private String kontak;
	
	@Version
	private Long version;
	
	public Content(){}
}
