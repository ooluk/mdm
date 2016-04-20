package com.ooluk.mdm.core.meta.app;

import com.ooluk.mdm.core.meta.GenericRepository;

/**
 * Repository interface for tags.
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
public interface TagRepository extends GenericRepository<Tag> {
	
	/**
	 * Finds a tag by its name
	 * 
	 * @param name
	 *            tag name
	 *            
	 * @return tag with the specified name or null if not found
	 */
	public Tag findByName(String name);
}