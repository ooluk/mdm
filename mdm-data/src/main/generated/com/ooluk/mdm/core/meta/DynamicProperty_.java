package com.ooluk.mdm.core.meta;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(DynamicProperty.class)
public abstract class DynamicProperty_ extends com.ooluk.mdm.core.meta.StandaloneEntity_ {

	public static volatile SingularAttribute<DynamicProperty, MetaObjectType> metaObjectType;
	public static volatile SingularAttribute<DynamicProperty, Integer> size;
	public static volatile SingularAttribute<DynamicProperty, String> defaultValue;
	public static volatile SetAttribute<DynamicProperty, ListValue> valueList;
	public static volatile SingularAttribute<DynamicProperty, String> format;
	public static volatile SingularAttribute<DynamicProperty, String> description;
	public static volatile SingularAttribute<DynamicProperty, DynamicPropertyType> type;
	public static volatile SingularAttribute<DynamicProperty, Boolean> mandatory;
	public static volatile SingularAttribute<DynamicProperty, String> key;
	public static volatile SetAttribute<DynamicProperty, PropertyGroup> propertyGroups;
	public static volatile SingularAttribute<DynamicProperty, VisualAttributes> visualAttributes;

}

