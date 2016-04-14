package com.ooluk.mdm.core.meta;


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
