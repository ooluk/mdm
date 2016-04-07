package com.ooluk.mdm.core.index.repository;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.springframework.context.annotation.Configuration;

import com.ooluk.mdm.core.attribute.data.Attribute;
import com.ooluk.mdm.core.dataobject.data.DataObject;
import com.ooluk.mdm.core.index.data.Index;
import com.ooluk.mdm.core.index.data.IndexAttributeMapping;
import com.ooluk.mdm.core.index.data.IndexAttributeMappingKey;
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
	
	private static String NS_NAME = "NS1";
	private static String OBJ_NAME = "OBJ1";
	private static String IDX_NAME = "IDX1";
	
	/**
	 * Creates a default index in the database.
	 * 
	 * @return created index
	 */
	private Index createDefaultIndex() {
		return super.createIndex(NS_NAME, OBJ_NAME, IDX_NAME);
	}
	
	private IndexAttributeMapping getMapping(Index idx, Attribute attr) {
		IndexAttributeMapping map = new IndexAttributeMapping(idx, attr);
		map.setProperties(TestData.getDynamicProperties());
		return map;
	}
	
	private IndexAttributeMapping createMapping(Index idx, Attribute attr) {
		IndexAttributeMapping map = getMapping(idx, attr);
		iaRepository.create(map);
		super.flushAndEvict(map);
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
		Attribute a1 = new Attribute(obj, "A-1");
		attrRepository.create(a1);
		super.flush();
		
		// Add 1 attribute to the index
		createMapping(idx, a1);
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
		Attribute a1 = new Attribute(obj, "A-1");
		attrRepository.create(a1);
		super.flush();
		
		// Add 1 attribute to the index
		IndexAttributeMapping map = createMapping(idx, a1);
		super.verifyRowCount(Tables.INDEX_ATTRIBUTE, 1);
		
		// Update
		makeChanges(map);
		iaRepository.update(map);
		super.flushAndEvict(map);
		
		// Verify by making same changes to expected
		IndexAttributeMapping exp = getMapping(idx, a1);
		makeChanges(exp);
		Index act = idxRepository.findById(idx.getId());
		EntityComparator.verifyEqual(exp, act.getAttributes().iterator().next());
	}		
	
	/*
	 * Test delete() and findById()
	 */	
	@Test
	public void delete() {
		Index idx = createDefaultIndex();
		
		// Add attribute to the data object
		DataObject obj = idx.getDataObject();
		Attribute a1 = new Attribute(obj, "A-1");
		attrRepository.create(a1);
		super.flush();
		
		// Add 1 attribute to the index
		IndexAttributeMapping map = createMapping(idx, a1);
		super.verifyRowCount(Tables.INDEX_ATTRIBUTE, 1);
		
		// Delete mapping
		map = iaRepository.findById(new IndexAttributeMappingKey(idx, a1)); 
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
		Attribute a1 = new Attribute(obj, "A-1");
		attrRepository.create(a1);
		super.flush();
		
		// Add attributes to the index
		createMapping(idx, a1);
		// Create new index on a1
		Index idx2 = new Index("I-2");
		obj.addIndex(idx2);
		idxRepository.create(idx2);
		super.flush();
		createMapping(idx2, a1);
		super.verifyRowCount(Tables.INDEX_ATTRIBUTE, 2);
		
		// Verify there are 2 indexes on a1
		super.evict(a1);
		a1 = attrRepository.findById(a1.getId());
		assertEquals(2, a1.getIndexes().size());
		
	}
}