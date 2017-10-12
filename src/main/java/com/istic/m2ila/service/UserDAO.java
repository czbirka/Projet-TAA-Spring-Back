package com.istic.m2ila.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.istic.m2ila.model.User;

@Transactional
public interface UserDAO extends CrudRepository<User, Long> {

	@Transactional
	@Query("select u from User u")
	List<User> findAll();
	
	@Transactional
	@Query("select u from User u where u.id = :id")
	User findById(@Param("id") long id);
	
	@Transactional
	@Query("select u from User u where u.nom = :nom")
	List<User> findByNomIgnoreCase(@Param("nom") String nom);
	
	@Transactional
	@Query("select u from User u where u.login = :login")
	User findByLogin(@Param("login") String login);

	boolean existsById(long id);

	@Transactional
	@Modifying
	@Query("update User u set u.nom = :nom, u.prenom = :prenom, u.login = :login, u.motDePasse = :mdp, u.email = :email where u.id = :id")
	void setUserInfoById(@Param("id") long id, @Param("nom") String nom, @Param("prenom") String prenom,
			@Param("login") String login, @Param("mdp") String motDePasse, @Param("email") String email);
	
	@Transactional
    @Query("delete from User u where u.id = :id")
	void deleteById(@Param("id") long id);

	@Transactional
    @Query("delete from User u")
	void deleteAllUsers();
	 
}
