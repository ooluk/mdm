package com.ooluk.mdm.core.meta.attribute;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.ooluk.mdm.core.meta.RelationshipEntity;

/**
 * Encapsulates the properties of an attribute source-to-target mapping. 
 * 
 * @author Siddhesh Prabhu
 * @since 1.0
 *
 */
@Entity
@Table ( name = "mdm_attribute_source")
public class AttributeSourceMapping extends 
	RelationshipEntity<Attribute, Attribute, AttributeSourceMappingKey> {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new attribute target-source mapping.
	 */
	public AttributeSourceMapping() {
	}
	
	/**
	 * Constructs a new attribute target-source.
	 * 
	 * @param target
	 *            target attribute
	 * @param source
	 *            source attribute
	 */
	public AttributeSourceMapping(Attribute target, Attribute source) {
		super(new AttributeSourceMappingKey(target, source));
	}

	@Override
	public String toString() {
		return "AttributeSourceMapping [key=" + getKey() + ", properties=" + getProperties() + "]";
	}
}