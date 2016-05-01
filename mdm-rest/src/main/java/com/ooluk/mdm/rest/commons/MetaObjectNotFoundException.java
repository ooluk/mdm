package com.ooluk.mdm.rest.commons;

import com.ooluk.mdm.data.meta.MetaObjectType;

/**
 * Exception to indicate a meta object is not found.
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
public class MetaObjectNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private MetaObjectType type;
	private Long id;
	
	/**
	 * Constructs a MetaObjectNotFoundException for a specific metaobject type and ID.
	 * 
	 * @param type
	 *            meta object type
	 * @param id
	 *            meta object ID
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
		return type + " with ID=" + id + " not found";
	}
}