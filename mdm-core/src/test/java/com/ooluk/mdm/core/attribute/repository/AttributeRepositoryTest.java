package com.ooluk.mdm.core.attribute.repository;

import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.springframework.context.annotation.Configuration;

import com.ooluk.mdm.core.app.data.Label;
import com.ooluk.mdm.core.app.data.LabelType;
import com.ooluk.mdm.core.app.data.Tag;
import com.ooluk.mdm.core.attribute.data.Attribute;
import com.ooluk.mdm.core.dataobject.data.DataObject;
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
 * - Find an attribute by its ID
 * - Create an attribute
 * - Update an attribute
 * - + Update to attach labels
 * - + Update to detach labels
 * - + Update to attach tags
 * - + Update to detach tags
 * - Delete an attribute
 * - Find an attribute by its natural key: data object + name
 * </pre>
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
public class AttributeRepositoryTest extends AbstractRepositoryTest {

	@Configuration
	static class ContextConfiguration {		
	}
	
	private static String NS_NAME = "NS1";	
	private static String OBJ_NAME = "OBJ1";
	private static String ATTR_NAME = "ATTR1";
	
	/**
	 * Returns a transient default attribute
	 * 
	 * @return namespace
	 */
	private Attribute getDefaultAttribute() {
		return TestData.getAttribute(NS_NAME, OBJ_NAME, ATTR_NAME);
	}
	
	private Attribute createDefaultAttribute() {
		Attribute attr = getDefaultAttribute();
		DataObject obj = attr.getDataObject();
		database.insertPropertyGroup(obj.getNamespace().getPropertyGroup(), 1L);
		database.insertNamespace(obj.getNamespace(), 1L);
		database.insertDataObject(obj, 1L);
		database.insertAttribute(attr, 1L);
		return attr;
	}
	
	/*
	 * Test findById()
	 */		
	@Test
	public void findById() {	
		Attribute exp = createDefaultAttribute();
		Attribute act = attrRepository.findById(exp.getId());
		EntityComparator.verifyEqual(exp, act);
		act = attrRepository.findById(exp.getId()+1);
		assertNull(act);
	}	
	
	/*
	 * Test create()
	 */	
	@Test
	public void create() {
		Attribute attr = getDefaultAttribute();
		DataObject obj = attr.getDataObject();
		database.insertPropertyGroup(obj.getNamespace().getPropertyGroup(), 1L);
		database.insertNamespace(obj.getNamespace(), 1L);
		database.insertDataObject(obj, 1L);
		
		// Create
		attr.setDataObject(obj);
		attrRepository.create(attr);
		super.flushAndEvict(attr);
		
		// Verify
		Attribute exp = getDefaultAttribute();
		Attribute act = attrRepository.findById(attr.getId());
		EntityComparator.verifyEqual(exp, act);
	}
	
	/*
	 * Test update()
	 */	
	private void makeChanges(Attribute attr) {
		attr.setName(attr.getName()+"-updated");
		// For dynamic properties, add new, update existing and remove existing
		attr.getProperties()
			.setProperty("new-property", "new-value")
			.setProperty("text", "text-value-new")
			.removeProperty("number");		
	}
	
	@Test
	public void update() {
		Attribute attr = createDefaultAttribute();
		
		// Update
		makeChanges(attr);
		attrRepository.update(attr);
		flushAndEvict(attr);
		
		// Verify by making the same changes to expected
		Attribute exp = getDefaultAttribute();
		makeChanges(exp);
		Attribute act = attrRepository.findById(attr.getId());
		EntityComparator.verifyEqual(exp, act);
	}
	
	@Test
	public void update_Attach_Labels() {
		Attribute exp = createDefaultAttribute();
		
		// Attach labels
		LabelType type = new LabelType("T1");
		database.insertLabelType(type, 1L);
		Label l1 = new Label(type, "L1");
		Label l2 = new Label(type, "L2");
		database.insertLabel(l1, 1L);
		database.insertLabel(l2, 2L);
		
		// Attach labels
		Attribute attr = attrRepository.findById(exp.getId());
		attr.attachLabel(l1).attachLabel(l2);
		attrRepository.update(attr);
		super.flushAndEvict(attr);
		super.verifyRowCount(Tables.ATTRIBUTE_LABEL, 2);
		
		// Verify
		exp.attachLabel(l1).attachLabel(l2);
		Attribute act = attrRepository.findById(attr.getId());
		EntityComparator.verifyEqual(exp, act);		
	}
	
	@Test
	public void update_Detach_Labels() {
		Attribute exp = createDefaultAttribute();
		
		// Attach labels
		LabelType type = new LabelType("T1");
		database.insertLabelType(type, 1L);
		Label l1 = new Label(type, "L1");
		Label l2 = new Label(type, "L2");
		database.insertLabel(l1, 1L);
		database.insertLabel(l2, 2L);
		database.insertAttributeLabel(exp, l1);
		database.insertAttributeLabel(exp, l2);
		
		// Detach label
		Attribute attr = attrRepository.findById(exp.getId());
		attr.detachLabel(l1);
		attrRepository.update(attr);
		super.flush();
		super.verifyRowCount(Tables.ATTRIBUTE_LABEL, 1);
				
		// Verify
		exp.attachLabel(l2);
		Attribute act = attrRepository.findById(attr.getId());
		EntityComparator.verifyEqual(exp, act);		
	}
	
	@Test
	public void update_Attach_Tags() {
		Attribute exp = createDefaultAttribute();
		
		// Create tags
		Tag t1 = new Tag("T1");
		Tag t2 = new Tag("T2");
		database.insertTag(t1, 1L);
		database.insertTag(t2, 2L);
		
		// Attach tags
		Attribute attr = attrRepository.findById(exp.getId());
		attr.attachTag(t1).attachTag(t2);
		attrRepository.update(attr);
		super.flushAndEvict(attr);
		super.verifyRowCount(Tables.ATTRIBUTE_TAG, 2);
		
		// Verify
		exp.attachTag(t1).attachTag(t2);
		Attribute act = attrRepository.findById(attr.getId());
		EntityComparator.verifyEqual(exp, act);		
	}
	
	@Test
	public void update_Detach_Tags() {
		Attribute exp = createDefaultAttribute();
		
		// Create and attach tags
		Tag t1 = new Tag("T1");
		Tag t2 = new Tag("T2");
		database.insertTag(t1, 1L);
		database.insertTag(t2, 2L);
		database.insertAttributeTag(exp, t1);
		database.insertAttributeTag(exp, t2);
		
		// Detach tag
		Attribute attr = attrRepository.findById(exp.getId());
		attr.detachTag(t2);
		attrRepository.update(attr);
		super.flush();
		super.verifyRowCount(Tables.ATTRIBUTE_TAG, 1);
		
		// Verify
		exp.attachTag(t1);
		Attribute act = attrRepository.findById(attr.getId());
		EntityComparator.verifyEqual(exp, act);		
	}
	
	/*
	 * Test delete()
	 */
	@Test
	public void delete() {	
		Attribute attr = createDefaultAttribute();
		attrRepository.delete(attr);
		flushAndEvict(attr);
		super.verifyRowCount(Tables.ATTRIBUTE, 0);
	}
	
	/*
	 * Test findByName()
	 */
	@Test
	public void findByName() {		
		Attribute exp = createDefaultAttribute();		
		Attribute act = attrRepository.findByName(exp.getDataObject(), exp.getName());
		EntityComparator.verifyEqual(exp, act);
		
		// Bad data object
		DataObject obj = new DataObject();
		TestUtils.setIdField(obj, 100L);
		act = attrRepository.findByName(obj, exp.getName());
		assertNull(act);
		
		// Bad name
		act = attrRepository.findByName(exp.getDataObject(), exp.getName().substring(1));
		assertNull(act);
	}	
}