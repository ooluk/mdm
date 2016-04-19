package com.ooluk.mdm.rest.dto;

import com.ooluk.mdm.rest.validation.Validatable;
import com.ooluk.mdm.rest.validation.ValidationResponse;

/**
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
public class LabelTypeData extends MetaObjectData implements Validatable<LabelTypeData>{

	private String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public ValidationResponse validate(LabelTypeData data) {
		ValidationResponse vResult  = new ValidationResponse();		
		if (name == null || name.trim().isEmpty()) {
			vResult.addReason("Name is missing");
		}		
		return vResult;
	}
}