package com.ooluk.mdm.core.meta.index;

import com.ooluk.mdm.core.meta.GenericRepository;
import com.ooluk.mdm.core.meta.dataobject.DataObject;

/**
 * Repository interface for indexes.
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
public interface IndexRepository extends GenericRepository<Index> {
	
	/**
	 * Finds an index by its name within a data object
	 * 
	 * @param dataObject
	 *            data object
	 * @param name
	 *            data object name
	 *            
	 * @return index with the specified name or null if not found
	 */
	public Index findByName(DataObject dataObject, String name);
}