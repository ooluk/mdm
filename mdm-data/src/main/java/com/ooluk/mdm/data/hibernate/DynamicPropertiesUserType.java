/* 
 *  Copyright 2016 Ooluk Corporation
 */
package com.ooluk.mdm.data.hibernate;

import java.io.IOException;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ooluk.mdm.data.meta.DynamicProperties;

/**
 * UserType implementation for {@link DynamicProperties} to JSON mapping.
 * 
 * Adapted from
 * http://stackoverflow.com/questions/15974474/mapping-postgresql-json-column-to-hibernate-value-type/16049086#16049086
 * 
 * @author Siddhesh Prabhu
 * @since 1.0
 *
 */
public class DynamicPropertiesUserType implements UserType {

	private ObjectMapper mapper = new ObjectMapper();

	@Override
	public int[] sqlTypes() {
		return new int[] { Types.JAVA_OBJECT };
	}

	@Override
	public Class<?> returnedClass() {
		return Map.class;
	}

	@Override
	public boolean equals(Object x, Object y) throws HibernateException {
		return Objects.equals(x, y);
	}

	@Override
	public int hashCode(Object x) throws HibernateException {
		return x.hashCode();
	}

	@SuppressWarnings ( "unchecked" )
	@Override
	public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner)
			throws HibernateException, SQLException {

		if (rs.getObject(names[0]) == null) {
			return null;
		}
		String json = rs.getString(names[0]);
		Map<String, Object> data = Collections.emptyMap();
		try {
			data = mapper.readValue(json, Map.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new DynamicProperties(data);
	}

	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index,
			SessionImplementor session) throws HibernateException, SQLException {

		String json = "{}";
		if (value == null) {
			st.setObject(index, json, Types.OTHER);
			return;
		}

		if (!(value instanceof DynamicProperties)) {
			throw new RuntimeException("Expected DynamicProperties found " + value.getClass());
		}

		try {
			DynamicProperties data = (DynamicProperties) value;
			json = mapper.writeValueAsString(data.getProperties());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		st.setObject(index, json, Types.OTHER);
	}

	@Override
	public Object deepCopy(Object value) throws HibernateException {
		if (value == null)
			return null;
		DynamicProperties original = (DynamicProperties) value;
		DynamicProperties copy = new DynamicProperties(original.getProperties());
		return copy;
	}

	@Override
	public boolean isMutable() {
		return true;
	}

	@Override
	public Serializable disassemble(Object value) throws HibernateException {
		return (String) this.deepCopy(value);
	}

	@Override
	public Object assemble(Serializable cached, Object owner) throws HibernateException {
		return this.deepCopy(cached);
	}

	@Override
	public Object replace(Object original, Object target, Object owner) throws HibernateException {
		return this.deepCopy(original);
	}
}