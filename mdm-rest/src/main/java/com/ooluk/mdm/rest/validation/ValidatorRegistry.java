package com.ooluk.mdm.rest.validation;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * A registry that maps {@link Validatable}s to their respective {@link Validator}s.
 * 
 * @author Siddhesh Prabhu
 * @since 1.0
 *
 */
@Component
public class ValidatorRegistry {

	private Map<Class<? extends Validatable<?>>, Validator<?>> registry = new HashMap<>();
	
	/**
	 * Registers a validator with the type it validates.
	 * 
	 * @param cls
	 *            class of the data/object the validator validates
	 * @param validator
	 *            validator
	 */
	public <T extends Validatable<T>> void register(Class<T> cls, Validator<T> validator) {
		registry.put(cls, validator);
	}
	
	@SuppressWarnings ( "rawtypes" )
	public <T extends Validatable<T>> Validator getValidator(Class<T> cls) {
		return registry.get(cls);
	}
}