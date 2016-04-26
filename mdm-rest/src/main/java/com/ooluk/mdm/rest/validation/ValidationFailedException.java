package com.ooluk.mdm.rest.validation;

/**
 * Thrown when validation fails for input data. ValidationFailedException maintains a reference to
 * the {@link ValidationResponse}.
 * 
 * @author Siddhesh Prabhu
 * @since 1.0
 *
 */
public class ValidationFailedException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private ValidationResponse validationResponse;
	
	public ValidationFailedException(ValidationResponse validationResponse) {
		this.validationResponse = validationResponse;
	}

	public ValidationResponse getValidationResponse() {
		return validationResponse;
	}
}