package com.istic.m2ila.meteo;

import java.util.ArrayList;
import java.util.List;

import com.istic.m2ila.model.User;

public class BilanUser {

	private User user;

	private List<BilanLieu> bilanSamedi = new ArrayList<>();
	private List<BilanLieu> bilanDimanche = new ArrayList<>();

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<BilanLieu> getBilanSamedi() {
		return bilanSamedi;
	}

	public void setBilanSamedi(List<BilanLieu> bilanSamedi) {
		this.bilanSamedi = bilanSamedi;
	}

	public List<BilanLieu> getBilanDimanche() {
		return bilanDimanche;
	}

	public void setBilanDimanche(List<BilanLieu> bilanDimanche) {
		this.bilanDimanche = bilanDimanche;
	}

}
