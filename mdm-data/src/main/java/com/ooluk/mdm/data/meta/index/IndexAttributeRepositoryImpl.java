package com.ooluk.mdm.data.meta.index;

import org.springframework.stereotype.Repository;

import com.ooluk.mdm.data.meta.GenericRepositoryImpl;

/**
 * Repository implementation for attribute mappings.
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
@Repository
public class IndexAttributeRepositoryImpl extends GenericRepositoryImpl<IndexAttributeMapping> 
		implements IndexAttributeRepository {	

	public IndexAttributeRepositoryImpl() {
		super(IndexAttributeMapping.class);
	}
}