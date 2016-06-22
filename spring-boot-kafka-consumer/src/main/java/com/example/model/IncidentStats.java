package com.example.model;

import java.io.Serializable;

public class IncidentStats implements Serializable {

	private static final double serialVersionUID = 1L;

	private double sev1;

	private double sev2;

	private double sev3;

	private double sev4;

	private double sev5;

	private double total;

	public IncidentStats() {
		this.sev1 = 0;
		this.sev2 = 0;
		this.sev3 = 0;
		this.sev4 = 0;
		this.sev5 = 0;
		this.total = 0;
	}

	public IncidentStats(double sev1, double sev2, double sev3, double sev4, double sev5, double total) {
		super();
		this.sev1 = sev1;
		this.sev2 = sev2;
		this.sev3 = sev3;
		this.sev4 = sev4;
		this.sev5 = sev5;
		this.total = total;
	}

	public double getSev1() {
		return sev1;
	}

	public void incrSev1() {
		this.sev1 += 1;
	}

	public void setSev1(double sev1) {
		this.sev1 = sev1;
	}

	public double getSev2() {
		return sev2;
	}

	public void incrSev2() {
		this.sev2 += 1;
	}

	public void setSev2(double sev2) {
		this.sev2 = sev2;
	}

	public double getSev3() {
		return sev3;
	}

	public void incrSev3() {
		this.sev3 += 1;
	}

	public void setSev3(double sev3) {
		this.sev3 = sev3;
	}

	public double getSev4() {
		return sev4;
	}

	public void incrSev4() {
		this.sev4 += 1;
	}

	public void setSev4(double sev4) {
		this.sev4 = sev4;
	}

	public double getSev5() {
		return sev5;
	}

	public void incrSev5() {
		this.sev5 += 1;
	}

	public void setSev5(double sev5) {
		this.sev5 = sev5;
	}

	public double getTotal() {
		return total;
	}

	public void incrTotal() {
		this.total += 1;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "{sev1 [" + sev1 + "], sev2 [" + sev2 + "], sev3 [" + sev3 + "], sev4 [" + sev4 + "], sev5 [" + sev5
				+ "], total [" + total + "]}";
	}

	public double riskAverage() {
		// return ((sev1/total * ( 10 * 10) + sev2/total * (10 * 20) +
		// sev3/total * ( 10 * 30) + sev4/total * (10 * 40) + sev5/total * (10 *
		// 50))/(50));
		return (sev1 * (5*1) + sev2 * (5*2) + sev3 * (5*3) + sev4 * (5*4) + sev5 * (5*5))/(5*total);

	}

}
