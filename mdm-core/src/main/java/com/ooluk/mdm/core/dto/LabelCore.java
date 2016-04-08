package com.ooluk.mdm.core.dto;

/**
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
public class LabelCore extends MetaObjectProjection {
	
	private LabelTypeCore type; 
	private String name;
	
	public LabelTypeCore getType() {
		return type;
	}
	public void setType(LabelTypeCore type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}