package com.ooluk.mdm.core.base.repository;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.ooluk.mdm.core.base.data.GenericRepositoryImpl;
import com.ooluk.mdm.core.base.data.PropertyGroup;

/**
 * Repository implementation for property groups.
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
@Repository
public class PropertyGroupRepositoryImpl 
	extends GenericRepositoryImpl<PropertyGroup> implements PropertyGroupRepository {	

	public PropertyGroupRepositoryImpl() {
		super(PropertyGroup.class);
	}
	
	@Override
	public PropertyGroup findByName(String name) {
		Session session = sessionFactory.getCurrentSession();
		return (PropertyGroup) session.createCriteria(PropertyGroup.class)
			.add(Restrictions.eq("name", name)).uniqueResult();
	}
}