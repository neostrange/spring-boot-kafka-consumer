package com.example.model;

import java.io.Serializable;

public class ThreatType implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -985629024450006237L;

	private boolean recon;
	
	private boolean malware;
	
	private boolean web;
	
	private boolean sip;
	
	private boolean bruteForce;
	
	private boolean possibleCompromise;
	
	private boolean db;

	public ThreatType() {
		this.recon = false;
		this.malware = false;
		this.web = false;
		this.sip = false;
		this.bruteForce = true;
		this.db = false;
		this.possibleCompromise = true;
	}

	public boolean isRecon() {
		return recon;
	}

	public void setRecon(boolean recon) {
		this.recon = recon;
	}

	public boolean isMalware() {
		return malware;
	}

	public void setMalware(boolean malware) {
		this.malware = malware;
	}

	public boolean isWeb() {
		return web;
	}

	public void setWeb(boolean web) {
		this.web = web;
	}

	public boolean isSip() {
		return sip;
	}

	public void setSip(boolean sip) {
		this.sip = sip;
	}

	public boolean isBruteForce() {
		return bruteForce;
	}

	public void setBruteForce(boolean bruteForce) {
		this.bruteForce = bruteForce;
	}

	public boolean isPossibleCompromise() {
		return possibleCompromise;
	}

	public void setPossibleCompromise(boolean possibleCompromise) {
		this.possibleCompromise = possibleCompromise;
	}

	public boolean isDb() {
		return db;
	}

	public void setDb(boolean db) {
		this.db = db;
	}
	
	

}
