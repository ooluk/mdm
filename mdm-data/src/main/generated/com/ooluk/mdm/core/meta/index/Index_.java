package com.ooluk.mdm.core.meta.index;

import com.ooluk.mdm.core.meta.dataobject.DataObject;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Index.class)
public abstract class Index_ extends com.ooluk.mdm.core.meta.StandaloneEntity_ {

	public static volatile SingularAttribute<Index, DataObject> dataObject;
	public static volatile SingularAttribute<Index, String> name;
	public static volatile SetAttribute<Index, IndexAttributeMapping> attributes;

}

