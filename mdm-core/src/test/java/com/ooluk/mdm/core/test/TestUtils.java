package com.ooluk.mdm.core.test;

import java.lang.reflect.Field;

/**
 * Utility methods for testing.
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
public class TestUtils {

	/**
	 * Sets the private id field on an entity.
	 * 
	 * @param obj
	 *            entity instance
	 * @param id
	 *            the ID value
	 */
	public static <T> void setIdField(T obj, Long id) {

		try {
			Field idField = null;
			Class<?> sc = obj.getClass();
			while (sc != null) {
				try {
					idField = sc.getDeclaredField("id");
					break;
				} catch (NoSuchFieldException ex) {
					sc = sc.getSuperclass();
				}
			}
			idField.setAccessible(true);
			idField.set(obj, id == null ? null : Long.valueOf(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}