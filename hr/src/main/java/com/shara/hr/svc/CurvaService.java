package com.shara.hr.svc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Strings;
import com.shara.hr.dm.Curve;
import com.shara.hr.dm.CurveRepository;

@Service
@Transactional(rollbackFor=Exception.class)
public class CurvaService
{
	@Autowired
	private CurveRepository repository;
	
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public Curve find(String id)
	{
		if(!Strings.isNullOrEmpty(id))
			return repository.findOne(id);
		
		return null;
	}
	
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public List<Curve> findAll()
	{
		return repository.findAll();
	}
	
	public void add(Curve curve)
	{
		repository.save(curve);
	}
	
	public void edit(Curve curve)
	{
		repository.saveAndFlush(curve);
	}
	
	public void delete(String curve)
	{
		if(!Strings.isNullOrEmpty(curve))
			repository.delete(curve);
	}
}
