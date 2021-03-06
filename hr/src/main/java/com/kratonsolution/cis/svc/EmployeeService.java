package com.kratonsolution.cis.svc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Strings;
import com.kratonsolution.cis.dm.Employee;
import com.kratonsolution.cis.dm.EmployeeRepository;

@Service
@Transactional(rollbackFor=Exception.class)
public class EmployeeService
{
	@Autowired
	private EmployeeRepository repository;
	
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public Employee find(String id)
	{
		if(!Strings.isNullOrEmpty(id))
			return repository.findOne(id);
		
		return null;
	}
	
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public List<Employee> findAll()
	{
		return repository.findAll();
	}
	
	public void edit(Employee admin)
	{
		repository.saveAndFlush(admin);
	}
	
	public void delete(String admin)
	{
		if(!Strings.isNullOrEmpty(admin))
			repository.delete(admin);
	}
}
