package com.ooluk.mdm.core.meta.dataobject;

import com.ooluk.mdm.core.meta.GenericRepository;

/**
 * Repository interface for namespaces.
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
public interface NamespaceRepository extends GenericRepository<Namespace> {
	
	/**
	 * Finds a namespace by its name
	 * 
	 * @param name
	 *            namespace name
	 *            
	 * @return namespace with the specified name or null if not found
	 */
	public Namespace findByName(String name);	
}