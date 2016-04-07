package com.ooluk.mdm.core.base.data;

import java.io.Serializable;
import java.util.List;

/**
 * GenericRepository defines generic repository methods (CRUD) to 
 * <ul>
 * <li>Fetch an entity by ID
 * <li>Create an entity
 * <li>Update an entity
 * <li>Delete an entity.
 * <li>Fetch all entities
 * </ul>>
 * 
 * @author Siddhesh Prabhu
 * @since 1.0
 */
public interface GenericRepository <T extends MetaObject> {

	/**
	 * Fetches an entity by its ID. The parameter ID is defined to be Serializable to allow any type
	 * of primary key. TODO Ideally we should have defined a base class for all primary keys but
	 * then we would need to wrap the most commonly used key type "Long" in an embedded key.
	 * 
	 * @param id
	 *            the ID of the entity
	 * 
	 * @return the entity identified by the ID
	 */
    public T findById(Serializable id);
    
	/**
	 * Creates an entity.
	 * 
	 * @param entity
	 *            entity to be created
	 */
    public void create(T entity);
    
	/**
	 * Updates an existing entity.
	 * 
	 * @param entity
	 *            the new image of the entity
	 */
    public void update(T entity);

	/**
	 * Deletes an entity.
	 * 
	 * @param entity
	 *            the entity to be deleted
	 */
    public void delete(T entity);
    
    /**
     * Returns all entities
     * 
     * @return list of all entities
     */
    public List<T> getAll();
}