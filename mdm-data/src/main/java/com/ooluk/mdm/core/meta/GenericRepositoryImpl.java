package com.ooluk.mdm.core.meta;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * GenericRepository implementation.
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
public abstract class GenericRepositoryImpl<T extends MetaObject> implements GenericRepository<T> {
    
    private final Class<T> cls;
    
    public GenericRepositoryImpl(Class<T> cls) {
        this.cls = cls;
    }
    
    @Autowired
    protected SessionFactory sessionFactory;
    
	@Override
	public T findById(Serializable id) {
        Session session = getSession();
        T entity = session.get(cls, id);
        return entity;        
	}

	@Override
	public void create(T entity) {
        Session session = getSession();
        session.save(entity);
	}

	@Override
	public void update(T entity) {
        Session session = getSession();
        session.saveOrUpdate(entity);
	}

	@Override
	public void delete(T entity) {
        Session session = getSession();
        session.delete(entity);		
	}
	
	@SuppressWarnings ( "unchecked" )
	@Override
	public List<T> getAll() {
        Session session = getSession();
		return session.createCriteria(cls).list();
	}
	
	/**
	 * Convenience method to retrieve the current session.
	 *  
	 * @return current session
	 */
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
}