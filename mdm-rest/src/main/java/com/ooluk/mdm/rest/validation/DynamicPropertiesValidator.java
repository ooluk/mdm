package com.ooluk.mdm.rest.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ooluk.mdm.data.meta.DynamicPropertiesCache;

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
	
}