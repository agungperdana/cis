package com.kratonsolution.cis.svc;

import java.util.List;

import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Strings;
import com.kratonsolution.cis.dm.Admin;
import com.kratonsolution.cis.dm.AdminRepository;

@Service
@Transactional(rollbackFor=Exception.class)
public class AdminService
{
	@Autowired
	private AdminRepository repository;

	private StrongPasswordEncryptor encryptor = new StrongPasswordEncryptor();
	
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public Admin authenticate(String email,String password)
	{
		if(!Strings.isNullOrEmpty(email) && !Strings.isNullOrEmpty(password))
		{
			Admin admin = repository.findOneByEmail(email);
			if(admin != null && encryptor.checkPassword(password, admin.getPassword()))
			{
				admin.setPassword(null);
				return admin;
			}
		}
		
		return null;
	}
	
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public Admin find(String id)
	{
		if(!Strings.isNullOrEmpty(id))
			return repository.findOne(id);
		
		return null;
	}
	
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public List<Admin> findAll()
	{
		return repository.findAll();
	}
	
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public List<Admin> findAll(String filter)
	{
		if(Strings.isNullOrEmpty(filter))
			return findAll();
		
		return repository.findAll(filter);
	}
	
	public void add(Admin admin)
	{
		if(Strings.isNullOrEmpty(admin.getEmail()))
			admin.setEmail(null);
		
		if(!Strings.isNullOrEmpty(admin.getPassword()))
			admin.setPassword(encryptor.encryptPassword(admin.getPassword()));
			
		repository.save(admin);
	}
	
	public void edit(Admin admin)
	{
		if(!Strings.isNullOrEmpty(admin.getPassword()))
			admin.setPassword(encryptor.encryptPassword(admin.getPassword()));
		
		repository.saveAndFlush(admin);
	}
	
	public void delete(String admin)
	{
		if(!Strings.isNullOrEmpty(admin))
			repository.delete(admin);
	}
}
