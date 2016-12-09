/**
 * 
 */
package com.shara.hr.dm;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Agung Dodi Perdana
 * @email agung.dodi.perdana@gmail.com 
 */
@Getter
@Setter
@Entity
@Table(name="career_path")
public class CareerPath extends Documents
{
	@ManyToOne
	@JoinColumn(name="fk_employee")
	private Employee employee;
}
