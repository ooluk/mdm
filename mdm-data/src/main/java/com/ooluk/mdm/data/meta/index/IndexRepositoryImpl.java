package com.ooluk.mdm.data.meta.index;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.ooluk.mdm.data.meta.GenericRepositoryImpl;
import com.ooluk.mdm.data.meta.dataobject.DataObject;

/**
 * Repository implementation for indexes.
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
@Repository
public class IndexRepositoryImpl extends GenericRepositoryImpl<Index> 
		implements IndexRepository {	

	public IndexRepositoryImpl() {
		super(Index.class);
	}
	
	@Override
	public Index findByName(DataObject dataObject, String name) {
		Session session = getSession();
		return (Index) session.createCriteria(Index.class)
			.add(Restrictions.eq("dataObject", dataObject))
			.add(Restrictions.eq("name", name)).uniqueResult();
	}
}