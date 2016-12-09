package com.kratonsolution.cis.svc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Strings;
import com.kratonsolution.cis.dm.WorkStep;
import com.kratonsolution.cis.dm.WorkStepRepository;

@Service
@Transactional(rollbackFor=Exception.class)
public class PositionService
{
	@Autowired
	private WorkStepRepository repository;
	
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public WorkStep find(String id)
	{
		if(!Strings.isNullOrEmpty(id))
			return repository.findOne(id);
		
		return null;
	}
	
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public List<WorkStep> findAll()
	{
		return repository.findAll();
	}
	
	public void add(WorkStep position)
	{
		repository.save(position);
	}
	
	public void edit(WorkStep position)
	{
		repository.saveAndFlush(position);
	}
	
	public void delete(String position)
	{
		if(!Strings.isNullOrEmpty(position))
			repository.delete(position);
	}
}
