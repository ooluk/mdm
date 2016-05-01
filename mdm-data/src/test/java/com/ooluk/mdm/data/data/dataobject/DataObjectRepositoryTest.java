package com.ooluk.mdm.data.data.dataobject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.context.annotation.Configuration;

import com.ooluk.mdm.data.meta.PropertyGroup;
import com.ooluk.mdm.data.meta.app.Label;
import com.ooluk.mdm.data.meta.app.LabelType;
import com.ooluk.mdm.data.meta.app.Tag;
import com.ooluk.mdm.data.meta.dataobject.DataObject;
import com.ooluk.mdm.data.meta.dataobject.Namespace;
import com.ooluk.mdm.data.test.AbstractRepositoryTest;
import com.ooluk.mdm.data.test.EntityComparator;
import com.ooluk.mdm.data.test.Tables;
import com.ooluk.mdm.data.test.TestData;
import com.ooluk.mdm.data.test.TestUtils;

/**
 * <pre>
 * ----------
 * TEST SCOPE
 * ----------
 * - Find a data object by its ID
 * - Create a data object
 * - Update a data object
 * - + Update to attach labels
 * - + Update to detach labels
 * - + Update to attach tags
 * - + Update to detach tags
 * - Delete a data object
 * - Find a data object by its natural key: namespace + name
 * - Find data objects by namespace 
 * - Find data objects with a set of labels
 * </pre>
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
public class DataObjectRepositoryTest extends AbstractRepositoryTest {

	@Configuration
	static class ContextConfiguration {		
	}
	
	private static String NS_NAME = "NS1";	
	private static String OBJ_NAME = "OBJ1";
	
	/**
	 * Returns a transient default data object
	 * 
	 * @return data object
	 */
	private DataObject getDefaultDataObject() {
		return TestData.getDataObject(NS_NAME, OBJ_NAME);
	}
	
	private DataObject createDefaultDataObject() {
		DataObject obj = getDefaultDataObject();
		database.insertPropertyGroup(obj.getNamespace().getPropertyGroup(), 1L);
		database.insertNamespace(obj.getNamespace(), 1L);
		database.insertDataObject(obj, 1L);
		return obj;
	}
	
	/*
	 * Test findById()
	 */		
	@Test
	public void findById() {	
		DataObject exp = createDefaultDataObject();
		DataObject act = objRepository.findById(exp.getId());
		EntityComparator.verifyEqual(exp, act);
	
		// Bad ID
		act = objRepository.findById(exp.getId() + 1);
		assertNull(act);
	}
	
	/*
	 * Test create()
	 */	
	@Test
	public void create() {
		DataObject exp = getDefaultDataObject();
		Namespace ns = exp.getNamespace();
		PropertyGroup pg = ns.getPropertyGroup();
		database.insertPropertyGroup(pg, 1L);
		database.insertNamespace(ns, 1L);
		
		// Create
		DataObject obj = getDefaultDataObject().setNamespace(ns);
		objRepository.create(obj);
		super.flushAndEvict(obj);
		
		// Verify
		DataObject act = objRepository.findById(obj.getId());
		EntityComparator.verifyEqual(exp, act);
	}
	
	/*
	 * Test update()
	 */	
	private void makeChanges(DataObject obj) {
		obj.setName(obj.getName()+"-updated");
		// For dynamic properties, add new, update existing and remove existing
		obj.getProperties()
			.setProperty("new-property", "new-value")
			.setProperty("text", "text-value-new")
			.removeProperty("number");		
	}
	
	@Test
	public void update() {
		
		DataObject exp = createDefaultDataObject();
		
		// Update
		DataObject obj = objRepository.findById(exp.getId());
		makeChanges(obj);
		Namespace ns = (new Namespace("NS2"))
				.setPropertyGroup(obj.getNamespace().getPropertyGroup());
		database.insertNamespace(ns, 2L);
		obj.setNamespace(ns);
		objRepository.update(obj);
		flushAndEvict(obj);
		
		// Verify by making the same changes to expected
		makeChanges(exp);
		exp.setNamespace(ns);
		DataObject act = objRepository.findById(obj.getId());
		EntityComparator.verifyEqual(exp, act);
	}
	
	@Test
	public void update_Attach_Labels() {
		
		DataObject exp = createDefaultDataObject();
		
		// Create labels
		LabelType type = new LabelType("T1");
		database.insertLabelType(type, 1L);
		Label l1 = new Label(type, "L1");
		Label l2 = new Label(type, "L2");
		database.insertLabel(l1, 1L);
		database.insertLabel(l2, 2L);
		
		// Attach labels
		DataObject obj = objRepository.findById(exp.getId());
		obj.attachLabel(l1).attachLabel(l2);
		objRepository.update(obj);
		super.flushAndEvict(obj);
		super.verifyRowCount(Tables.DATA_OBJECT_LABEL, 2);
		
		// Verify
		exp.attachLabel(l1).attachLabel(l2);
		DataObject act = objRepository.findById(obj.getId());
		EntityComparator.verifyEqual(exp, act);		
	}
	
	@Test
	public void update_Detach_Labels() {
		
		DataObject exp = createDefaultDataObject();
		
		// Create and attach labels
		LabelType type = new LabelType("T1");
		database.insertLabelType(type, 1L);
		Label l1 = new Label(type, "L1");
		Label l2 = new Label(type, "L2");
		database.insertLabel(l1, 1L);
		database.insertLabel(l2, 2L);
		database.insertDataObjectLabel(exp, l1);
		database.insertDataObjectLabel(exp, l2);
		super.verifyRowCount(Tables.DATA_OBJECT_LABEL, 2);
		
		// Detach label
		DataObject obj = objRepository.findById(exp.getId());
		obj.detachLabel(l1);
		objRepository.update(obj);
		super.flushAndEvict(obj);
		super.verifyRowCount(Tables.DATA_OBJECT_LABEL, 1);
				
		// Verify
		exp.attachLabel(l2);
		DataObject act = objRepository.findById(obj.getId());
		EntityComparator.verifyEqual(exp, act);		
	}
	
	@Test
	public void update_Attach_Tags() {
		
		DataObject exp = createDefaultDataObject();
		
		// Create tags
		Tag t1 = new Tag("T1");
		Tag t2 = new Tag("T2");
		database.insertTag(t1, 1L);
		database.insertTag(t2, 2L);
		
		// Attach tags
		DataObject obj = objRepository.findById(exp.getId());
		obj.attachTag(t1).attachTag(t2);
		objRepository.update(obj);
		super.flushAndEvict(obj);
		super.verifyRowCount(Tables.DATA_OBJECT_TAG, 2);
		
		// Verify
		exp.attachTag(t1).attachTag(t2);
		DataObject act = objRepository.findById(obj.getId());
		EntityComparator.verifyEqual(exp, act);		
	}
	
	@Test
	public void update_Detach_Tags() {
		
		DataObject exp = createDefaultDataObject();
		
		// Create and attach tags
		Tag t1 = new Tag("T1");
		Tag t2 = new Tag("T2");
		database.insertTag(t1, 1L);
		database.insertTag(t2, 2L);
		database.insertDataObjectTag(exp, t1);
		database.insertDataObjectTag(exp, t2);
		super.verifyRowCount(Tables.DATA_OBJECT_TAG, 2);
		
		// Detach tag
		DataObject obj = objRepository.findById(exp.getId());
		obj.detachTag(t2);
		objRepository.update(obj);
		super.flush();
		super.verifyRowCount(Tables.DATA_OBJECT_TAG, 1);
		
		// Verify
		exp.attachTag(t1);
		DataObject act = objRepository.findById(obj.getId());
		EntityComparator.verifyEqual(exp, act);		
	}
	
	/*
	 * Test delete()
	 */
	@Test
	public void delete() {	
		DataObject obj = createDefaultDataObject();
		objRepository.delete(obj);
		flushAndEvict(obj);
		super.verifyRowCount(Tables.DATA_OBJECT, 0);
	}
	
	/*
	 * Test findByName()
	 */
	@Test
	public void findByName() {		
		DataObject exp = createDefaultDataObject(); 
		DataObject act = objRepository.findByName(exp.getNamespace(), exp.getName());
		EntityComparator.verifyEqual(exp, act);
		
		// Bad name
		act = objRepository.findByName(exp.getNamespace(), exp.getName().substring(1));
		assertNull(act);
		
		// Bad namespace
		Namespace ns = new Namespace("NS2");
		TestUtils.setIdField(ns, exp.getId()+1);
		act = objRepository.findByName(ns, exp.getName());
		assertNull(act);
	}	
	
	/*
	 * Test findByNamespace()
	 */
	@Test
	public void findByNamespace() {
		PropertyGroup pg = new PropertyGroup("PG1");
		database.insertPropertyGroup(pg, 1L);
		Namespace ns1 = (new Namespace("NS1")).setPropertyGroup(pg);
		database.insertNamespace(ns1, 1L);
		Namespace ns2 = (new Namespace("NS2")).setPropertyGroup(pg);
		database.insertNamespace(ns2, 2L);
		DataObject o1 = new DataObject(ns1, "O1");
		DataObject o2 = new DataObject(ns2, "O2");
		database.insertDataObject(o1, 1L);
		database.insertDataObject(o2, 2L);
		super.verifyRowCount(Tables.DATA_OBJECT, 2);
		
		List<DataObject> objs = objRepository.findByNamespace(ns1);
		assertEquals(1, objs.size());
		assertEquals("O1", objs.get(0).getName());
	}
	
	/*
	 * Test findByLabels()
	 */
	@Test
	public void findByLabels() {
		// Create 2 data objects
		PropertyGroup pg = new PropertyGroup("PG1");
		database.insertPropertyGroup(pg, 1L);
		Namespace ns1 = (new Namespace("NS1")).setPropertyGroup(pg);
		database.insertNamespace(ns1, 1L);
		Namespace ns2 = (new Namespace("NS2")).setPropertyGroup(pg);
		database.insertNamespace(ns2, 2L);
		DataObject o1 = new DataObject(ns1, "O1");
		DataObject o2 = new DataObject(ns2, "O2");
		database.insertDataObject(o1, 1L);
		database.insertDataObject(o2, 2L);
		super.verifyRowCount(Tables.DATA_OBJECT, 2);
		
		// Create 3 label types and 3 labels
		LabelType t1 = new LabelType("T1");
		database.insertLabelType(t1, 1L);
		LabelType t2 = new LabelType("T3");
		database.insertLabelType(t2, 2L);
		LabelType t3 = new LabelType("T3");
		database.insertLabelType(t3, 3L);		
		Label l1 = new Label(t1, "L1");
		Label l2 = new Label(t2, "L2");
		Label l3 = new Label(t3, "L3");		
		database.insertLabel(l1, 1L);
		database.insertLabel(l2, 2L);
		database.insertLabel(l3, 3L);
		
		// Attach labels
		database.insertDataObjectLabel(o1, l1);
		database.insertDataObjectLabel(o1, l3);
		database.insertDataObjectLabel(o2, l2);
		database.insertDataObjectLabel(o2, l3);
		super.verifyRowCount(Tables.DATA_OBJECT_LABEL, 4);
		 		
		// L3 = 2 data objects
		List<Label> labels = Arrays.asList(new Label[]{l3});
		List<DataObject> objs = objRepository.findByLabels(labels);
		assertEquals(2, objs.size());
 		
		// L1 and L3 = 1 data object
		labels = Arrays.asList(new Label[]{l1, l3});
		objs = objRepository.findByLabels(labels);
		assertEquals(1, objs.size());
		assertEquals("O1", objs.get(0).getName());
	}
}