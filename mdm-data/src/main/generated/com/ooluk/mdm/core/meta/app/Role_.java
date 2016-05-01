package com.ooluk.mdm.core.meta.app;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Role.class)
public abstract class Role_ extends com.ooluk.mdm.core.meta.StandaloneEntity_ {

	public static volatile SingularAttribute<Role, String> name;
	public static volatile SetAttribute<Role, User> users;

}

