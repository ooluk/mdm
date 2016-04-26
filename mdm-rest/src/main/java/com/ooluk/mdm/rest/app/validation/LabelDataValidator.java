package com.ooluk.mdm.rest.app.validation;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.ooluk.mdm.rest.app.dto.LabelData;
import com.ooluk.mdm.rest.validation.GenericValidator;
import com.ooluk.mdm.rest.validation.ValidationFailedException;
import com.ooluk.mdm.rest.validation.ValidationResponse;

/**
 * Validator for {@link LabelData}.
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
@Component
public class LabelDataValidator extends GenericValidator<LabelData> {
	
	@PostConstruct
	public void register() {
		registry.register(LabelData.class, this);
	}
	
	@Override
	public void validate(LabelData data) {		
		ValidationResponse result  = new ValidationResponse();	
		if (data.getName() == null || data.getName().trim().isEmpty()) {
			result.addReason("Name is missing");
		}
		
		ValidationResponse dpValidation = dpValidator.validate(data.getProperties());
		for (String reason : dpValidation.getFailureReasons()) 
			result.addReason(reason);
		
		if (!result.isValid()) {
			throw new ValidationFailedException(result);
		}
	}
}