package com.example;

import java.util.Date;

public class Feed {
	
	private String indicator;
	
	private String type; 
	
	private String title;
	
	private String description;
	
	private Date firstSeen;
	
	private Date lastSeen;
	
	private Date timestamp;
	
	private String validityPeriod;
	
	private float riskFactor;
	
	private float confidence;
	
	private String source;
	
	private String tlpLevel;
	
	public enum TTPType{C2, MALWARE, SCANNING, WEB, SSH};
	
	public enum suggestedCOA{BLOCK, MONITOR};
	
	
	
	
	

}
