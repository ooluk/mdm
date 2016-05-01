package com.ooluk.mdm.data.meta;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

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
		Session session = getSession();
		return (PropertyGroup) session.createCriteria(PropertyGroup.class)
			.add(Restrictions.eq("name", name)).uniqueResult();
	}
}