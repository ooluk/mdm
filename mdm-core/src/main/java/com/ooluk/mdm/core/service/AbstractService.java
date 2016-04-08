package com.ooluk.mdm.core.service;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.ooluk.mdm.core.base.data.MetaObjectType;

/**
 * Superclass for all services.
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
public abstract class AbstractService {

	@Autowired 
	protected Mapper mapper;
	
	
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