package com.example.feedgeneration;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.model.Feed;

public class FeedGenerationUtil {

	public static final Logger log = LoggerFactory.getLogger(FeedGenerationUtil.class);

	public static DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

	public static double relThreshold = 1000;

	public static final double CONVERSION_CONSTANT = 1000 * 60 * 60 * 24;

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
		Period valPeriod = Period.ZERO;
		LocalDateTime now = LocalDateTime.parse(LocalDateTime.now().format(formatter));
		Duration dur = Duration.between(feed.getLastSeen(), now);

		// volume
		confidence = (total / relThreshold);
		confidence = confidence > 1 ? 0.4 : confidence * 0.4;

		// age in days
		duration = dur.toDays();

		// age
		// activity state
		if (duration < 30) {
			valPeriod = valPeriod.plusDays(90);
			confidence += (duration / 30) * 0.4;
		} else if (duration >= 30 && duration < 91) {
			valPeriod = valPeriod.plusDays(60);
			confidence += (duration / 90) * 0.4;
		} else if (duration >= 91) {
			valPeriod = valPeriod.plusDays(30);
			confidence += (duration / 365) * 0.4;
		} else {
			confidence += 0.4;
		}

		log.info("confidence [{}]", confidence);
		feed.setExpiry(ZonedDateTime.now(ZoneId.of("UTC")).plus(valPeriod));
		log.info("Feed expiry [{}]",feed.getExpiry().toString());
		feed.setConfidence(confidence * 100);
		feed.setRiskFactor((feed.getIncidentStats().riskAverage() / 5) * 10);
		return feed;

	}

}
