package com.ooluk.mdm.core.test.database;

/**
 * Interface for common SQL actions against a database table. Each table that wants to provide a
 * single entry point for common SQL operations against it must implement this interface. The
 * implementation must rely on JDBC-SQL to provide a verification of ORM functionality.
 * 
 * @author Siddhesh Prabhu
 * @since 1.0
 * @deprecated
 *
 */
@Deprecated
public interface DatabaseTable<T> {

	/**
	 * Inserts an entity into the table.
	 * 
	 * @param id
	 *            entity ID
	 */
	public void insert(Long id, T entity);
	
	/**
	 * Returns the entity with the specified id
	 * 
	 * @param id
	 *            entity ID
	 *            
	 * @return entity
	 */
	public T select(Long id);
	
	/**
	 * Delete the specified entity
	 * 
	 * @param id
	 *            entity ID
	 */
	public void delete(Long id);
}