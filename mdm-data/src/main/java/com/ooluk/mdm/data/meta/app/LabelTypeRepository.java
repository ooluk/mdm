package com.ooluk.mdm.data.meta.app;

import com.ooluk.mdm.data.meta.GenericRepository;

/**
 * Repository interface for label types.
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
public interface LabelTypeRepository extends GenericRepository<LabelType> {
	
	/**
	 * Finds a label type by its name
	 * 
	 * @param name
	 *            label type name
	 *            
	 * @return label type with the specified name or null if not found
	 */
	public LabelType findByName(String name);	
}