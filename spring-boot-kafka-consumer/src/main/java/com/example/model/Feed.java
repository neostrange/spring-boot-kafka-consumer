package com.example.model;

import java.io.Serializable;

public class Feed implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7996275520964690026L;

	public enum TTPType {
		C2, MALWARE, SCANNING, WEB, SSH
	};

	public enum SuggestedCOA {
		BLOCK, MONITOR
	};

	public enum Type {
		IP, MD5, URL
	};

	private String indicator;

	private String type;

	private String title;

	private String description;

	private String firstSeen;

	private String lastSeen;

	private String timestamp;

	private String validityPeriod;

	private double riskFactor;

	private double confidence;

	private IncidentStats incidentStats;

	private String source;

	private String tlpLevel;

	private TTPType ttpType;
	
	private ThreatType threatType;

	private SuggestedCOA suggestedCOA;

	public Feed() {
	}

	public Feed(String indicator, String type, String firstSeen, String lastSeen, String timestamp) {
		this.indicator = indicator;
		this.type = type;
		this.firstSeen = firstSeen;
		this.lastSeen = lastSeen;
		this.timestamp = timestamp;
	}

	public String getIndicator() {
		return indicator;
	}

	public void setIndicator(String indicator) {
		this.indicator = indicator;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

		
	public String getFirstSeen() {
		return firstSeen;
	}

	public void setFirstSeen(String firstSeen) {
		this.firstSeen = firstSeen;
	}

	public String getLastSeen() {
		return lastSeen;
	}

	public void setLastSeen(String lastSeen) {
		this.lastSeen = lastSeen;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getValidityPeriod() {
		return validityPeriod;
	}

	public void setValidityPeriod(String validityPeriod) {
		this.validityPeriod = validityPeriod;
	}

	public double getRiskFactor() {
		return riskFactor;
	}

	public void setRiskFactor(double riskFactor) {
		this.riskFactor = riskFactor;
	}

	public double getConfidence() {
		return confidence;
	}

	public void setConfidence(double confidence) {
		this.confidence = confidence;
	}

	public IncidentStats getIncidentStats() {
		return incidentStats;
	}

	public void setIncidentStats(IncidentStats incidentStats) {
		this.incidentStats = incidentStats;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTlpLevel() {
		return tlpLevel;
	}

	public void setTlpLevel(String tlpLevel) {
		this.tlpLevel = tlpLevel;
	}

	public TTPType getTtpType() {
		return ttpType;
	}

	public void setTtpType(TTPType ttpType) {
		this.ttpType = ttpType;
	}
	
	public ThreatType getThreatType() {
		return threatType;
	}

	public void setThreatType(ThreatType threatType) {
		this.threatType = threatType;
	}

	public SuggestedCOA getSuggestedCOA() {
		return suggestedCOA;
	}

	public void setSuggestedCOA(SuggestedCOA suggestedCOA) {
		this.suggestedCOA = suggestedCOA;
	}



}
