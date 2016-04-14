package com.ooluk.mdm.core.meta.attribute;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.ooluk.mdm.core.meta.GenericRepositoryImpl;
import com.ooluk.mdm.core.meta.dataobject.DataObject;

/**
 * Repository implementation for namespaces.
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
@Repository
public class AttributeRepositoryImpl extends GenericRepositoryImpl<Attribute> 
		implements AttributeRepository {	

	public AttributeRepositoryImpl() {
		super(Attribute.class);
	}
	
	@Override
	public Attribute findByName(DataObject dataObject, String name) {
		Session session = getSession();
		return (Attribute) session.createCriteria(Attribute.class)
			.add(Restrictions.eq("dataObject", dataObject))
			.add(Restrictions.eq("name", name)).uniqueResult();
	}
}