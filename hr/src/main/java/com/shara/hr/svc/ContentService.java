package com.shara.hr.svc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Strings;
import com.shara.hr.dm.Content;
import com.shara.hr.dm.ContentRepository;

@Service
@Transactional(rollbackFor=Exception.class)
public class ContentService
{
	@Autowired
	private ContentRepository repository;
	
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public Content find(String id)
	{
		if(!Strings.isNullOrEmpty(id))
			return repository.findOne(id);
		
		return null;
	}
	
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public List<Content> findAll()
	{
		return repository.findAll();
	}
	
	public void add(Content content)
	{
		repository.save(content);
	}
	
	public void edit(Content content)
	{
		repository.saveAndFlush(content);
	}
	
	public void delete(String content)
	{
		if(!Strings.isNullOrEmpty(content))
			repository.delete(content);
	}
}
