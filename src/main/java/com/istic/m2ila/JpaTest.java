package com.istic.m2ila;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.springframework.beans.factory.annotation.Autowired;

import com.istic.m2ila.model.User;


public class JpaTest {

	//@Autowired
	//private User u1 = new User();
	//u1.
	
//	u1.setPrenom("Jean");
//	u1.setLogin("jeanbambois");
//	u1.setMotDePasse("jbpassword");
//	u1.setEmail("jean.bambois@azertyuiop.xyz");


	private void createData() {
		/*
		User u1 = new User();
		u1.setNom("Bambois");
		u1.setPrenom("Jean");
		u1.setLogin("jeanbambois");
		u1.setMotDePasse("jbpassword");
		u1.setEmail("jean.bambois@azertyuiop.xyz");
		manager.persist(u1);
		User u2 = new User();
		u2.setNom("Deffaire");
		u2.setPrenom("Phil");
		u2.setLogin("phildeffaire");
		u2.setMotDePasse("pdpassword");
		u2.setEmail("phil.deffaire@azertyuiop.xyz");
		manager.persist(u2);
		User u3 = new User();
		u3.setNom("Terieur");
		u3.setPrenom("Alain");
		u3.setLogin("alainterieur");
		u3.setMotDePasse("atpassword");
		u3.setEmail("alain.terieur@azertyuiop.xyz");
		manager.persist(u3);
		User u4 = new User();
		u4.setNom("Terieur");
		u4.setPrenom("Alex");
		u4.setLogin("alexterieur");
		u4.setMotDePasse("atpassword2");
		u4.setEmail("alex.terieur@azertyuiop.xyz");
		manager.persist(u4);
		
		Region r1 = new Region();
		r1.setNom("Bretagne");
		manager.persist(r1);
		Region r2 = new Region();
		r2.setNom("Normandie");
		manager.persist(r2);

		Departement d1 = new Departement();
		d1.setNom("Ille-et-Vilaine");
		manager.persist(d1);
		Departement d2 = new Departement();
		d2.setNom("Morbihan");
		manager.persist(d2);
		Departement d3 = new Departement();
		d3.setNom("Manche");
		manager.persist(d3);
		
		d1.setRegion(r1);
		d2.setRegion(r1);
		d3.setRegion(r2);
		
		Lieu l1 = new Lieu("Rennes", 48.1119800, -1.6742900);
		manager.persist(l1);
		Lieu l2 = new Lieu("Saint-Malo", 48.649337, -2.025674);
		manager.persist(l2);
		Lieu l3 = new Lieu("Quiberon", 47.4833, -3.1167);
		manager.persist(l3);
		Lieu l4 = new Lieu("Saint-Lô", 49.116667, -1.083333);
		manager.persist(l4);
		
		l1.setDepartement(d1);
		l2.setDepartement(d1);
		l3.setDepartement(d2);
		l4.setDepartement(d3);
		
		Activite a1u1 = new Activite();
		a1u1.setNom("Football");
		Condition c1 = new Condition(1.5, 2.3,  5.9, 7.5, 5, 15.3, 25.6, 35.6);
		a1u1.setCondition(c1);
		manager.persist(a1u1);
		a1u1.setUser(u1);
		
		Activite a2u1 = new Activite();
		a2u1.setNom("Jokari");
		Condition c2 = new Condition(0, 2,  4, 8, 12, 18, 25, 35);
		a2u1.setCondition(c2);
		manager.persist(a2u1);
		a2u1.setUser(u1);
		
		Activite a3u1 = new Activite();
		a3u1.setNom("Planche à voile");
		Condition c3 = new Condition(4, 8,  10, 15, 10, 15, 24, 35);
		a3u1.setCondition(c3);
		manager.persist(a3u1);
		a3u1.setUser(u1);
		
		a1u1.addLieu(l1);
		a1u1.addLieu(l2);
		a1u1.addLieu(l3);
		a1u1.addLieu(l4);
		
		a2u1.addLieu(l1);
		a2u1.addLieu(l2);
		
		a3u1.addLieu(l1);
		a3u1.addLieu(l2);
		a3u1.addLieu(l3);
		*/
	}



}
