package com.ooluk.mdm.rest.validation;

import org.springframework.stereotype.Component;

@Component
public class Validator {

	public <T extends Validatable<T>> void validate(T data) throws ValidationFailedException {
		ValidationResponse vResult = data.validate(data);
		if (!vResult.isValid()) {
			throw new ValidationFailedException(vResult);
		}
	}
}
