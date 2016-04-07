package com.ooluk.mdm.core.base.repository;

import com.ooluk.mdm.core.base.data.GenericRepository;
import com.ooluk.mdm.core.base.data.PropertyGroup;

/**
 * Repository interface for property groups.
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
public interface PropertyGroupRepository extends GenericRepository<PropertyGroup> {
	
	/**
	 * Finds a property group by name
	 * 
	 * @param name
	 *            property group name
	 *            
	 * @return property group with the specified name or null if not found
	 */
	public PropertyGroup findByName(String name);	
}
