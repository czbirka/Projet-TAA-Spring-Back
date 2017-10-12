package com.istic.m2ila.model;

import javax.persistence.Embeddable;

@Embeddable
public class Condition {

	double ventMin;
	double ventInf;
	double ventSup;
	double ventMax;
	double tempMin;
	double tempInf;
	double tempSup;
	double tempMax;
	
	public Condition() {}
	
	public Condition(double vmin, double vinf, double vsup, double vmax, double tmin, double tinf, double tsup, double tmax) {
		this.ventMin = vmin;
		this.ventInf = vinf;
		this.ventSup = vsup;
		this.ventMax = vmax;
		this.tempMin = tmin;
		this.tempInf = tinf;
		this.tempSup = tsup;
		this.tempMax = tmax;
	}

	public double getVentMin() {
		return ventMin;
	}

	public void setVentMin(double ventMin) {
		this.ventMin = ventMin;
	}

	public double getVentInf() {
		return ventInf;
	}

	public void setVentInf(double ventInf) {
		this.ventInf = ventInf;
	}

	public double getVentSup() {
		return ventSup;
	}

	public void setVentSup(double ventSup) {
		this.ventSup = ventSup;
	}

	public double getVentMax() {
		return ventMax;
	}

	public void setVentMax(double ventMax) {
		this.ventMax = ventMax;
	}

	public double getTempMin() {
		return tempMin;
	}

	public void setTempMin(double tempMin) {
		this.tempMin = tempMin;
	}

	public double getTempInf() {
		return tempInf;
	}

	public void setTempInf(double tempInf) {
		this.tempInf = tempInf;
	}

	public double getTempSup() {
		return tempSup;
	}

	public void setTempSup(double tempSup) {
		this.tempSup = tempSup;
	}

	public double getTempMax() {
		return tempMax;
	}

	public void setTempMax(double tempMax) {
		this.tempMax = tempMax;
	}

}
