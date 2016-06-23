package com.example.feedgeneration;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.model.Feed;

public class FeedGenerationUtil {

	public static final Logger log = LoggerFactory.getLogger(FeedGenerationUtil.class);

	public static Calendar calendar = Calendar.getInstance();

	public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

	public static double relThreshold = 1000;

	public static final double CONVERSION_CONSTANT = 1000 * 60 * 60 * 24;

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

	/**
	 * 
	 * @param feed
	 * @return
	 */
	public static Feed evaluateFeed(Feed feed) {
		double total = feed.getIncidentStats().getTotal();
		double relThreshold = 1000;
		double duration = 0;
		double confidence = 0;
		Date from = null;
		try {
			from = dateFormat.parse(feed.getLastSeen());
		} catch (ParseException e) {
			log.error("Error occurred while trying to parse date [{}]", feed.getLastSeen(), e);
		}

		// volume
		confidence = (total / relThreshold);
		confidence = confidence > 1 ? 0.4 : confidence * 0.4;

		// age in days
		duration = new Double(calendar.getTimeInMillis() - from.getTime()) / CONVERSION_CONSTANT;

		// age
		// activity state
		if (duration < 30) {
			// confidence += (duration / 30) * 0.4;
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

		log.info("confidence [{}]", confidence);
		feed.setConfidence(confidence * 100);
		feed.setRiskFactor(feed.getIncidentStats().riskAverage());
		return feed;

	}

}
