package com.ooluk.mdm.rest.dto;

import com.ooluk.mdm.core.meta.DynamicProperties;

/**
 * Common attributes for all meta objects. 
 * 
 * @author Siddhesh Prabhu
 * @since 1.0
 *
 */
public abstract class MetaObjectProjection {

	private Long id;
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