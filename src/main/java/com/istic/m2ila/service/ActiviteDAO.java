package com.istic.m2ila.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.istic.m2ila.model.Activite;
import com.istic.m2ila.model.Condition;

@Transactional
public interface ActiviteDAO extends JpaRepository<Activite, Long>  {

	@Query("select a from Activite a")
	List<Activite> findAll();
	
	@Query("select a from Activite a where a.id = :id")
	Activite findById(@Param("id") long id);
	
	boolean existsById(long id);
	
	@Transactional
	@Modifying
	@Query("update Activite a set a.nom = :nom, a.condition = :condition where a.id = :id")
	void setActiviteInfoById(@Param("id") long id, @Param("nom") String nom, 
			@Param("condition") Condition condition);
	
	@Transactional
    @Query("delete from Activite a where a.id = :id")
	void deleteById(@Param("id") long id);
	
	@Transactional
    @Query("delete from Activite a")
	void deleteAllActivites();
	
}
