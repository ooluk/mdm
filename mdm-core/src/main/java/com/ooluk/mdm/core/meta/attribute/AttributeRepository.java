package com.ooluk.mdm.core.meta.attribute;

import com.ooluk.mdm.core.meta.GenericRepository;
import com.ooluk.mdm.core.meta.dataobject.DataObject;

/**
 * Repository interface for attributes.
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
public interface AttributeRepository extends GenericRepository<Attribute> {
	
	/**
	 * Finds a attribute by its name within a data object
	 * 
	 * @param data object
	 *            data object
	 * @param name
	 *            attribute name
	 *            
	 * @return attribute with the specified name or null if not found
	 */
	public Attribute findByName(DataObject dataObject, String name);
}