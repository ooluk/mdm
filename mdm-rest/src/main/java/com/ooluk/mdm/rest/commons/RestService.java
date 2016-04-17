package com.ooluk.mdm.rest.commons;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.ooluk.mdm.core.meta.MetaObjectType;
import com.ooluk.mdm.rest.validation.Validator;

/**
 * Superclass for all REST services.
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
@RestController
public abstract class RestService {

	@Autowired 
	protected Mapper mapper;
	
	@Autowired
	protected Validator validator;
	
	/**
	 * Sets the validator used for validation.
	 * 
	 * @param validator
	 *            validator
	 */
	public void setValidator(Validator validator) {
		this.validator = validator;
	}


	/**
	 * Convenience method to throw MetaObjectNotFoundException.
	 * 
	 * @param type
	 *            metaobject type
	 * @param id
	 *            metaobject id
	 *            
	 * @throws MetaObjectNotFoundException
	 */
	protected void notFound(MetaObjectType type, Long id) throws MetaObjectNotFoundException {
		throw new MetaObjectNotFoundException(type, id);
	}
}