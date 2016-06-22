package com.example.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;

public class CustomObjectMapper {

	public static ObjectMapper getInstance() {
		ObjectMapper objectMapper = null;

		if (objectMapper == null) {
			// init Jackson object mapper and make sure it doesn't include null
			// fields
			objectMapper = new ObjectMapper();
			objectMapper.setSerializationInclusion(Include.NON_NULL);
			objectMapper.setSerializationInclusion(Include.NON_EMPTY);
			objectMapper.disable(SerializationFeature.WRITE_NULL_MAP_VALUES);
			objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
			objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			objectMapper.setVisibility(
					VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
		}
		return objectMapper;
	}

}
