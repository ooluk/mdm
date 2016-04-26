package com.ooluk.mdm.rest.commons;

import java.util.Map;

import com.ooluk.mdm.core.meta.MetaObjectType;

/**
 * Exception to indicate a meta object with the specified key already exists.
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
public class MetaObjectExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private MetaObjectType type;
	private String key;
	private Map<String, Object> compositeKey;
	
	/**
	 * Constructs a MetaObjectNotFoundException for a specific metaobject type and string key. This
	 * method is handy for meta objects that have a single String key or are represented by a unique
	 * string key within a containing meta object (e.g. Attribute).
	 * 
	 * @param type
	 *            metaobject type
	 * @param key
	 *            metaobject key
	 */
	public MetaObjectExistsException(MetaObjectType type, String key) {
		this.type = type;
		this.key = key;
	}
	
	/**
	 * Constructs a MetaObjectNotFoundException for a specific metaobject type and composite key.
	 * This method is handy for meta objects that have composite keys.
	 * 
	 * @param type
	 *            metaobject type
	 * @param compositeKey
	 *            metaobject composite key
	 */
	public MetaObjectExistsException(MetaObjectType type, Map<String, Object> compositeKey) {
		this.type = type;
		this.compositeKey = compositeKey;
	}

	/**
	 * Returns the meta object type.
	 * 
	 * @return the meta object type
	 */
	public MetaObjectType getType() {
		return type;
	}
	
	/**
	 * Determines if a meta object has a composite key.
	 * 
	 * @return true if meta object has a composite key, false otherwise.
	 */
	public boolean hasCompositeKey() {
		return compositeKey != null;
	}

	/**
	 * Returns the meta object key.
	 * 
	 * @return the meta object key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Returns the meta object composite key.
	 * 
	 * @return a map of key-value pairs
	 */
	public Map<String, Object> getCompositeKey() {
		return compositeKey;
	}
}