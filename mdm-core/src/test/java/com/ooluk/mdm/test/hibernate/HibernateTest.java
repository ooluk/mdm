package com.ooluk.mdm.test.hibernate;

import org.junit.Test;

public class HibernateTest {
	
	@Test
	public void buildTables() {
		HibernateUtils.getSessionFactory();
	}
}