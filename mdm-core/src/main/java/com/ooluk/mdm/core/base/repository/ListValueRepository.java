package com.ooluk.mdm.core.base.repository;

import com.ooluk.mdm.core.base.data.GenericRepository;
import com.ooluk.mdm.core.base.data.ListValue;

/**
 * Repository interface for list value.
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
public interface ListValueRepository extends GenericRepository<ListValue> {
	
	/**
	 * Finds a list value by its value
	 * 
	 * @param value
	 *            list value
	 *            
	 * @return list value with the specified value or null if not found
	 */
	public ListValue findByValue(String value);	
}