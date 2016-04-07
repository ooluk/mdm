package com.ooluk.mdm.core.dataobject.repository;

import org.springframework.stereotype.Repository;

import com.ooluk.mdm.core.base.data.GenericRepositoryImpl;
import com.ooluk.mdm.core.dataobject.data.DataObjectSourceMapping;

/**
 * Repository implementation for data object mappings.
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
@Repository
public class DataObjectSourceRepositoryImpl extends GenericRepositoryImpl<DataObjectSourceMapping> 
		implements DataObjectSourceRepository {	

	public DataObjectSourceRepositoryImpl() {
		super(DataObjectSourceMapping.class);
	}
}