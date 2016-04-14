package com.ooluk.mdm.core.meta.dataobject;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.springframework.context.annotation.Configuration;

import com.ooluk.mdm.core.meta.dataobject.DataObject;
import com.ooluk.mdm.core.meta.dataobject.DataObjectSourceMapping;
import com.ooluk.mdm.core.meta.dataobject.DataObjectSourceMappingKey;
import com.ooluk.mdm.core.meta.dataobject.Namespace;
import com.ooluk.mdm.core.test.AbstractRepositoryTest;
import com.ooluk.mdm.core.test.EntityComparator;
import com.ooluk.mdm.core.test.Tables;
import com.ooluk.mdm.core.test.TestData;

/**
 * <pre>
 * ----------
 * TEST SCOPE
 * ----------
 * - Find a target-source mapping from the source and target data objects
 * - Create a target-source mapping for a data object
 * - Update a target-source mapping for a data object
 * - Delete a target-source mapping for a data object
 * # Find all sources of a data object
 * # Find all targets of a data object 
 * </pre>
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
public class DataObjectSourceRepositoryTest extends AbstractRepositoryTest {

	@Configuration
	static class ContextConfiguration {		
	}
				
	private Namespace createDefaultNamespace() {		
		Namespace ns = TestData.getNamespace("NS1");
		database.insertPropertyGroup(ns.getPropertyGroup(), 1L);
		database.insertNamespace(ns, 1L);
		return ns;		
	}
	
	private DataObject createDataObject(Namespace ns, String name, Long id) {
		DataObject obj = new DataObject(ns, name);
		database.insertDataObject(obj, id);
		return obj;		
	}
	
	private DataObjectSourceMapping getMapping(DataObject tgt, DataObject src) {
		DataObjectSourceMapping map = new DataObjectSourceMapping(tgt, src);
		map.setProperties(TestData.getDynamicProperties());
		return map;
	}
	
	private DataObjectSourceMapping createMapping(DataObject tgt, DataObject src) {
		DataObjectSourceMapping map = getMapping(tgt, src);
		database.insertDataObjectSource(map);
		return map;
	}
	
	private DataObjectSourceMapping fetchFromRepository(DataObject tgt, DataObject src) {
		DataObject t = objRepository.findById(tgt.getId());
		DataObject s = objRepository.findById(src.getId());
		// Need to pass persistent entities
		DataObjectSourceMapping map = osRepository
				.findById(new DataObjectSourceMappingKey(t, s));
		return map;
	}
	
	/*
	 * Test findById()
	 */	
	@Test
	public void findById() {
		
		Namespace ns = createDefaultNamespace();
		DataObject tgt = createDataObject(ns, "O1", 1L);
		DataObject src1 = createDataObject(ns, "O2", 2L);
		DataObject src2 = createDataObject(ns, "O2", 3L);
		
		// Add 2 sources to O1
		createMapping(tgt, src1);
		createMapping(tgt, src2);
		super.verifyRowCount(Tables.DATA_OBJECT_SOURCE, 2);
		
		DataObjectSourceMapping exp = getMapping(tgt, src1);
		DataObjectSourceMapping act = fetchFromRepository(tgt, src1);
		EntityComparator.verifyEqual(exp, act);
	}
		
	/*
	 * Test create()
	 */		
	@Test
	public void create() {
		
		Namespace ns = createDefaultNamespace();
		DataObject tgt = createDataObject(ns, "O1", 1L);
		DataObject src1 = createDataObject(ns, "O2", 2L);
		DataObject src2 = createDataObject(ns, "O2", 3L);
		
		// Add 2 sources
		createMapping(tgt, src1);
		createMapping(tgt, src2);
		super.verifyRowCount(Tables.DATA_OBJECT_SOURCE, 2);
		
		// Verify
		Set<DataObjectSourceMapping> exp = new HashSet<>();
		exp.add(getMapping(tgt, src1));
		exp.add(getMapping(tgt, src2));
		DataObject act = objRepository.findById(tgt.getId());
		EntityComparator.verifyLists(exp, act.getSources());
	}
	
	/*
	 * Test update()
	 */			
	private void makeChanges(DataObjectSourceMapping map) {
		// For dynamic properties, add new, update existing and remove existing
		map.getProperties()
			.setProperty("new-property", "new-value")
			.setProperty("text", "text-value-new")
			.removeProperty("number");		
	}
	
	@Test
	public void update() {
		
		Namespace ns = createDefaultNamespace();
		DataObject tgt = createDataObject(ns, "O1", 1L);
		DataObject src = createDataObject(ns, "O2", 2L);
		DataObjectSourceMapping exp = createMapping(tgt, src);
		
		// Update
		DataObjectSourceMapping map = fetchFromRepository(tgt, src);
		makeChanges(map);
		osRepository.update(map);
		super.flushAndEvict(map);
		
		// Verify 
		makeChanges(exp);
		DataObject act = objRepository.findById(tgt.getId());
		EntityComparator.verifyEqual(exp, act.getSources().iterator().next());
	}	
	
	/*
	 * Test delete()
	 */	
	@Test
	public void delete() {
		
		Namespace ns = createDefaultNamespace();
		DataObject tgt = createDataObject(ns, "O1", 1L);
		DataObject src = createDataObject(ns, "O2", 2L);
		createMapping(tgt, src);
		
		// Delete mapping
		DataObjectSourceMapping map = fetchFromRepository(tgt, src);
		osRepository.delete(map);
		super.flushAndEvict(map);
		super.verifyRowCount(Tables.DATA_OBJECT_SOURCE, 0);
	}
	
	/*
	 * Test getTargets() of DataObject
	 */	
	@Test
	public void _getTargets() {
		
		Namespace ns = createDefaultNamespace();
		DataObject src1 = createDataObject(ns, "O1", 1L);
		DataObject tgt1 = createDataObject(ns, "O2", 2L);
		DataObject tgt2 = createDataObject(ns, "O3", 3L);
		
		// Add 2 targets to src1
		createMapping(tgt1, src1);
		createMapping(tgt2, src1);
		super.verifyRowCount(Tables.DATA_OBJECT_SOURCE, 2);
				
		// Verify
		DataObject act = objRepository.findById(src1.getId());
		assertEquals(2, act.getTargets().size());		
	}
}