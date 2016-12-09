package com.shara.hr.dm;

import java.util.Base64;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="document_image")
public class DocumentImage
{
	@Id
	private String id = UUID.randomUUID().toString();

	@Column(name="name")
	private String name;
	
	@Column(name="scan")
	private byte[] scan;
	
	@Version
	private Long version;

	@ManyToOne
	@JoinColumn(name="fk_document")
	private Documents document;
	
	public DocumentImage(){}
	
	public String getBase64()
	{
		return Base64.getEncoder().encodeToString(getScan());
	}
}
