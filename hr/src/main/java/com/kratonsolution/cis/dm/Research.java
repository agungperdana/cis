package com.kratonsolution.cis.dm;

import java.math.BigDecimal;

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
@Table(name="research")
public class Research extends Documents
{
	@Column(name="title")
	private String title;
	
	@Column(name="membership")
	private String membership;
	
	@Column(name="funders")
	private FundingSources funders = FundingSources.BIAYA_SENDIRI;
	
	@Column(name="amount")
	private BigDecimal amount;
	
	@Column(name="period")
	private String period;
	
	@ManyToOne
	@JoinColumn(name="fk_employee")
	private Employee employee;
	
	public Research(){}
}
