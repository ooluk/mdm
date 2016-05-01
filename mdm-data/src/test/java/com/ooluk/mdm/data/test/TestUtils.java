package com.ooluk.mdm.data.test;

import java.lang.reflect.Field;

import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.test.util.ReflectionTestUtils;

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
	
	/**
	 * Unwrap a bean if it is wrapped in an AOP proxy.
	 * 
	 * @param bean
	 *            the proxy or bean.
	 *            
	 * @return the bean itself if not wrapped in a proxy or the bean wrapped in the proxy.
	 */
	public static Object unwrapProxy(Object bean) {
		if (AopUtils.isAopProxy(bean) && bean instanceof Advised) {
			Advised advised = (Advised) bean;
			try {
	            bean = advised.getTargetSource().getTarget();
            } catch (Exception e) {
	            e.printStackTrace();
            }
		}
		return bean;
	}
	
	/**
	 * Sets a mock in a bean wrapped in a proxy or directly in the bean if there is no proxy.
	 * 
	 * @param bean
	 *            bean itself or a proxy
	 * @param mockName
	 *            name of the mock variable
	 * @param mockValue
	 *            reference to the mock
	 */
	public static void setMockToProxy(Object bean, String mockName, Object mockValue) {
		ReflectionTestUtils.setField(unwrapProxy(bean), mockName, mockValue);
	}
}