package com.ooluk.mdm.rest.dto;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.ooluk.mdm.core.meta.DynamicProperties;

/**
 * A custom JSON serializer for {@link DynamicProperties}. MetaObjectPrjection maintains dynamic
 * properties using a field called "properties". DynamicProperties itself maintains its properties
 * as a Map called "properties". This causes the default JSON to appear as
 * <code>{... "properties": {"properties": {}}}</code>.
 * 
 * <p>
 * This serializer just writes out the internal map of DynamicProperties causing the JSON to appear
 * as <code>{... "properties": {}}</code>.
 * 
 * @author Siddhesh Prabhu
 * @since 1.0
 *
 */
public class DynamicPropertiesJsonSerializer extends JsonSerializer<DynamicProperties> {

	@Override
	public void serialize(DynamicProperties props, JsonGenerator jgen, SerializerProvider sp)
			throws IOException, JsonProcessingException {
		jgen.writeObject(props.getProperties());
	}
}