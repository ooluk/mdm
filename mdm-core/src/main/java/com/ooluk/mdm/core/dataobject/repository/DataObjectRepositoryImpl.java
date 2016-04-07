package com.ooluk.mdm.core.dataobject.repository;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.stereotype.Repository;

import com.ooluk.mdm.core.app.data.Label;
import com.ooluk.mdm.core.base.data.GenericRepositoryImpl;
import com.ooluk.mdm.core.dataobject.data.DataObject;
import com.ooluk.mdm.core.dataobject.data.Namespace;

/**
 * Repository implementation for data objects.
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
@Repository
public class DataObjectRepositoryImpl extends GenericRepositoryImpl<DataObject> 
		implements DataObjectRepository {	

	public DataObjectRepositoryImpl() {
		super(DataObject.class);
	}
	
	@Override
	public DataObject findByName(Namespace namespace, String name) {
		Session session = getSession();
		return (DataObject) session.createCriteria(DataObject.class)
			.add(Restrictions.eq("namespace", namespace))
			.add(Restrictions.eq("name", name)).uniqueResult();
	}
	
	@SuppressWarnings ( "unchecked" )
	@Override
	public List<DataObject> findByNamespace(Namespace namespace) {
		Session session = getSession();
		return session.createCriteria(DataObject.class)
			.add(Restrictions.eq("namespace", namespace)).list();		
	}

	@SuppressWarnings ( "unchecked" )
	@Override
	public List<DataObject> findByLabels(List<Label> labels) {
		
		// Create a list of labels IDs and use in an in clause
		List<Long> ids = new ArrayList<>(); 
		for (Label lbl : labels) {
			ids.add(lbl.getId());
		}
		
		Session session = getSession();
		Criteria criteria = session.createCriteria(DataObject.class, "DataObject");
		for(Label label : labels){
            DetachedCriteria subquery = DetachedCriteria.forClass(DataObject.class,"obj")
            		.add(Restrictions.eqProperty("obj.id", "DataObject.id"))
            		.createAlias("obj.labels", "label")
            		.add(Restrictions.eq("label.id",label.getId()))
            		.setProjection(Projections.property("obj.id"));
            criteria.add(Subqueries.exists(subquery));  
        }
		return criteria.list();
	}
}