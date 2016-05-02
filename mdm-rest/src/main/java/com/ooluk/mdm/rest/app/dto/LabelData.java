package com.ooluk.mdm.rest.app.dto;

import org.hibernate.validator.constraints.NotEmpty;

import com.ooluk.mdm.rest.dto.MetaObjectData;
import com.ooluk.mdm.rest.validation.IsIdentified;

/**
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
public class LabelData extends MetaObjectData {
	
	@IsIdentified (message = "{label.type.missing}")
	private LabelTypeData type; 

	@NotEmpty (message = "{label.name.missing}")
	private String name;
	
	public LabelTypeData getType() {
		return type;
	}
	public void setType(LabelTypeData type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "LabelData [type=" + type + ", name=" + name + "]";
	}
}