package com.ooluk.mdm.core.test.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * A convenience abstract superclass for DatabaseTable interface.
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 * @deprecated
 *
 */
@Deprecated
public abstract class AbstractDatabaseTable<T> implements DatabaseTable<T> {

	@Autowired
	protected JdbcTemplate jdbc;

	@Override
	public void insert(Long id, T entity) {
		throw new UnsupportedOperationException();
	}

	@Override
	public T select(Long id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void delete(Long id) {
		throw new UnsupportedOperationException();		
	}
}