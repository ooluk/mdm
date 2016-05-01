package com.ooluk.mdm.core.meta.attribute;

import com.ooluk.mdm.core.meta.app.Label;
import com.ooluk.mdm.core.meta.app.Tag;
import com.ooluk.mdm.core.meta.dataobject.DataObject;
import com.ooluk.mdm.core.meta.index.IndexAttributeMapping;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Attribute.class)
public abstract class Attribute_ extends com.ooluk.mdm.core.meta.StandaloneEntity_ {

	public static volatile SingularAttribute<Attribute, DataObject> dataObject;
	public static volatile SetAttribute<Attribute, AttributeNote> notes;
	public static volatile SetAttribute<Attribute, IndexAttributeMapping> indexes;
	public static volatile SetAttribute<Attribute, AttributeSourceMapping> sources;
	public static volatile SingularAttribute<Attribute, String> name;
	public static volatile SetAttribute<Attribute, Attribute> childAttributes;
	public static volatile SetAttribute<Attribute, AttributeSourceMapping> targets;
	public static volatile SingularAttribute<Attribute, Attribute> parentAttribute;
	public static volatile SetAttribute<Attribute, Label> labels;
	public static volatile SetAttribute<Attribute, Tag> tags;

}

