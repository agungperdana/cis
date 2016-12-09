package com.shara.hr.svc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Strings;
import com.shara.hr.dm.Teacher;
import com.shara.hr.dm.TeacherRepository;
import com.shara.hr.dm.TeacherType;

@Service
@Transactional(rollbackFor=Exception.class)
public class TeacherService
{
	@Autowired
	private TeacherRepository repository;
	
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public Teacher find(String id)
	{
		if(!Strings.isNullOrEmpty(id))
			return repository.findOne(id);
		
		return null;
	}
	
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public List<Teacher> findAll()
	{
		return repository.findAll();
	}
	
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public List<Teacher> findAll(String filter)
	{
		if(Strings.isNullOrEmpty(filter))
			return findAll();
		
		return repository.findAll(filter);
	}
	
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public List<Teacher> findAll(TeacherType type,String filter)
	{
		if(Strings.isNullOrEmpty(filter) && type == null)
			return findAll();
		else if(Strings.isNullOrEmpty(filter))
			return repository.findAllByType(type);
		else if(type == null)
			return findAll(filter);
		
		return repository.findAll(type,filter);
	}
	
	public void add(Teacher employee)
	{
		repository.save(employee);
	}
	
	public void edit(Teacher employee)
	{
		repository.saveAndFlush(employee);
	}
	
	public void flush()
	{
		repository.flush();
	}
	
	public void delete(String employee)
	{
		if(!Strings.isNullOrEmpty(employee))
			repository.delete(employee);
	}
}
