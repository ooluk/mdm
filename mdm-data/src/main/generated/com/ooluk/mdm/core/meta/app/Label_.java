package com.ooluk.mdm.core.meta.app;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Label.class)
public abstract class Label_ extends com.ooluk.mdm.core.meta.StandaloneEntity_ {

	public static volatile SetAttribute<Label, Label> children;
	public static volatile SingularAttribute<Label, String> name;
	public static volatile SingularAttribute<Label, LabelType> type;
	public static volatile SetAttribute<Label, Label> parents;

}

