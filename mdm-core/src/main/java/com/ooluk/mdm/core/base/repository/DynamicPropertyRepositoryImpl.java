package com.ooluk.mdm.core.base.repository;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.ooluk.mdm.core.base.data.DynamicProperty;
import com.ooluk.mdm.core.base.data.GenericRepositoryImpl;

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