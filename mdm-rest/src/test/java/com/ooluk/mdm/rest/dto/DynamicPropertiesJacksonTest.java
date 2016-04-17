package com.ooluk.mdm.rest.dto;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.ooluk.mdm.core.meta.DynamicProperties;
import com.ooluk.mdm.rest.test.TestData;

/**
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
public class DynamicPropertiesJacksonTest {

	private static ObjectMapper mapper = new ObjectMapper();

	// Create an implementation of abstract class MetaObjectProjection 
	private static class MetaObjectProjectionImpl extends MetaObjectData {
		
		@Override
		public Long getId() {
			return 1L;
		}
		
		@Override
		public DynamicProperties getProperties() {
			return TestData.getDynamicProperties();
		}
	}
	
	private DynamicProperties expected = TestData.getDynamicProperties();	
	
	@Test
	public void testSerialization() throws JsonProcessingException {
				
		MetaObjectData meta = new MetaObjectProjectionImpl();
		String json = mapper.writeValueAsString(meta);
		// DEBUG: System.out.println(json);
		
		// Test "number" property
		JsonPath jpath = JsonPath.compile("$.properties.number");
		Integer number = jpath.read(json);
		assertEquals(expected.getProperty("number"), number.intValue());
		
		// Test "text-value" property
		jpath = JsonPath.compile("$.properties.text");
		String value = jpath.read(json);
		assertEquals(expected.getProperty("text"), value);
	}
	
	@Test
	public void testDeserialization() throws Exception {		
		String json = "{\"id\":1,\"properties\":"
				+ "{\"number\":100,\"text\":\"text-value\",\"number-list\":[1,2],\"text-list\":[\"t1\",\"t2\"]}}";		
		MetaObjectProjectionImpl meta = mapper.readValue(json, MetaObjectProjectionImpl.class);
		assertEquals(expected, meta.getProperties());
	}
}