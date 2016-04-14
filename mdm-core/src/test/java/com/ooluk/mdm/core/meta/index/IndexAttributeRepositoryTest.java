package com.ooluk.mdm.core.meta.index;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.springframework.context.annotation.Configuration;

import com.ooluk.mdm.core.meta.attribute.Attribute;
import com.ooluk.mdm.core.meta.dataobject.DataObject;
import com.ooluk.mdm.core.meta.index.Index;
import com.ooluk.mdm.core.meta.index.IndexAttributeMapping;
import com.ooluk.mdm.core.meta.index.IndexAttributeMappingKey;
import com.ooluk.mdm.core.test.AbstractRepositoryTest;
import com.ooluk.mdm.core.test.EntityComparator;
import com.ooluk.mdm.core.test.Tables;
import com.ooluk.mdm.core.test.TestData;

/**
 * <pre>
 * ----------
 * TEST SCOPE
 * ----------
 * - Create an index-attribute mapping
 * - Update an index-attribute mapping
 * - Delete an index-attribute mapping
 * - Find a index-attribute mapping from the index and attribute
 * # Find all attributes of an index
 * # Find all indexes on an attribute 
 * </pre>
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
public class IndexAttributeRepositoryTest extends AbstractRepositoryTest {

	@Configuration
	static class ContextConfiguration {		
	}
	
	private Index getDefaultIndex() {
		return TestData.getIndex("NS1", "O1", "I1");
	}
		
	/**
	 * Creates a default index in the database.
	 * 
	 * @return created index
	 */
	private Index createDefaultIndex() {
		Index idx = getDefaultIndex();
		DataObject obj = idx.getDataObject();
		database.insertPropertyGroup(obj.getNamespace().getPropertyGroup(), 1L);
		database.insertNamespace(obj.getNamespace(), 1L);
		database.insertDataObject(obj, 1L);
		database.insertIndex(idx, 1L);
		return idx;
		
	}
	
	private IndexAttributeMapping getMapping(Index idx, Attribute attr) {
		IndexAttributeMapping map = new IndexAttributeMapping(idx, attr);
		map.setProperties(TestData.getDynamicProperties());
		return map;
	}
	
	private IndexAttributeMapping fetchFromRepository(Index idx, Attribute attr) {
		Index i = idxRepository.findById(idx.getId());
		Attribute a = attrRepository.findById(attr.getId());
		// Need to pass persistent entities
		IndexAttributeMapping map = iaRepository
				.findById(new IndexAttributeMappingKey(i, a));
		return map;
	}
		
	/*
	 * Test create()
	 */		
	@Test
	public void create() {
		Index idx = createDefaultIndex();
		super.verifyRowCount(Tables.INDEX_ATTRIBUTE, 0);
		
		// Add attribute to the data object
		DataObject obj = idx.getDataObject();
		Attribute a1 = new Attribute(obj, "A1");
		database.insertAttribute(a1, 1L);
		
		// Add 1 attribute to the index
		database.insertIndexAttribute(getMapping(idx, a1));
		super.verifyRowCount(Tables.INDEX_ATTRIBUTE, 1);
		
		// Verify
		Set<IndexAttributeMapping> exp = new HashSet<>();
		exp.add(getMapping(idx, a1));
		idx = idxRepository.findById(idx.getId());
		EntityComparator.verifyLists(exp, idx.getAttributes());
	}
	
	/*
	 * Test update()
	 */			
	private void makeChanges(IndexAttributeMapping map) {
		// For dynamic properties, add new, update existing and remove existing
		map.getProperties()
			.setProperty("new-property", "new-value")
			.setProperty("text", "text-value-new")
			.removeProperty("number");		
	}
	
	@Test
	public void update() {
		Index idx = createDefaultIndex();
		super.verifyRowCount(Tables.INDEX_ATTRIBUTE, 0);
		
		// Add attribute to the data object
		DataObject obj = idx.getDataObject();
		Attribute a1 = new Attribute(obj, "A1");
		database.insertAttribute(a1, 1L);
		
		// Add 1 attribute to the index
		database.insertIndexAttribute(getMapping(idx, a1));
		super.verifyRowCount(Tables.INDEX_ATTRIBUTE, 1);
		
		// Update
		IndexAttributeMapping map = fetchFromRepository(idx, a1);
		makeChanges(map);
		iaRepository.update(map);
		super.flushAndEvict(map);
		
		// Verify by making same changes to expected
		IndexAttributeMapping exp = getMapping(idx, a1);
		makeChanges(exp);
		IndexAttributeMapping act = fetchFromRepository(idx, a1);
		EntityComparator.verifyEqual(exp, act);
	}		
	
	/*
	 * Test delete() and findById()
	 */	
	@Test
	public void delete() {
		Index idx = createDefaultIndex();
		
		// Add attribute to the data object
		DataObject obj = idx.getDataObject();
		Attribute a1 = new Attribute(obj, "A1");
		database.insertAttribute(a1, 1L);
		
		// Add 1 attribute to the index
		database.insertIndexAttribute(getMapping(idx, a1));
		super.verifyRowCount(Tables.INDEX_ATTRIBUTE, 1);
		
		// Delete mapping
		IndexAttributeMapping map = fetchFromRepository(idx, a1);
		iaRepository.delete(map);
		super.flushAndEvict(map);
		super.verifyRowCount(Tables.INDEX_ATTRIBUTE, 0);
	}
	
	/*
	 * Test indexes on attribute
	 */
	@Test
	public void _indexesOnAttribute() {
		Index idx = createDefaultIndex();
		
		// Add attribute to the data object
		DataObject obj = idx.getDataObject();
		Attribute a1 = new Attribute(obj, "A1");
		database.insertAttribute(a1, 1L);
		
		// Add attributes to the index
		database.insertIndexAttribute(getMapping(idx, a1));
		// Create new index on a1
		Index idx2 = new Index("I2");
		obj.addIndex(idx2);
		database.insertIndex(idx2, 2L);
		database.insertIndexAttribute(getMapping(idx2, a1));
		super.verifyRowCount(Tables.INDEX_ATTRIBUTE, 2);
		
		// Verify there are 2 indexes on a1
		a1 = attrRepository.findById(a1.getId());
		assertEquals(2, a1.getIndexes().size());
		
	}
}