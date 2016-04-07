package com.ooluk.mdm.core.base.data;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This class represents a collection of extended properties. Each extended property is defined as a
 * key-value pair. The key must correspond to the key attribute of the corresponding
 * {@link DynamicProperty}
 * 
 * @author Siddhesh Prabhu
 * @since 1.0
 *
 */
public class DynamicProperties {
	
	private static ObjectMapper mapper = new ObjectMapper();

	private Map<String, Object> properties;
	
	/**
	 * Parses a JSON string into a DynamicProperties instance.
	 * 
	 * @param json
	 *            JSON object
	 *            
	 * @return DynamicProperties instance
	 * 
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static DynamicProperties parseJson(String json) 
			throws JsonParseException, JsonMappingException, IOException {
		@SuppressWarnings ( "unchecked" )
		Map<String, Object> props = mapper.readValue(json, Map.class);
		return new DynamicProperties(props);
	}
	
	/**
	 * Constructs an instance without properties. 
	 */
	public DynamicProperties() {
		properties = new HashMap<>();
	}
	
	/**
	 * Constructs an instance with the specified property mappings
	 * 
	 * @param mappings
	 *            key-value pairs
	 */
	public DynamicProperties(Map<String, Object> mappings) {
		this();
		this.properties.putAll(mappings);
	}
	
	/**
	 * Returns the value of a property.
	 * 
	 * @param name
	 *            property name
	 *            
	 * @return property value
	 */
	public Object getProperty(String name) {
		return properties.get(name);
	}
	
	/**
	 * Sets the value for a specific property.  
	 * 
	 * @param name
	 *            property name
	 * @param value
	 *            property value
	 * 
	 * @return a reference to this object to allow method chaining
	 */
	public DynamicProperties setProperty(String name, Object value) {
		properties.put(name, value);
		return this;
	}
	
	/**
	 * Removes a specific property. 
	 * 
	 * @param name
	 *            property name
	 * 
	 * @return a reference to this object to allow method chaining
	 */
	public DynamicProperties removeProperty(String name) {
		properties.remove(name);
		return this;
	}
	
	/**
	 * Determines if a property with the specified name exists.
	 * 
	 * @param name
	 *            property name
	 * @return true if property exists false otherwise.
	 */
	public boolean containsProperty(String name) {
		return properties.containsKey(name);
	}
	
	/**
	 * Returns all property name-value pairs.
	 * 
	 * @return map of property name-value pairs
	 */
	public Map<String, Object> getAllProperties() {
		return properties;
	}
	
	/**
	 * Returns a count of the number of dynamic properties.
	 * 
	 * @return count of the number of dynamic properties
	 */
	public int count() {
		return properties.size();
	}
	
	/**
	 * Returns a JSON representation of the properties.
	 * 
	 * @return JSON string
	 * 
	 * @throws JsonProcessingException
	 *             if exception occurs during JSON conversion
	 */
	public String toJson() throws JsonProcessingException {
		return mapper.writeValueAsString(properties);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((properties == null) ? 0 : properties.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DynamicProperties other = (DynamicProperties) obj;		
		return Objects.equals(properties, other.properties);
	}

	@Override
	public String toString() {
		return "DynamicProperties [properties=" + properties + "]";
	}
}