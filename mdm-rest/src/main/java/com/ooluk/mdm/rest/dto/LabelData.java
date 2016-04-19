package com.ooluk.mdm.rest.dto;

import com.ooluk.mdm.rest.validation.Validatable;
import com.ooluk.mdm.rest.validation.ValidationResponse;

/**
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
public class LabelData extends MetaObjectData implements Validatable<LabelData> {
	
	private LabelTypeData type; 
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
	public ValidationResponse validate(LabelData data) {		
		ValidationResponse vResult  = new ValidationResponse();		
		if (name == null || name.trim().isEmpty()) {
			vResult.addReason("Name is missing");
		}		
		return vResult;
	}

	@Override
	public String toString() {
		return "LabelCore [type=" + type + ", name=" + name + "]";
	}
}