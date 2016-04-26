package com.ooluk.mdm.core.meta;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;

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
    
    @PersistenceContext
    protected EntityManager em;
    
	@Override
	public T findById(Serializable id) {
        T entity = em.find(cls, id);
        return entity;        
	}

	@Override
	public void create(T entity) {
        em.persist(entity);
	}

	@Override
	public void update(T entity) {
		// EntityManager has no saveOrUpdate equivalent
        getSession().saveOrUpdate(entity);
	}

	@Override
	public void delete(T entity) {
		// EntityManager's remove requires a persistent entity
        getSession().delete(entity);		
	}
	
	@SuppressWarnings ( "unchecked" )
	@Override
	public List<T> getAll() {
        Session session = em.unwrap(Session.class);
		return session.createCriteria(cls).list();
	}
	
	/**
	 * Convenience method to retrieve the current session.
	 *  
	 * @return current session
	 */
	protected Session getSession() {
		return em.unwrap(Session.class);
	}
}