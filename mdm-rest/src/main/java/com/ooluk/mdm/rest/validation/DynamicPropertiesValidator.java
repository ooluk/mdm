package com.ooluk.mdm.rest.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ooluk.mdm.core.meta.DynamicProperties;
import com.ooluk.mdm.core.meta.DynamicPropertiesCache;
import com.ooluk.mdm.core.meta.DynamicProperty;

/**
 * Validator for dynamic properties.
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
@Component
public class DynamicPropertiesValidator {

	@Autowired
	private DynamicPropertiesCache cache;
	
	//@Autowired 
	private boolean validationEnabled;
	
	public ValidationResponse validate(DynamicProperties props) {
		ValidationResponse result = new ValidationResponse();
		if (validationEnabled) {
			for (String key : props.getProperties().keySet()) {
				DynamicProperty dp = cache.get(key);
				if (dp == null) {
					result.addReason("Property [" + key + "] not found");
				}
				Object value = props.getProperty(key);
			}
		}
		return result;
	}
}