package com.ooluk.mdm.core.test;

import static com.ooluk.mdm.core.test.TestConstants.SCHEMA;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ooluk.mdm.core.base.data.DynamicProperties;

/**
 * A utility class for database functions.
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
public class DatabaseUtils {
	
	/**
	 * Returns the argument string surrounded with single quote. Also escapes any embedded single
	 * quotes with backslash (\)
	 * 
	 * @param value
	 *            string value
	 * 
	 * @return 'value'
	 */
	public static String quote(String value) {
		value = value.replace("'", "\'");
		return "'" + value + "'";
	}
	
	/**
	 * Returns a JSON string representation of the dynamic properties.
	 * 
	 * @param props
	 *            dynamic properties
	 *            
	 * @return JSON string representation
	 */
	public static String json(DynamicProperties props) {
		String json = "{}";
		try {
			json = props.toJson();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * Parses a JSON string into DynamicProperties.
	 * 
	 * @param json
	 *            JSON string
	 * 
	 * @return DynamicProperties instance
	 */
	public static DynamicProperties parseJson(String json) {
		try {
			return DynamicProperties.parseJson(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Creates a comma separated list of the specified fields.
	 * 
	 * @param fields
	 *            list of fields
	 *            
	 * @return comma separated list of the specified fields.
	 */
	private static String fieldString(String[] fields) {
		StringBuilder f = new StringBuilder();
		for (int i = 0; i < fields.length - 1; i++) {
			f.append(fields[i]).append(",");
		}
		f.append(fields[fields.length-1]);
		return f.toString();
	}
	
	/**
	 * Generates a SQL insert statement.
	 * 
	 * @param table
	 *            table name
	 * @param fields
	 *            list of fields
	 * @param values
	 *            comma separated list of values
	 *            
	 * @return SQL insert statement
	 */
	public static String insertSQL(String table, String[] fields, String values) {
		StringBuilder sql = new StringBuilder();
		sql.append("insert into ")
			.append(SCHEMA).append(".").append(table).append(" ")
			.append("(").append(fieldString(fields)).append(") values (")
			.append(values)
			.append(");");
		return sql.toString();
	}
	
	/**
	 * Generates a SQL select statement.
	 * 
	 * @param table
	 *            table name
	 * @param fields
	 *            list of fields
	 * @param idField
	 *            primary key column OR null if no WHERE is required
	 * @param id
	 *            table primary key
	 *            
	 * @return SQL select statement
	 */
	public static String selectSQL(String table, String[] fields, String idField, Long id) {
		StringBuilder sql = new StringBuilder();
		sql.append("select ").append(fieldString(fields))
			.append(" from ").append(SCHEMA).append(".").append(table);
		if (idField != null)
			sql.append(" where ").append(idField).append(" = ").append(id);
		return sql.toString();
	}
	
	/**
	 * Generates a SQL delete statement.
	 * 
	 * @param table
	 *            table name
	 * @param idField
	 *            primary key column
	 * @param id
	 *            table primary key
	 *            
	 * @return SQL select statement
	 */
	public static String deleteSQL(String table, String idField, Long id) {
		StringBuilder sql = new StringBuilder();
		sql.append("delete from ")
			.append(SCHEMA).append(".").append(table)
			.append(" where ").append(idField).append(" = ").append(id);
		return sql.toString();
	}
}
