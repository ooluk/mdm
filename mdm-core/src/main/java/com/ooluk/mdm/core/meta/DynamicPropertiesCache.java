package com.ooluk.mdm.core.meta;

import java.util.HashMap;
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
	
	private static final long DEFAULT_PGROUP_ID = 0L;
	
	private DynamicPropertyRepository propertyStore;
	
	/*
	 * The cache is maintained as
	 * 
	 * <pre>
	 * 
	 * MetaObjectType-1 --> 
	 * 		PropertyGroupID-11 --> 
	 * 				[property-key111 --> DynamicProperty111, property-key112 --> DynamicProperty112, ...] 
	 * 		PropertyGroupID-12 --> 
	 * 				[property-key121 --> DynamicProperty121, property-key122 --> DynamicProperty122, ...]
	 *                      
	 * MetaObjectType-2 -->
	 * 		PropertyGroupID-21 --> 
	 * 				[property-key211 --> DynamicProperty211, property-key212 --> DynamicProperty212, ...] 
	 * 		PropertyGroupID-22 --> 
	 * 				[property-key221 --> DynamicProperty221, property-key222 --> DynamicProperty222, ...]
	 *                      
	 * </pre>
	 */
	private Map<MetaObjectType, Map<Long, Map<String, DynamicProperty>>> cache;
	
	/*
	 * This cache maintains a mapping of a DynamicProperty key with its definition. 
	 */
	Map<String, DynamicProperty> keyCache;

	
	public DynamicPropertiesCache() {		
		cache = new ConcurrentHashMap<>();
		for (MetaObjectType type : MetaObjectType.values()) {
			cache.put(type, new ConcurrentHashMap<>());
		}
		buildCache();
	}
	
	private void buildCache() {		
		List<DynamicProperty> props = propertyStore.getAll();
		for (DynamicProperty prop : props) {
			insertIntoCache(prop);
		}
	}
	
	private void insertIntoCache(DynamicProperty prop) {
		MetaObjectType type = prop.getMetaObjectType();
		if (type.isUniversal()) {
			insertUniversalType(prop);
		} else {
			insertNonUniversalType(prop);
		}		
	}

	private void insertUniversalType(DynamicProperty prop) {
		Map<Long, Map<String, DynamicProperty>> typeProps = cache.get(prop.getType());
		if (typeProps.isEmpty()) {
			typeProps.put(DEFAULT_PGROUP_ID, new HashMap<>());			
		}
		Map<String, DynamicProperty> pgProps = typeProps.get(DEFAULT_PGROUP_ID);
		pgProps.put(prop.getKey(), prop);
	}
	
	private void insertNonUniversalType(DynamicProperty prop) {
		Map<Long, Map<String, DynamicProperty>> typeProps = cache.get(prop.getType());
		for (PropertyGroup pg : prop.getPropertyGroups()) {
			if (!typeProps.containsKey(pg.getId())) {
				typeProps.put(pg.getId(), new HashMap<>());	
			}
			Map<String, DynamicProperty> pgProps = typeProps.get(pg.getId());
			pgProps.put(prop.getKey(), prop);			
		}
	}
	
	@Override
	public String toString() {
		return "DynamicPropertiesCache [cache=" + cache + "]";
	}	 
}