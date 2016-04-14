/* 
 *  Copyright 2016 Ooluk Corporation
 */
package com.ooluk.mdm.core.meta;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/**
 * Enumeration of meta object types. Meta object types may be universal or non universal. Universal
 * meta object types have the same properties across the metadata manager instance whereas non
 * universal meta object types may have properties that are specific to certain contexts.
 * 
 * <p>
 * For example a data object may have different properties depending on whether it denotes a
 * relational database table or an Amazon DynamoDB table. Users however will have the same
 * properties across the instance of the software.
 * 
 * @author Siddhesh Prabhu
 * @since 1.0
 *
 */
public enum MetaObjectType {
	
	PROPERTY_GROUP,
	NAMESPACE,
	DATA_OBJECT,
	DATA_OBJECT_NOTE,
	DATA_OBJECT_SOURCE_TARGET_MAPPING,
	ATTRIBUTE,	
	ATTRIBUTE_NOTE,
	ATTRIBUTE_CODE,
	ATTRIBUTE_SOURCE_TARGET_MAPPING,
	INDEX,	
	INDEX_ATTRIBUTE_MAPPING,
	USER(true),	
	ROLE(true),		
	LABEL_TYPE(true),
	LABEL(true),	
	TAG(true);
	
	private boolean isUniversal = false;

	/**
	 * Constructs a non-universal MetaObjectType
	 */
	private MetaObjectType() {
	}
	
	/**
	 * Constructs a MetaObjectType with the specified universality
	 * 
	 * @param isUniversal
	 *            true if context aware.
	 */
	private MetaObjectType(boolean isUniversal) {
		this.isUniversal = isUniversal;
	}
	
	/**
	 * Returns meta object types that are independent of the context.
	 * 
	 * @return set of enum constants.
	 */
	public static EnumSet<MetaObjectType> getUniversalTypes() {
		
		List<MetaObjectType> types = new ArrayList<>();
		for (MetaObjectType type : MetaObjectType.values()) {
			if (!type.isUniversal) {
				types.add(type);
			}
		}
		return EnumSet.copyOf(types);
	};
	
	/**
	 * Returns meta object types that are dependent on the context.
	 * 
	 * @return set of enum constants.
	 */
	public static EnumSet<MetaObjectType> getNonUniversalTypes() {
		
		List<MetaObjectType> types = new ArrayList<>();
		for (MetaObjectType type : MetaObjectType.values()) {
			if (type.isUniversal) {
				types.add(type);
			}
		}
		return EnumSet.copyOf(types);
	};
}