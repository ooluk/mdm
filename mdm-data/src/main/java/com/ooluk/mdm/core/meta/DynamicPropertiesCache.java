package com.ooluk.mdm.core.meta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class maintains dynamic property information in-memory for speedy retrieval.
 * 
 * @author Siddhesh Prabhu
 * @since 1.0
 *
 */
public class DynamicPropertiesCache {
	
	private DynamicPropertyRepository repository;

	/*
	 * This cache maintains a mapping of a DynamicProperty key with its definition. 
	 */
	Map<String, DynamicProperty> cache;
	
	public DynamicPropertiesCache() {		
		cache = new ConcurrentHashMap<>();
	}
	
	/**
	 * Sets the dynamic property repository.
	 * 
	 * @param repository dynamic property repository
	 */
	public void setRepository(DynamicPropertyRepository repository) {
		this.repository = repository;
	}

	/**
	 * Refresh a dynamic property in the cache is it exists, adds it if not.
	 * 
	 * @param dp
	 *            dynamic property
	 */
	public void refresh(DynamicProperty dp) {
		cache.put(dp.getKey(), dp);
	}
	
	/**
	 * Builds or rebuilds the cache.
	 */
	public void build() {		
		List<DynamicProperty> props = repository.getAll();
		for (DynamicProperty dp : props) {
			refresh(dp);
		}
	}
	
	/**
	 * Returns the dynamic property for the specified key.
	 * 
	 * @param key
	 *            property key
	 *            
	 * @return dynamic property
	 */
	public DynamicProperty get(String key) {
		return cache.get(key);
	}
	
	/*
	 * Package private method for testing.
	 */
	List<DynamicProperty> getAll() {
		return new ArrayList<>(cache.values());
	}
	
	@Override
	public String toString() {
		return "DynamicPropertiesCache [cache=" + cache + "]";
	}	 
}