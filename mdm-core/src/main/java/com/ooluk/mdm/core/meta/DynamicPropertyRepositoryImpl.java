package com.ooluk.mdm.core.meta;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 * Repository implementation for dynamic properties.
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
public class DynamicPropertyRepositoryImpl 
	extends GenericRepositoryImpl<DynamicProperty> implements DynamicPropertyRepository {

	public DynamicPropertyRepositoryImpl() {
		super(DynamicProperty.class);
	}

	@Override
	public DynamicProperty findByKey(String key) {
		Session session = super.getSession();
		return (DynamicProperty) session.createCriteria(DynamicProperty.class)
			.add(Restrictions.eq("key", key)).uniqueResult();
	}
}