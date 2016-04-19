package com.ooluk.mdm.rest.app.dto;

import com.ooluk.mdm.rest.dto.MetaObjectData;
import com.ooluk.mdm.rest.validation.Validatable;

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
}