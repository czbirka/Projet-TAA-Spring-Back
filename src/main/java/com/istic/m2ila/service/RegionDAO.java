package com.istic.m2ila.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.istic.m2ila.model.Region;
import com.istic.m2ila.model.User;

@Transactional
public interface RegionDAO extends CrudRepository<Region, Long>  {

	@Transactional
	@Query("select r from Region r")
	List<Region> findAll();
	
	@Transactional
	@Query("select r from Region r where r.id = :id")
	Region findById(@Param("id") long id);
	
//	@Query("select r from Region r where r.nom = :nom")
//	Region findByNom(@Param("nom") String nom);
	
	@Transactional
	@Query("select r from Region r where r.nom = :nom")
	List<Region> findByNom(@Param("nom") String nom);
	
	boolean existsById(long id);
	
	@Transactional
	@Modifying
	@Query("update Region r set r.nom = :nom where r.id = :id")
	void setRegionInfoById(@Param("id") long id, @Param("nom") String nom);
	
//	@Transactional
//    @Query("delete from Region r where r.id = :id")
//	void deleteById(@Param("id") long id);
	
	@Transactional
    long deleteById(long id);
	
	@Transactional
    @Query("delete from Region r")
	void deleteAllRegions();
	
}
