package com.kratonsolution.cis.svc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Strings;
import com.kratonsolution.cis.dm.Family;
import com.kratonsolution.cis.dm.FamilyRepository;

@Service
@Transactional(rollbackFor=Exception.class)
public class FamilyService
{
	@Autowired
	private FamilyRepository repository;
	
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public Family find(String id)
	{
		if(!Strings.isNullOrEmpty(id))
			return repository.findOne(id);
		
		return null;
	}
	
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public List<Family> findAll()
	{
		return repository.findAll();
	}
	
	public void add(Family position)
	{
		repository.save(position);
	}
	
	public void edit(Family position)
	{
		repository.saveAndFlush(position);
	}
	
	public void delete(String position)
	{
		if(!Strings.isNullOrEmpty(position))
			repository.delete(position);
	}
}
