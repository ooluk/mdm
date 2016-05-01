package com.ooluk.mdm.data.meta.attribute;

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
public class AttributeSourceRepositoryImpl extends GenericRepositoryImpl<AttributeSourceMapping> 
		implements AttributeSourceRepository {	

	public AttributeSourceRepositoryImpl() {
		super(AttributeSourceMapping.class);
	}
}