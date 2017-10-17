package com.istic.m2ila.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.istic.m2ila.model.Departement;
import com.istic.m2ila.model.Lieu;

@Transactional
public interface LieuDAO extends JpaRepository<Lieu, Long> {

	@Query("select l from Lieu l")
	List<Lieu> findAll();
	
	@Query("select l from Lieu l where l.id = :id")
	Lieu findById(@Param("id") long id);
	
	@Transactional
	@Query("select l from User l where l.nom = :nom")
	List<Lieu> findByNom(@Param("nom") String nom);
	
	boolean existsById(long id);

	@Transactional
	@Modifying
	@Query("update Lieu l set l.nom = :nom, l.latitude = :latitude, l.longitude = :longitude, "
			+ "l.departement = :departement where l.id = :id")
	void setLieuInfoById(@Param("id") long id, @Param("nom") String nom, @Param("latitude") double latitude,
			@Param("longitude") double longitude, @Param("departement") Departement departement);
	
	@Transactional
    @Query("delete from Lieu l where l.id = :id")
	void deleteById(@Param("id") long id);
	
	@Transactional
    @Query("delete from Lieu u")
	void deleteAllLieux();
	
}
