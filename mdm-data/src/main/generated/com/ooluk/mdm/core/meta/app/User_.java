package com.ooluk.mdm.core.meta.app;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ extends com.ooluk.mdm.core.meta.StandaloneEntity_ {

	public static volatile SetAttribute<User, Role> roles;
	public static volatile SingularAttribute<User, String> userName;

}

