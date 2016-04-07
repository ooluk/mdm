package com.ooluk.mdm.core.app.repository;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.ooluk.mdm.core.app.data.Label;
import com.ooluk.mdm.core.app.data.LabelType;
import com.ooluk.mdm.core.base.data.GenericRepositoryImpl;

/**
 * Repository implementation for labels.
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
@Repository
public class LabelRepositoryImpl extends GenericRepositoryImpl<Label> 
		implements LabelRepository {	

	public LabelRepositoryImpl() {
		super(Label.class);
	}
	
	@Override
	public Label findByTypeAndName(LabelType type, String name) {
		Session session = getSession();
		return (Label) session.createCriteria(Label.class)
			.add(Restrictions.eq("type", type))
			.add(Restrictions.eq("name", name)).uniqueResult();
	}
	
	@SuppressWarnings ( "unchecked" )
	@Override
	public List<Label> findByType(LabelType type) {
		Session session = getSession();
		return session.createCriteria(Label.class)
				.add(Restrictions.eq("type", type)).list();
	}
	
	@SuppressWarnings ( "unchecked" )
	@Override
	public List<Label> findRootLabels() {
		Session session = getSession();
		return session.createCriteria(Label.class)
				.add(Restrictions.isEmpty("parents")).list();		
	}
}