/* 
 *  Copyright 2016 Ooluk Corporation
 */
package com.ooluk.mdm.data.hibernate;

import java.util.Map;

import org.apache.lucene.document.Document;
import org.hibernate.search.bridge.FieldBridge;
import org.hibernate.search.bridge.LuceneOptions;

import com.ooluk.mdm.data.meta.DynamicProperties;
import com.ooluk.mdm.data.meta.DynamicProperty;

/**
 * A field bridge for {@link DynamicProperties} to allow customized indexing with Hibernate Search.
 * Only dynamic properties defined as searchable will be indexed. For this to work correctly it is
 * vital that the key used in {@link DynamicProperties} matches the <code>key</code> attribute of
 * the corresponding {@link DynamicProperty}.
 * 
 * @author Siddhesh Prabhu
 * @since 1.0
 *
 */
public class DynamicPropertyFieldBridge implements FieldBridge {

	@Override
	public void set(String name, Object value, Document document, LuceneOptions luceneOptions) {

		DynamicProperties dp = (DynamicProperties) value;
		Map<String, Object> map = dp.getProperties();
		for (String key : map.keySet()) {
			if (isSearchable(key)) {
				String val = map.get(key).toString();
				if (val != null) {
					luceneOptions.addFieldToDocument(key, val, document);
				}
			}
		}
	}

	/**
	 * Checks if the specified key is defined to be a searchable dynamic property.
	 * 
	 * @param key
	 *            dynamic property key; this is also the key for the item in DynamicProperties.
	 * 
	 * @return true if searchable, false otherwise
	 */
	private boolean isSearchable(String key) {
		// TODO 
		return true;
	}
}