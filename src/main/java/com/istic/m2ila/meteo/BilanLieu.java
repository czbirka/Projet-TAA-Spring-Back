package com.istic.m2ila.meteo;

import java.util.ArrayList;
import java.util.List;

import com.istic.m2ila.model.Lieu;

public class BilanLieu {

	private Lieu lieu;
	List<BilanActivite> bilansActivite = new ArrayList<>();
	double temp;
	double vent;

	public Lieu getLieu() {
		return lieu;
	}

	public void setLieu(Lieu lieu) {
		this.lieu = lieu;
	}

	public List<BilanActivite> getBilansActivite() {
		return bilansActivite;
	}

	public void setBilansActivite(List<BilanActivite> bilansActivite) {
		this.bilansActivite = bilansActivite;
	}

	public double getTemp() {
		return temp;
	}

	public void setTemp(double temp) {
		this.temp = temp;
	}

	public double getVent() {
		return vent;
	}

	public void setVent(double vent) {
		this.vent = vent;
	}

}
