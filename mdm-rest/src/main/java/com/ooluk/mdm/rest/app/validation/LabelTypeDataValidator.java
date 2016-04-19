package com.ooluk.mdm.rest.app.validation;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ooluk.mdm.rest.app.dto.LabelTypeData;
import com.ooluk.mdm.rest.validation.ValidationFailedException;
import com.ooluk.mdm.rest.validation.ValidationResponse;
import com.ooluk.mdm.rest.validation.Validator;
import com.ooluk.mdm.rest.validation.ValidatorRegistry;

/**
 * Validator for {@link LabelTypeData}. 
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
@Component
public class LabelTypeDataValidator implements Validator<LabelTypeData> {
	
	@Autowired
	private ValidatorRegistry registry;
	
	@PostConstruct
	public void register() {
		registry.register(LabelTypeData.class, this);
	}
	
	@Override
	public void validate(LabelTypeData data) throws ValidationFailedException {		
		ValidationResponse result  = new ValidationResponse();	
		if (data.getName() == null || data.getName().trim().isEmpty()) {
			result.addReason("Name is missing");
		}
		
		if (!result.isValid()) {
			throw new ValidationFailedException(result);
		}
	}
}