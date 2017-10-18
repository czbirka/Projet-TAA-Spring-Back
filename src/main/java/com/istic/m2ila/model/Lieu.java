package com.istic.m2ila.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Lieu {

	
	private long id;
	private String nom;
	private double latitude;
	private double longitude;
	
	private List<Activite> activites = new ArrayList<Activite>();

	private Departement departement;
	
	public Lieu() {
		this.latitude = 99;
		this.longitude = 99;
	};
	
	public Lieu(String nom, double latitude, double longitude) {
		this.nom = nom;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	@ManyToMany(mappedBy = "lieux")
	@JsonIgnore
	public List<Activite> getActivites() {
		return activites;
	}

	public void setActivites(List<Activite> activites) {
		this.activites = activites;
	}

	@ManyToOne
	//@JsonIgnore
	public Departement getDepartement() {
		return departement;
	}

	public void setDepartement(Departement departement) {
		this.departement = departement;
	}
	
	
	
	
	
	

/*	
	private long id;
	private String nom;
	private double latitude;
	private double longitude;
	
	
	private Departement departement;

	//private List<User> users = new ArrayList<User>();
	
	//@ManyToMany(mappedBy="lieux", cascade={CascadeType.PERSIST,CascadeType.MERGE})
	//@JsonIgnore
	private List<Activite> activites = new ArrayList<Activite>();

	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	//@ManyToMany(mappedBy="lieux", cascade={CascadeType.PERSIST,CascadeType.MERGE})
	//@JsonIgnore
	//public List<User> getUsers() {
	//	return users;
	//}

	//public void setUsers(List<User> users) {
	//	this.users = users;
	//}
	
	@ManyToMany(mappedBy="lieux", cascade={CascadeType.PERSIST,CascadeType.MERGE})
	//@JsonIgnore
	public List<Activite> getActivites() {
		return activites;
	}

	public void setActivites(List<Activite> activites) {
		this.activites = activites;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	@ManyToOne
	@JsonBackReference
	public Departement getDepartement() {
		return departement;
	}

	public void setDepartement(Departement departement) {
		this.departement = departement;
	}
	
	public void addActivite(Activite activite) {
		this.getActivites().add(activite);
	}
*/
}
