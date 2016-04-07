package com.ooluk.mdm.core.dataobject.repository;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.ooluk.mdm.core.base.data.GenericRepositoryImpl;
import com.ooluk.mdm.core.dataobject.data.Namespace;

/**
 * Repository implementation for namespaces.
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
@Repository
public class NamespaceRepositoryImpl extends GenericRepositoryImpl<Namespace> 
		implements NamespaceRepository {	

	public NamespaceRepositoryImpl() {
		super(Namespace.class);
	}
	
	@Override
	public Namespace findByName(String name) {
		Session session = getSession();
		return (Namespace) session.createCriteria(Namespace.class)
			.add(Restrictions.eq("name", name)).uniqueResult();
	}
}