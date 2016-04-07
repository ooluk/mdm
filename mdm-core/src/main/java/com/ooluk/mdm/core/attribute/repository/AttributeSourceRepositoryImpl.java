package com.ooluk.mdm.core.attribute.repository;

import org.springframework.stereotype.Repository;

import com.ooluk.mdm.core.attribute.data.AttributeSourceMapping;
import com.ooluk.mdm.core.base.data.GenericRepositoryImpl;

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