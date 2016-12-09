package com.shara.hr.svc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Strings;
import com.shara.hr.dm.Education;
import com.shara.hr.dm.EducationRepository;

@Service
@Transactional(rollbackFor=Exception.class)
public class EducationService
{
	@Autowired
	private EducationRepository repository;
	
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public Education find(String id)
	{
		if(!Strings.isNullOrEmpty(id))
			return repository.findOne(id);
		
		return null;
	}
	
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public List<Education> findAll()
	{
		return repository.findAll();
	}
	
	public void add(Education education)
	{
		repository.save(education);
	}
	
	public void edit(Education education)
	{
		repository.saveAndFlush(education);
	}
	
	public void delete(String education)
	{
		if(!Strings.isNullOrEmpty(education))
			repository.delete(education);
	}
}
