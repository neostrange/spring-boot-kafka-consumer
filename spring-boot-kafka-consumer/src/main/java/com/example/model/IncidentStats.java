package com.example.model;

import java.io.Serializable;

public class IncidentStats implements Serializable {

	private static final long serialVersionUID = 1L;

	private long sev1;

	private long sev2;

	private long sev3;

	private long sev4;

	private long sev5;

	private long total;

	public IncidentStats() {
		this.sev1 = 0;
		this.sev2 = 0;
		this.sev3 = 0;
		this.sev4 = 0;
		this.sev5 = 0;
		this.total = 0;
	}

	public IncidentStats(long sev1, long sev2, long sev3, long sev4, long sev5, long total) {
		super();
		this.sev1 = sev1;
		this.sev2 = sev2;
		this.sev3 = sev3;
		this.sev4 = sev4;
		this.sev5 = sev5;
		this.total = total;
	}



	public long getSev1() {
		return sev1;
	}

	public void incrSev1() {
		this.sev1 += 1;
	}
	
	public void setSev1(long sev1) {
		this.sev1 = sev1;
	}

	public long getSev2() {
		return sev2;
	}
	
	public void incrSev2() {
		this.sev2 += 1;
	}

	public void setSev2(long sev2) {
		this.sev2 = sev2;
	}

	public long getSev3() {
		return sev3;
	}
	
	public void incrSev3() {
		this.sev3 += 1;
	}

	public void setSev3(long sev3) {
		this.sev3 = sev3;
	}

	public long getSev4() {
		return sev4;
	}
	
	public void incrSev4() {
		this.sev4 += 1;
	}

	public void setSev4(long sev4) {
		this.sev4 = sev4;
	}

	public long getSev5() {
		return sev5;
	}

	public void incrSev5() {
		this.sev5 += 1;
	}
	
	public void setSev5(long sev5) {
		this.sev5 = sev5;
	}

	public long getTotal() {
		return total;
	}
	
	public void incrTotal() {
		this.total += 1;
	}

	public void setTotal(long total) {
		this.total = total;
	}
	
	public double riskFactor(){
		return (sev1 * (0.1) + sev2 * (0.1) + sev3 * (0.2) + sev4 * (0.3) + sev5 * (0.3)) * 5;
	}

}
