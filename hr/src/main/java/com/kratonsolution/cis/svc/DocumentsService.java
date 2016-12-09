package com.kratonsolution.cis.svc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Strings;
import com.kratonsolution.cis.dm.Documents;
import com.kratonsolution.cis.dm.DocumentsRepository;

@Service
@Transactional(rollbackFor=Exception.class)
public class DocumentsService
{
	@Autowired
	private DocumentsRepository repository;
	
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public Documents find(String id)
	{
		if(!Strings.isNullOrEmpty(id))
			return repository.findOne(id);
		
		return null;
	}
	
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public List<Documents> findAll()
	{
		return repository.findAll();
	}
	
	public void add(Documents documents)
	{
		repository.save(documents);
	}
	
	public void edit(Documents documents)
	{
		repository.saveAndFlush(documents);
	}
	
	public void delete(String documents)
	{
		if(!Strings.isNullOrEmpty(documents))
			repository.delete(documents);
	}
}
