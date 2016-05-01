package com.ooluk.mdm.data.meta.dataobject;

import org.springframework.stereotype.Repository;

import com.ooluk.mdm.data.meta.GenericRepositoryImpl;

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