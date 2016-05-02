package com.ooluk.mdm.rest.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.ooluk.mdm.rest.dto.MetaObjectData;

public class IsIdentifiedValidator implements ConstraintValidator<IsIdentified, MetaObjectData> {
	
	@Override
	public void initialize(IsIdentified constraintAnnotation) {
	}

	@Override
	public boolean isValid(MetaObjectData obj, ConstraintValidatorContext ctx) {
		return obj != null && obj.getId() != null;
	}
}