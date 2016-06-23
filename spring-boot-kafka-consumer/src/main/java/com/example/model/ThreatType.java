package com.example.model;

import java.io.Serializable;

import com.example.feedgeneration.Constants;

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
		this.bruteForce = false;
		this.db = false;
		this.possibleCompromise = false;
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
	
	public void update(String category){
		switch (category) {
		case Constants.RECONNAISSANCE:
			this.setRecon(true);
			break;
		case Constants.MALWARE:
			this.setMalware(true);
			break;
		case Constants.WEB_ATTACK:
			this.setWeb(true);
			break;
		case Constants.SIP_ATTACK:
			this.setSip(true);
			break;
		case Constants.DB_MSSQL_ATTACK:
			this.setDb(true);
			break;
		case Constants.DB_MYSQL_ATTACK:
			this.setDb(true);
			break;
		case Constants.SSH_BRUTE_FORCE:
			this.setBruteForce(true);
			break;
		case Constants.SSH_POSSIBLE_COMPROMISE:
			this.setPossibleCompromise(true);
			break;
		default:
			this.setRecon(true);
		}

	}
	
	

}
