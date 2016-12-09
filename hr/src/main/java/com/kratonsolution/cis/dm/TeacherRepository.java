package com.kratonsolution.cis.dm;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TeacherRepository extends JpaRepository<Teacher, String>
{
	public List<Teacher> findAllByType(TeacherType type);
	
	@Query("FROM Teacher emp WHERE "
			+ "emp.nama LIKE %:key% "
			+ "OR emp.nidn LIKE %:key% "
			+ "OR emp.path.note LIKE %:key% "
			+ "OR emp.step.note LIKE %:key% "
			+ "OR emp.decree.note LIKE %:key% "
			+ "OR emp.bagian LIKE %:key% "
			+ "OR emp.alamat LIKE %:key% "
			+ "OR emp.nip LIKE %:key% "
			+ "ORDER BY emp.nama ASC ")
	public List<Teacher> findAll(@Param("key")String key);
		
	@Query("FROM Teacher emp WHERE "
			+ "emp.type =:type "
			+ "AND (emp.nama LIKE %:key% "
			+ "OR emp.nidn LIKE %:key% "
			+ "OR emp.path.note LIKE %:key% "
			+ "OR emp.step.note LIKE %:key% "
			+ "OR emp.decree.note LIKE %:key% "
			+ "OR emp.bagian LIKE %:key% "
			+ "OR emp.alamat LIKE %:key% "
			+ "OR emp.nip LIKE %:key%) "
			+ "ORDER BY emp.nama ASC ")
	public List<Teacher> findAll(@Param("type")TeacherType type ,@Param("key")String key);
}
