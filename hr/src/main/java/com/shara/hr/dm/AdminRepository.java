package com.shara.hr.dm;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdminRepository extends JpaRepository<Admin, String>
{
	public Admin findOneByEmail(String email);
	
	@Query("FROM Admin emp WHERE "
			+ "emp.nama LIKE %:key% "
			+ "OR emp.step.note LIKE %:key% "
			+ "OR emp.path.note LIKE %:key% "
			+ "OR emp.decree.note LIKE %:key% "
			+ "OR emp.bagian LIKE %:key% "
			+ "OR emp.alamat LIKE %:key% "
			+ "OR emp.nip LIKE %:key% "
			+ "ORDER BY emp.nama ASC ")
	public List<Admin> findAll(@Param("key")String key);
}
