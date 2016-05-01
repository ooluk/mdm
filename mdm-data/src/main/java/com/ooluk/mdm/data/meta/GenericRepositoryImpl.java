package com.ooluk.mdm.data.meta;

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
    
    //@PersistenceContext
    //protected EntityManager em;
    
    @Autowired
    private SessionFactory sf;
    
	@Override
	public T findById(Serializable id) {
        T entity = getSession().get(cls, id);
        return entity;        
	}

	@Override
	public void create(T entity) {
		getSession().persist(entity);
	}

	@Override
	public void update(T entity) {
        getSession().saveOrUpdate(entity);
	}

	@Override
	public void delete(T entity) {
		getSession().delete(entity); 		
	}
	
	@SuppressWarnings ( "unchecked" )
	@Override
	public List<T> getAll() {
		return getSession().createCriteria(cls).list();
	}
	
	/**
	 * Convenience method to retrieve the current session.
	 *  
	 * @return current session
	 */
	protected Session getSession() {
		return sf.getCurrentSession();
	}
}