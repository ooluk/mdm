package com.ooluk.mdm.core.meta;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

public class DynamicPropertyCacheTest {
	
	private List<DynamicProperty> getProperties() {
		List<DynamicProperty> list = new ArrayList<>();
		for (int i = 1; i <=5; i++) {
			DynamicProperty p = new DynamicProperty()
				.setId(Long.valueOf(i))
				.setKey("K"+i)
				.setMetaObjectType(MetaObjectType.LABEL);
			list.add(p);
		}
		return list;
	}
	
	private DynamicPropertiesCache buildTestCache() {
		DynamicPropertiesCache cache = new DynamicPropertiesCache();
		DynamicPropertyRepository repo = Mockito.mock(DynamicPropertyRepository.class);
		Mockito.when(repo.getAll()).thenReturn(getProperties());
		cache.setRepository(repo);
		cache.build();
		return cache;
	}
	
	@Test
	public void build() {
		DynamicPropertiesCache cache = buildTestCache();
		List<DynamicProperty> props = cache.getAll();
		assertEquals(5, props.size());
		for (DynamicProperty dp : props) {
			Long id = dp.getId();
			assertEquals("K"+id, dp.getKey());
			assertEquals(MetaObjectType.LABEL, dp.getMetaObjectType());
		}
	}
	
	@Test
	public void get() {
		DynamicPropertiesCache cache = buildTestCache();
		DynamicProperty dp = cache.get("K2");
		assertEquals(Long.valueOf(2L), dp.getId());
		assertEquals(MetaObjectType.LABEL, dp.getMetaObjectType());
	}
}