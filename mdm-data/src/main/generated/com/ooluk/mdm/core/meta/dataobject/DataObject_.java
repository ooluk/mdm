package com.ooluk.mdm.core.meta.dataobject;

import com.ooluk.mdm.core.meta.app.Label;
import com.ooluk.mdm.core.meta.app.Tag;
import com.ooluk.mdm.core.meta.attribute.Attribute;
import com.ooluk.mdm.core.meta.index.Index;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(DataObject.class)
public abstract class DataObject_ extends com.ooluk.mdm.core.meta.StandaloneEntity_ {

	public static volatile SetAttribute<DataObject, DataObjectNote> notes;
	public static volatile SetAttribute<DataObject, Index> indexes;
	public static volatile SetAttribute<DataObject, DataObjectSourceMapping> sources;
	public static volatile SingularAttribute<DataObject, Namespace> namespace;
	public static volatile SingularAttribute<DataObject, String> name;
	public static volatile SetAttribute<DataObject, Attribute> attributes;
	public static volatile SetAttribute<DataObject, DataObjectSourceMapping> targets;
	public static volatile SetAttribute<DataObject, Label> labels;
	public static volatile SetAttribute<DataObject, Tag> tags;

}

