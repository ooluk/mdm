package com.ooluk.mdm.rest.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * A common entry point for all validations.
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
@Component
public class DataValidator {
	
	@Autowired
	private ValidatorRegistry registry;

	/**
	 * Validates data. Returns void if validation is successful and throws ValidationFailedException
	 * otherwise.
	 * 
	 * @param data
	 *            validatable data
	 * 
	 * @throws ValidationFailedException
	 *             if validation fails
	 */
	public <T extends Validatable<T>> void validate(T data)
			throws ValidationFailedException {
		@SuppressWarnings ( "unchecked" )
		Validator<T> validator = registry.getValidator(data.getClass());
		validator.validate(data);
	}
}