package com.ooluk.mdm.core.app.repository;

import com.ooluk.mdm.core.app.data.LabelType;
import com.ooluk.mdm.core.base.data.GenericRepository;

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