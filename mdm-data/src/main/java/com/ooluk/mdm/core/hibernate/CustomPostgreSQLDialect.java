/* 
 *  Copyright 2016 Ooluk Corporation
 */
package com.ooluk.mdm.core.hibernate;

import java.sql.Types;

import org.hibernate.dialect.PostgreSQL94Dialect;

/**
 * A customization of PostgreSQL94Dialect initially designed to support jsonb and translate VARCHAR
 * to TEXT for hibernate schema generation.
 * 
 * @author Siddhesh Prabhu
 * @since 1.0
 *
 */
public class CustomPostgreSQLDialect extends PostgreSQL94Dialect {

	/**
	 * Constructs a CustomPostgreSQLDialect
	 */
	public CustomPostgreSQLDialect() {
		super();
		this.registerColumnType( Types.JAVA_OBJECT, "jsonb" );
		this.registerColumnType( Types.VARCHAR, "text" );
	}
}