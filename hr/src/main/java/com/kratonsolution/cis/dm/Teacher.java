package com.kratonsolution.cis.dm;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="teacher")
public class Teacher extends Employee
{
	private static final long serialVersionUID = 1L;

	@Column(name="nidn")
	private String nidn;
	
	@Enumerated(EnumType.STRING)
	@Column(name="tipe")
	private TeacherType type = TeacherType.DOSEN_HUKUM_EKONOMI;	
	
	@ManyToOne
	@JoinColumn(name="publikasi_terakhir")
	@NotFound(action=NotFoundAction.IGNORE)
	private Publication publication;
	
	@ManyToOne
	@JoinColumn(name="penelitian_terakhir")
	@NotFound(action=NotFoundAction.IGNORE)
	private Research research;
	
	@OneToMany(mappedBy="employee",cascade=CascadeType.ALL,orphanRemoval=true,fetch=FetchType.EAGER)
	private Set<Publication> publications = new HashSet<>();
	
	@OneToMany(mappedBy="employee",cascade=CascadeType.ALL,orphanRemoval=true,fetch=FetchType.EAGER)
	private Set<Research> researchs = new HashSet<>();
	
	@Override
	public String getDisplayName()
	{
		return "DOSEN";
	}
}
