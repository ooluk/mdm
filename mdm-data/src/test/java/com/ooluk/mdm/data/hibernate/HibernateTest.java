package com.ooluk.mdm.data.hibernate;

import org.junit.Test;

public class HibernateTest {
	
	@Test
	public void buildTables() {
		HibernateUtils.getSessionFactory();
	}
}