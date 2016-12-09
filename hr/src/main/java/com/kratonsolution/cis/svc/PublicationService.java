package com.kratonsolution.cis.svc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Strings;
import com.kratonsolution.cis.dm.Publication;
import com.kratonsolution.cis.dm.PublicationRepository;

@Service
@Transactional(rollbackFor=Exception.class)
public class PublicationService
{
	@Autowired
	private PublicationRepository repository;
	
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public Publication find(String id)
	{
		if(!Strings.isNullOrEmpty(id))
			return repository.findOne(id);
		
		return null;
	}
	
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public List<Publication> findAll()
	{
		return repository.findAll();
	}
	
	public void add(Publication publication)
	{
		repository.save(publication);
	}
	
	public void edit(Publication publication)
	{
		repository.saveAndFlush(publication);
	}
	
	public void delete(String publication)
	{
		if(!Strings.isNullOrEmpty(publication))
			repository.delete(publication);
	}
}
