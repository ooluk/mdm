package com.ooluk.mdm.core.service;

import com.ooluk.mdm.core.base.data.MetaObjectType;

/**
 * Exception to indicate a meta object is not found.
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
public class MetaObjectNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private MetaObjectType type;
	private Long id;
	
	/**
	 * Constructs a MetaObjectNotFoundException for a specific metaobject type and ID.
	 * 
	 * @param type
	 *            metaobject type
	 * @param id
	 *            metaobject ID
	 */
	public MetaObjectNotFoundException(MetaObjectType type, Long id) {
		this.type = type;
		this.id = id;
	}
	
	/**
	 * Returns the meta object type.
	 * 
	 * @return meta object type
	 */
	public MetaObjectType getType() {
		return type;
	}

	/**
	 * Returns meta object ID.
	 * 
	 * @return meta object ID
	 */
	public Long getId() {
		return id;
	}

	@Override
	public String getMessage() {
		return type + " with id=" + id + " not found";
	}
}