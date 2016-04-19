package com.ooluk.mdm.rest.app.validation;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ooluk.mdm.rest.app.dto.LabelData;
import com.ooluk.mdm.rest.validation.ValidationFailedException;
import com.ooluk.mdm.rest.validation.ValidationResponse;
import com.ooluk.mdm.rest.validation.Validator;
import com.ooluk.mdm.rest.validation.ValidatorRegistry;

/**
 * Validator for {@link LabelData}.
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
@Component
public class LabelDataValidator implements Validator<LabelData> {
	
	@Autowired
	private ValidatorRegistry registry;
	
	@PostConstruct
	public void register() {
		registry.register(LabelData.class, this);
	}
	
	@Override
	public void validate(LabelData data) throws ValidationFailedException {		
		ValidationResponse result  = new ValidationResponse();	
		if (data.getName() == null || data.getName().trim().isEmpty()) {
			result.addReason("Name is missing");
		}
		
		if (!result.isValid()) {
			throw new ValidationFailedException(result);
		}
	}
}