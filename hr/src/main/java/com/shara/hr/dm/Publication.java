package com.shara.hr.dm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="papper")
public class Publication extends Documents
{
	@Column(name="judul")
	private String title;
	
	@Column(name="media")
	private String publishedIn;
	
	@Column(name="tahun")
	private String period;
	
	@ManyToOne
	@JoinColumn(name="fk_employee")
	private Employee employee;
	
	public Publication(){}
}
