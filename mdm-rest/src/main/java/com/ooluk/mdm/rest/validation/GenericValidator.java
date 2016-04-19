package com.ooluk.mdm.rest.validation;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * A generic validator with common attributes.
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
public abstract class GenericValidator<T extends Validatable<T>> implements Validator<T> {
	
	@Autowired
	protected ValidatorRegistry registry;
	
	@Autowired
	protected DynamicPropertiesValidator dpValidator;
}
