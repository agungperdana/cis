package com.kratonsolution.cis.dm;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="employee")
@Inheritance(strategy=InheritanceType.JOINED)
public class Employee implements Serializable
{
	protected static final long serialVersionUID = 1L;

	@Id
	protected String id = UUID.randomUUID().toString();

	@Column(name="nama")
	protected String nama;

	@Column(name="nip")
	protected String nip;

	@Column(name="agama")
	@Enumerated(EnumType.STRING)
	protected Religion agama = Religion.ISLAM;

	@Column(name="gender")
	@Enumerated(EnumType.STRING)
	protected GenderType gender = GenderType.PRIA;

	@Column(name="tempat_lahir")
	protected String tempatLahir;

	@Column(name="tgl_lahir")
	protected Date tglLahir;

	@Column(name="bagian")
	protected String bagian;

	@Column(name="alamat")
	protected String alamat;

	@Column(name="kontak")
	protected String kontak;

	@Version
	protected Long version;

	@ManyToOne
	@JoinColumn(name="fk_last_step")
	@NotFound(action=NotFoundAction.IGNORE)
	protected WorkStep step;

	@ManyToOne
	@JoinColumn(name="fk_last_path")
	@NotFound(action=NotFoundAction.IGNORE)
	protected CareerPath path;

	@ManyToOne
	@JoinColumn(name="fk_last_decree")
	@NotFound(action=NotFoundAction.IGNORE)
	protected Decree decree;

	@ManyToOne
	@JoinColumn(name="pendidikan_terakhir")
	@NotFound(action=NotFoundAction.IGNORE)
	protected Education education;

	@OneToMany(mappedBy="employee",cascade=CascadeType.ALL,orphanRemoval=true,fetch=FetchType.EAGER)
	@OrderBy("start ASC")
	protected Set<WorkStep> workSteps = new HashSet<>();

	@OneToMany(mappedBy="employee",cascade=CascadeType.ALL,orphanRemoval=true,fetch=FetchType.EAGER)
	@OrderBy("start ASC")
	protected Set<CareerPath> careerPaths = new HashSet<>();

	@OneToMany(mappedBy="employee",cascade=CascadeType.ALL,orphanRemoval=true,fetch=FetchType.EAGER)
	@OrderBy("start ASC")
	protected Set<Decree> decrees = new HashSet<>();

	@OneToMany(mappedBy="employee",cascade=CascadeType.ALL,orphanRemoval=true,fetch=FetchType.EAGER)
	@OrderBy("start ASC")
	protected Set<Education> educations = new HashSet<>();

	@OneToMany(mappedBy="employee",cascade=CascadeType.ALL,orphanRemoval=true,fetch=FetchType.EAGER)
	protected Set<Family> familys = new HashSet<>();

	@Transient
	private Date past = null;

	public Employee(){}

	public String getDisplayName()
	{
		return "";
	}

	public int getYearExp()
	{
		if(step == null)
			return 0;
			
		return getPeriod().getYears();
	}
	
	public int getMonthExp()
	{
		if(step == null)
			return 0;
		
		return getPeriod().getMonths();
	}

	private Period getPeriod()
	{
		past = null;

		workSteps.forEach(step ->{
			if(past == null || past.compareTo(step.getStart()) > 0)
				past = step.getStart();
		});

		Calendar cpast = Calendar.getInstance();
		if(past != null)
			cpast.setTime(past);
		
		return Period.between(past.toLocalDate(), LocalDate.now());
	}
}
