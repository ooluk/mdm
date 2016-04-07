package com.ooluk.mdm.core.index.data;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.ooluk.mdm.core.attribute.data.Attribute;
import com.ooluk.mdm.core.base.data.RelationshipEntity;

/**
 * Encapsulates the properties of an index-attribute mapping. For example you could store details
 * such as ascending or descending or whether any expression is defined on the attribute.
 * 
 * @author Siddhesh Prabhu
 * @since 1.0
 *
 */
@Entity
@Table ( name = "mdm_index_attribute")
public class IndexAttributeMapping extends 
	RelationshipEntity<Index, Attribute, IndexAttributeMappingKey> {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs a new data mapping.
	 */
	public IndexAttributeMapping() {
		super();
	}
	
	/**
	 * Constructs a new mapping between an index and attribute
	 * 
	 * @param idx
	 *            index
	 * @param attr
	 *            attribute
	 */
	public IndexAttributeMapping(Index idx, Attribute attr) {
		super(new IndexAttributeMappingKey(idx, attr));
	}

	@Override
	public String toString() {
		return "IndexAttributeMapping [getKey()=" + getKey() + ", getProperties()="
				+ getProperties() + "]";
	}
}