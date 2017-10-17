package com.istic.m2ila.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.istic.m2ila.model.Departement;
import com.istic.m2ila.model.Region;

@Transactional
public interface DepartementDAO extends CrudRepository<Departement, Long>   {

	@Query("select d from Departement d")
	List<Departement> findAll();
	
	@Query("select d from Departement d where d.id = :id")
	Departement findById(@Param("id") long id);
	
	@Query("select d from Departement d where d.nom = :nom")
	List<Departement> findByNom(@Param("nom") String nom);
	
	@Query("select d from Departement d where d.region.nom = :nomRegion")
	List<Departement> findByRegion(@Param("nomRegion") String nomRegion);
	
	boolean existsById(long id);
	
	@Transactional
	@Modifying
	@Query("update Departement d set d.nom = :nom, d.region = :region where d.id = :id")
	void setDepartementInfoById(@Param("id") long id, @Param("nom") String nom, @Param("region") Region region);

//	@Transactional
//    @Query("delete from Departement d where d.id = :id")
//	void deleteById(@Param("id") long id);
	
	@Transactional
    long deleteById(long id);
	
	@Transactional
    @Query("delete from Departement d")
	void deleteAllDepartements();
	
}
