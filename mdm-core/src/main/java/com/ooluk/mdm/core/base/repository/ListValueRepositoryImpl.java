package com.ooluk.mdm.core.base.repository;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.ooluk.mdm.core.base.data.GenericRepositoryImpl;
import com.ooluk.mdm.core.base.data.ListValue;

/**
 * Repository implementation for list value.
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
@Repository
public class ListValueRepositoryImpl 
	extends GenericRepositoryImpl<ListValue> implements ListValueRepository {	

	public ListValueRepositoryImpl() {
		super(ListValue.class);
	}
	
	@Override
	public ListValue findByValue(String value) {
		Session session = sessionFactory.getCurrentSession();
		return (ListValue) session.createCriteria(ListValue.class)
			.add(Restrictions.eq("value", value)).uniqueResult();
	}
}