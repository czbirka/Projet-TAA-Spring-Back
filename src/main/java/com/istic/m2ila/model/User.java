package com.istic.m2ila.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class User {

	private long id;

	private String nom;
	private String prenom;
	private String login;
	private String motDePasse;
	private String email;

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

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@OneToMany(cascade=CascadeType.ALL, mappedBy="user")
	@JsonIgnore
	public List<Activite> getActivites() {
		return activites;
	}

	public void setActivites(List<Activite> activites) {
		this.activites = activites;
	}
	
	public void addActivite(Activite activite) {
		this.getActivites().add(activite);
	}
	
    void update(String newNom, String newPrenom, String newLogin, String newMDP, String newEmail) {
        this.setNom(newNom);
        this.setPrenom(newPrenom);
        this.setLogin(newLogin);
        this.setMotDePasse(newMDP);
        this.setEmail(newEmail);
    }
}
