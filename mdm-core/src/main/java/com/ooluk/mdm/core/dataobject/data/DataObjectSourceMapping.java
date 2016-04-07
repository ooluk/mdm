package com.ooluk.mdm.core.dataobject.data;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.ooluk.mdm.core.base.data.RelationshipEntity;

/**
 * Encapsulates the properties of a data object source-to-target mapping. 
 * 
 * @author Siddhesh Prabhu
 * @since 1.0
 *
 */
@Entity
@Table ( name = "mdm_data_object_source")
public class DataObjectSourceMapping extends 
	RelationshipEntity<DataObject, DataObject, DataObjectSourceMappingKey> {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new data object target-source mapping.
	 */
	public DataObjectSourceMapping() {
	}
	
	/**
	 * Constructs a new data object target-source mapping.
	 * 
	 * @param target
	 *            target data object
	 * @param source
	 *            source data object
	 */
	public DataObjectSourceMapping(DataObject target, DataObject source) {
		super(new DataObjectSourceMappingKey(target, source));
	}

	@Override
	public String toString() {
		return "DataObjectSourceMapping [key=" + getKey() + ", properties=" + getProperties() + "]";
	}
}