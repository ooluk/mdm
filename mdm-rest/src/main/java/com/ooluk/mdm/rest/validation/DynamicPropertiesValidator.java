package com.ooluk.mdm.rest.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ooluk.mdm.data.meta.DynamicProperties;
import com.ooluk.mdm.data.meta.DynamicPropertiesCache;

/**
 * Validator for dynamic properties.
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
@Component
public class DynamicPropertiesValidator implements ConstraintValidator<DynamicValidated, DynamicProperties> {

	@Autowired
	private DynamicPropertiesCache cache;

	@Override
	public void initialize(DynamicValidated constraintAnnotation) {		
	}

	@Override
	public boolean isValid(DynamicProperties props, ConstraintValidatorContext ctx) {
		boolean valid = true;
		/*
		for (String key : props.getProperties().keySet()) {
			DynamicProperty dp = cache.get(key);
			if (dp == null) {
				ctx.buildConstraintViolationWithTemplate(key).addConstraintViolation();
				valid = false;
			}
			Object value = props.getProperty(key);
		}
		*/
		return valid;
	}	
}