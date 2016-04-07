package com.ooluk.mdm.core.app.repository;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.ooluk.mdm.core.app.data.Tag;
import com.ooluk.mdm.core.base.data.GenericRepositoryImpl;

/**
 * Repository implementation for tags.
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
@Repository
public class TagRepositoryImpl extends GenericRepositoryImpl<Tag> 
		implements TagRepository {	

	public TagRepositoryImpl() {
		super(Tag.class);
	}
	
	@Override
	public Tag findByName(String name) {
		Session session = getSession();
		return (Tag) session.createCriteria(Tag.class)
			.add(Restrictions.eq("name", name)).uniqueResult();
	}
}