package com.ooluk.mdm.core.meta.app;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.ooluk.mdm.core.meta.GenericRepositoryImpl;

/**
 * Repository implementation for label types.
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
@Repository
public class LabelTypeRepositoryImpl extends GenericRepositoryImpl<LabelType> 
		implements LabelTypeRepository {	

	public LabelTypeRepositoryImpl() {
		super(LabelType.class);
	}
	
	@Override
	public LabelType findByName(String name) {
		Session session = getSession();
		return (LabelType) session.createCriteria(LabelType.class)
			.add(Restrictions.eq("name", name)).uniqueResult();
	}
}