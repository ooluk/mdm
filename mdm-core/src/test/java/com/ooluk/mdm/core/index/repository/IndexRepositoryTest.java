package com.ooluk.mdm.core.index.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.springframework.context.annotation.Configuration;

import com.ooluk.mdm.core.dataobject.data.DataObject;
import com.ooluk.mdm.core.index.data.Index;
import com.ooluk.mdm.core.test.AbstractRepositoryTest;
import com.ooluk.mdm.core.test.EntityComparator;
import com.ooluk.mdm.core.test.Tables;
import com.ooluk.mdm.core.test.TestData;
import com.ooluk.mdm.core.test.TestUtils;

/**
 * <pre>
 * ----------
 * TEST SCOPE
 * ----------
 * - Find index by its ID
 * - Create an index
 * - Update an index
 * - Delete an index
 * - Find a index by its name within a data object
 * + Get all indexes for a data object 
 * </pre>
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
public class IndexRepositoryTest extends AbstractRepositoryTest {

	@Configuration
	static class ContextConfiguration {		
	}
	
	private static String IDX_NAME = "IDX1";
	
	/**
	 * Returns a transient default index
	 * 
	 * @return index
	 */
	private Index getDefaultIndex() {
		return TestData.getIndex("NS1", "OBJ1", IDX_NAME);
	}
	
	private Index createDefaultIndex() {
		Index idx = getDefaultIndex();
		DataObject obj = idx.getDataObject();
		database.insertPropertyGroup(obj.getNamespace().getPropertyGroup(), 1L);
		database.insertNamespace(obj.getNamespace(), 1L);
		database.insertDataObject(obj, 1L);
		database.insertIndex(idx, 1L);
		return idx;
	}
	
	/*
	 * Test findById()
	 */		
	@Test
	public void findById() {
		Index exp = createDefaultIndex();
		Index act = idxRepository.findById(exp.getId());
		EntityComparator.verifyEqual(exp, act);
		
		// Bad ID
		act = idxRepository.findById(exp.getId()+1);
		assertNull(act);
	}	
	
	/*
	 * Test create()
	 */	
	@Test
	public void create() {
		
		DataObject obj = TestData.getDataObject("NS1", "O1");
		database.insertPropertyGroup(obj.getNamespace().getPropertyGroup(), 1L);
		database.insertNamespace(obj.getNamespace(), 1L);
		database.insertDataObject(obj, 1L);
		
		// Create index
		Index idx = getDefaultIndex();
		idx.setDataObject(obj);
		idxRepository.create(idx);
		super.flushAndEvict(idx);

		Index exp = getDefaultIndex();
		exp.setDataObject(obj);
		Index act = idxRepository.findById(idx.getId());
		EntityComparator.verifyEqual(exp, act);
	}
	
	/*
	 * Test update()
	 */
	private void makeChanges(Index idx) {
		idx.setName(idx.getName()+"-updated");
		// For dynamic properties, add new, update existing and remove existing
		idx.getProperties()
			.setProperty("new-property", "new-value")
			.setProperty("text", "text-value-new")
			.removeProperty("number");		
	}
	
	@Test
	public void update() {
		Index exp = createDefaultIndex();
		
		// Update
		Index idx = idxRepository.findById(exp.getId());
		makeChanges(idx);
		idxRepository.update(idx);
		flushAndEvict(idx);
		
		// Verify by making the same changes to expected
		makeChanges(exp);
		Index act = idxRepository.findById(idx.getId());
		EntityComparator.verifyEqual(exp, act);
	}
	
	/*
	 * Test delete()
	 */
	@Test
	public void delete() {
		Index idx = createDefaultIndex();
		super.verifyRowCount(Tables.INDEX, 1);
		idxRepository.delete(idx);
		flushAndEvict(idx);
		super.verifyRowCount(Tables.INDEX, 0);
	}
	
	/*
	 * Test findByName()
	 */
	@Test
	public void findByName() {	
		Index exp = createDefaultIndex();
		
		// Add another index
		Index idx2 = (new Index("I2")).setProperties(TestData.getDynamicProperties());
		idx2.setDataObject(exp.getDataObject());
		database.insertIndex(idx2, 2L);
		
		Index act = idxRepository.findByName(exp.getDataObject(), exp.getName());
		EntityComparator.verifyEqual(exp, act);
		
		// Bad name
		act = idxRepository.findByName(exp.getDataObject(), exp.getName().substring(1));
		assertNull(act);

		// Bad data object
		DataObject obj = new DataObject();
		TestUtils.setIdField(obj, exp.getDataObject().getId()+1);
		act = idxRepository.findByName(obj, exp.getName().substring(1));
		assertNull(act);
	}
	
	/*
	 * Test all indexes for a data object
	 */	
	@Test
	public void _getAllIndexes_For_DataObject() {	
		Index idx = createDefaultIndex();	
		DataObject obj = idx.getDataObject();
		
		// Add another index
		Index idx2 = (new Index("I2")).setProperties(TestData.getDynamicProperties());
		idx2.setDataObject(idx.getDataObject());
		database.insertIndex(idx2, 2L);
		
		// DataObject should already be detached in super.create() but clarifies the intention
		super.evict(obj);
		obj = objRepository.findById(idx.getDataObject().getId());
		assertEquals(2, obj.getIndexes().size());
	}
}