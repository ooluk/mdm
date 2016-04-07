package com.ooluk.mdm.core.index.repository;

import org.springframework.stereotype.Repository;

import com.ooluk.mdm.core.base.data.GenericRepositoryImpl;
import com.ooluk.mdm.core.index.data.IndexAttributeMapping;

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