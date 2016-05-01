package com.ooluk.mdm.rest.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ooluk.mdm.data.meta.DynamicProperties;

/**
 * Common attributes for all meta objects. 
 * 
 * @author Siddhesh Prabhu
 * @since 1.0
 *
 */
public abstract class MetaObjectData {

	private Long id;
	
	@JsonSerialize(using = DynamicPropertiesJsonSerializer.class)
	@JsonDeserialize(using = DynamicPropertiesJsonDeserializer.class)
	private DynamicProperties properties;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public DynamicProperties getProperties() {
		return properties;
	}
	public void setProperties(DynamicProperties properties) {
		this.properties = properties;
	}
}