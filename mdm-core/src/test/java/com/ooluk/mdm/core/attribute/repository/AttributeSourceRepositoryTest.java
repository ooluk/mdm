package com.ooluk.mdm.core.attribute.repository;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.springframework.context.annotation.Configuration;

import com.ooluk.mdm.core.attribute.data.Attribute;
import com.ooluk.mdm.core.attribute.data.AttributeSourceMapping;
import com.ooluk.mdm.core.attribute.data.AttributeSourceMappingKey;
import com.ooluk.mdm.core.dataobject.data.DataObject;
import com.ooluk.mdm.core.dataobject.data.Namespace;
import com.ooluk.mdm.core.test.AbstractRepositoryTest;
import com.ooluk.mdm.core.test.EntityComparator;
import com.ooluk.mdm.core.test.Tables;
import com.ooluk.mdm.core.test.TestData;

/**
 * <pre>
 * ----------
 * TEST SCOPE
 * ----------
 * - Find a target-source mapping from the source and target attributes
 * - Create a target-source mapping for an attribute
 * - Update a target-source mapping for an attribute
 * - Delete a target-source mapping for an attribute
 * # Find all sources of an attribute
 * # Find all targets of an attribute 
 * </pre>
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
public class AttributeSourceRepositoryTest extends AbstractRepositoryTest {

	@Configuration
	static class ContextConfiguration {		
	}
				
	private DataObject createDefaultDataObject() {	
		DataObject obj = TestData.getDataObject("NS1", "O1");
		Namespace ns = obj.getNamespace();
		database.insertPropertyGroup(ns.getPropertyGroup(), 1L);
		database.insertNamespace(ns, 1L);
		database.insertDataObject(obj, 1L);
		return obj;		
	}
	
	private Attribute createAttribute(DataObject obj, String name, Long id) {
		Attribute attr = new Attribute(obj, name);
		database.insertAttribute(attr, id);
		return attr;		
	}
	
	private AttributeSourceMapping getMapping(Attribute tgt, Attribute src) {
		AttributeSourceMapping map = new AttributeSourceMapping(tgt, src);
		map.setProperties(TestData.getDynamicProperties());
		return map;
	}
	
	private AttributeSourceMapping createMapping(Attribute tgt, Attribute src) {
		AttributeSourceMapping map = getMapping(tgt, src);
		database.insertAttributeSource(map);
		return map;
	}
	
	private AttributeSourceMapping fetchFromRepository(Attribute tgt, Attribute src) {
		Attribute t = attrRepository.findById(tgt.getId());
		Attribute s = attrRepository.findById(src.getId());
		// Need to pass persistent entities
		AttributeSourceMapping map = asRepository
				.findById(new AttributeSourceMappingKey(t, s));
		return map;
	}
	
	/*
	 * Test findById()
	 */	
	@Test
	public void findById() {
		
		DataObject obj = createDefaultDataObject();
		Attribute tgt = createAttribute(obj, "A1", 1L);
		Attribute src1 = createAttribute(obj, "A2", 2L);
		Attribute src2 = createAttribute(obj, "A2", 3L);
		
		// Add 2 sources to A1
		createMapping(tgt, src1);
		createMapping(tgt, src2);
		super.verifyRowCount(Tables.ATTRIBUTE_SOURCE, 2);
		
		AttributeSourceMapping exp = getMapping(tgt, src1);
		AttributeSourceMapping act = fetchFromRepository(tgt, src1);
		EntityComparator.verifyEqual(exp, act);
	}
		
	/*
	 * Test create()
	 */		
	@Test
	public void create() {
		
		DataObject obj = createDefaultDataObject();
		Attribute tgt = createAttribute(obj, "A1", 1L);
		Attribute src1 = createAttribute(obj, "A2", 2L);
		Attribute src2 = createAttribute(obj, "A2", 3L);
		
		// Add 2 sources
		createMapping(tgt, src1);
		createMapping(tgt, src2);
		super.verifyRowCount(Tables.ATTRIBUTE_SOURCE, 2);
		
		// Verify
		Set<AttributeSourceMapping> exp = new HashSet<>();
		exp.add(getMapping(tgt, src1));
		exp.add(getMapping(tgt, src2));
		Attribute act = attrRepository.findById(tgt.getId());
		EntityComparator.verifyLists(exp, act.getSources());
	}
	
	/*
	 * Test update()
	 */			
	private void makeChanges(AttributeSourceMapping map) {
		// For dynamic properties, add new, update existing and remove existing
		map.getProperties()
			.setProperty("new-property", "new-value")
			.setProperty("text", "text-value-new")
			.removeProperty("number");		
	}
	
	@Test
	public void update() {
		
		DataObject obj = createDefaultDataObject();
		Attribute tgt = createAttribute(obj, "A1", 1L);
		Attribute src = createAttribute(obj, "A2", 2L);
		AttributeSourceMapping exp = createMapping(tgt, src);
		
		// Update
		AttributeSourceMapping map = fetchFromRepository(tgt, src);
		makeChanges(map);
		asRepository.update(map);
		super.flushAndEvict(map);
		
		// Verify 
		makeChanges(exp);
		Attribute act = attrRepository.findById(tgt.getId());
		EntityComparator.verifyEqual(exp, act.getSources().iterator().next());
	}	
	
	/*
	 * Test delete()
	 */	
	@Test
	public void delete() {
		
		DataObject obj = createDefaultDataObject();
		Attribute tgt = createAttribute(obj, "A1", 1L);
		Attribute src = createAttribute(obj, "A2", 2L);
		createMapping(tgt, src);
		
		// Delete mapping
		AttributeSourceMapping map = fetchFromRepository(tgt, src);
		asRepository.delete(map);
		super.flushAndEvict(map);
		super.verifyRowCount(Tables.ATTRIBUTE_SOURCE, 0);
	}
	
	/*
	 * Test getTargets() of Attribute
	 */	
	@Test
	public void _getTargets() {
		
		DataObject obj = createDefaultDataObject();
		Attribute src1 = createAttribute(obj, "A1", 1L);
		Attribute tgt1 = createAttribute(obj, "A2", 2L);
		Attribute tgt2 = createAttribute(obj, "A3", 3L);
		
		// Add 2 targets to src1
		createMapping(tgt1, src1);
		createMapping(tgt2, src1);
		super.verifyRowCount(Tables.ATTRIBUTE_SOURCE, 2);
				
		// Verify
		Attribute act = attrRepository.findById(src1.getId());
		assertEquals(2, act.getTargets().size());		
	}
}