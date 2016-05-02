package com.ooluk.mdm.rest.app.dto;

import org.hibernate.validator.constraints.NotEmpty;

import com.ooluk.mdm.rest.dto.MetaObjectData;

/**
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
public class LabelTypeData extends MetaObjectData {

	@NotEmpty (message = "{labeltype.name.missing}")
	private String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}