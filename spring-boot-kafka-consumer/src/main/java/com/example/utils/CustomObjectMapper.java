package com.example.utils;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;

public class CustomObjectMapper extends ObjectMapper {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4547238634469707690L;

	public CustomObjectMapper() {

		// init Jackson object mapper and make sure it doesn't include null
		// fields
		Jackson8Module module = new Jackson8Module();
		module.addStringSerializer(LocalDateTime.class, (val) -> val.toString());
		this.registerModule(module);
		this.setSerializationInclusion(Include.NON_NULL);
		this.setSerializationInclusion(Include.NON_EMPTY);
		this.disable(SerializationFeature.WRITE_NULL_MAP_VALUES);
		this.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		this.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		this.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		this.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
	}

}
