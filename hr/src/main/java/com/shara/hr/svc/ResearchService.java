package com.shara.hr.svc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Strings;
import com.shara.hr.dm.Research;
import com.shara.hr.dm.ResearchRepository;

@Service
@Transactional(rollbackFor=Exception.class)
public class ResearchService
{
	@Autowired
	private ResearchRepository repository;
	
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public Research find(String id)
	{
		if(!Strings.isNullOrEmpty(id))
			return repository.findOne(id);
		
		return null;
	}
	
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public List<Research> findAll()
	{
		return repository.findAll();
	}
	
	public void add(Research research)
	{
		repository.save(research);
	}
	
	public void edit(Research research)
	{
		repository.saveAndFlush(research);
	}
	
	public void delete(String research)
	{
		if(!Strings.isNullOrEmpty(research))
			repository.delete(research);
	}
}
