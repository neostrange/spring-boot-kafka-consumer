package com.example.feedgeneration;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.model.Feed;
import com.example.model.IncidentStats;
import com.example.model.ThreatType;

public class FeedGenerationUtil {

	public static final Logger log = LoggerFactory.getLogger(FeedGenerationUtil.class);

	public static Calendar calendar = Calendar.getInstance();

	public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

	public static Calendar getCalendar() {
		return calendar;
	}

	public static void setCalendar(Calendar calendar) {
		FeedGenerationUtil.calendar = calendar;
	}

	public static DateFormat getDateFormat() {
		return dateFormat;
	}

	public static void setDateFormat(DateFormat dateFormat) {
		FeedGenerationUtil.dateFormat = dateFormat;
	}

	public static long getMax() {
		return max;
	}

	public static void setMax(long max) {
		FeedGenerationUtil.max = max;
	}

	public static long max = 0;

	/**
	 * 
	 * @param category
	 * @param type
	 * @return
	 */
	public ThreatType getThreatType(String category, ThreatType type) {
		switch (category) {
		case "Reconnaissance":
			type.setRecon(true);
			break;
		case "Malware":
			type.setMalware(true);
			break;
		case "Web Attack":
			type.setWeb(true);
			break;
		case "SIP Attack":
			type.setSip(true);
			break;
		case "DB-MSSQL Attack":
			type.setDb(true);
			break;
		case "DB-MYSQL Attack":
			type.setDb(true);
			break;
		case "SSH Brute-Force Attempt":
			type.setBruteForce(true);
			break;
		case "SSH Possible Compromise":
			type.setPossibleCompromise(true);
			break;
		default:
			type.setRecon(true);
			break;
		}
		return type;
	}

	/**
	 * 
	 * @param severity
	 * @param stats
	 * @return
	 */
	public IncidentStats updateStats(int severity, IncidentStats stats) {
		switch (severity) {
		case 1:
			stats.incrSev1();
			break;
		case 2:
			stats.incrSev2();
			break;
		case 3:
			stats.incrSev3();
			break;
		case 4:
			stats.incrSev4();
			break;
		case 5:
			stats.incrSev5();
			break;
		default:
			stats.incrSev1();
			break;
		}
		stats.incrTotal();
		if (stats.getTotal() > max)
			max = (long) stats.getTotal();
		return stats;
	}

	/**
	 * 
	 * @param feed
	 * @return
	 */
	public Feed evaluateFeed(Feed feed) {
		double total = feed.getIncidentStats().getTotal();
		double relThreshold = 1000;
		double duration = 0;
		double confidence = 0;
		Date from = null, to = null;
		try {
			from = dateFormat.parse(feed.getFirstSeen());
		} catch (ParseException e) {
			log.error("Error occurred while trying to parse date [{}]", feed.getFirstSeen(), e);
		}

		try {
			to = dateFormat.parse(feed.getLastSeen());
		} catch (ParseException e) {
			log.error("Error occurred while trying to parse date [{}]", feed.getLastSeen(), e);
		}

//		duration = to.getTime() - from.getTime();
//		// TODO why is total 0?
//		 total = total == 0 ? total++ : total;
//		// // frequency
//		 duration = duration == 0 ? duration++ : duration;
//		 confidence = (total / (duration / 1000));
//		 log.info("confidence [{}]", confidence);
		// volume
		confidence = (total / relThreshold) * 0.6;
		 log.info("confidence [{}]", confidence);

		// confidence *= 0.4;
		// confidence = total * 0.5;
		// age
		duration = (calendar.getTimeInMillis() - to.getTime()) / (1000 * 60 * 60 * 24);

		// age
		// activity state
		if (duration < 30) {
			confidence += (duration / 30) * 0.4;
			feed.setValidityPeriod("long");
		} else if (duration >= 30 && duration < 91) {
			confidence += (duration / 90) * 0.4;
			feed.setValidityPeriod("long");
		} else if (duration >= 91) {
			confidence += (duration / 365) * 0.4;
			feed.setValidityPeriod("short");
		} else {
			confidence += 0.4;
			feed.setValidityPeriod("short");
		}

		feed.setConfidence(confidence);
		feed.setRiskFactor(feed.getIncidentStats().riskAverage());
		return feed;

	}

}
