package com.ooluk.mdm.rest.dto;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.ooluk.mdm.data.meta.DynamicProperties;

/**
 * This performs deserialization of dynamic properties. See {@link DynamicPropertiesJsonSerializer}
 * for details.
 * 
 * @author Siddhesh Prabhu
 * @since 1.0
 * @see DynamicPropertiesJsonSerializer
 *
 */
public class DynamicPropertiesJsonDeserializer extends JsonDeserializer<DynamicProperties> {

	@Override
	public DynamicProperties deserialize(JsonParser parser, DeserializationContext ctx)
			throws IOException, JsonProcessingException {
		@SuppressWarnings ( "unchecked" )
		Map<String, Object> map = parser.readValueAs(Map.class);
		return new DynamicProperties(map);
	}
}