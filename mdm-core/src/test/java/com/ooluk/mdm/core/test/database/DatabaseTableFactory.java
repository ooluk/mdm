package com.ooluk.mdm.core.test.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ooluk.mdm.core.base.data.PropertyGroup;

/**
 * A factory for retrieving DatabaseTable instances.
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 * @deprecated
 *
 */
@Deprecated
@Component
public class DatabaseTableFactory {
	
	@Autowired
	private PropertyGroupTable pgTable;
		
	public DatabaseTable<?> getDatabaseTable(Class<?> cls) {
		
		if (cls.equals(PropertyGroup.class)) {
			return pgTable;
		}
		
		return null;
	}
}