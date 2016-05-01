package com.ooluk.mdm.data.meta;


/**
 * Repository interface for dynamic properties.
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
public interface DynamicPropertyRepository extends GenericRepository<DynamicProperty> {
		
	/**
	 * Finds a dynamic property by its key. The key is used while storing a dynamic property in JSON.
	 * 
	 * @param key
	 *            property key 
	 *            
	 * @return dynamic property with the specified key or null if not found
	 */
	public DynamicProperty findByKey(String key);	
}