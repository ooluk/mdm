package com.ooluk.mdm.core.meta;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

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
		Session session = getSession();
		return (ListValue) session.createCriteria(ListValue.class)
			.add(Restrictions.eq("value", value)).uniqueResult();
	}
}