package com.ooluk.mdm.data.meta.attribute;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.springframework.context.annotation.Configuration;

import com.ooluk.mdm.data.meta.attribute.Attribute;
import com.ooluk.mdm.data.meta.attribute.AttributeNote;
import com.ooluk.mdm.data.meta.dataobject.DataObject;
import com.ooluk.mdm.data.test.AbstractRepositoryTest;
import com.ooluk.mdm.data.test.EntityComparator;
import com.ooluk.mdm.data.test.Tables;
import com.ooluk.mdm.data.test.TestData;

/**
 * <pre>
 * ----------
 * TEST SCOPE
 * ----------
 * - Find an attribute note by ID
 * - Create an attribute note
 * - Update an attribute note
 * - Delete an attribute note
 * </pre>
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
public class AttributeNoteRepositoryTest extends AbstractRepositoryTest {

	@Configuration
	static class ContextConfiguration {		
	}
	
	private static String NOTE = "text1";
	
	/**
	 * Returns a transient default note
	 * 
	 * @return note
	 */
	private AttributeNote getDefaultNote() {
		return TestData.getAttributeNote(NOTE);
	}
	
	private Attribute createDefaultAttribute() {
		Attribute attr = TestData.getAttribute("NS1", "OBJ1", "ATTR1");
		DataObject obj = attr.getDataObject();
		database.insertPropertyGroup(obj.getNamespace().getPropertyGroup(), 1L);
		database.insertNamespace(obj.getNamespace(), 1L);
		database.insertDataObject(obj, 1L);
		database.insertAttribute(attr, 1L);
		return attr;
		
	}
	
	private AttributeNote createDefaultNote() {
		Attribute attr = createDefaultAttribute();
		AttributeNote note = getDefaultNote().setAttribute(attr);
		database.insertAttributeNote(note, 1L);
		return note;
	}
	
	/*
	 * Test findById()
	 */		
	@Test
	public void findById() {
		AttributeNote exp = createDefaultNote();
		AttributeNote act = anoteRepository.findById(exp.getId());		
		EntityComparator.verifyEqual(exp, act);
		
		// Bad ID
		act = anoteRepository.findById(exp.getId()+1);		
		assertNull(act);
	}	
	
	/*
	 * Test create()
	 */	
	@Test
	public void create() {
		
		Attribute attr = createDefaultAttribute();
		
		// Create note
		AttributeNote note = getDefaultNote();
		note.setAttribute(attr);
		anoteRepository.create(note);
		super.flushAndEvict(note);
		
		// Verify
		AttributeNote exp = getDefaultNote().setAttribute(attr);
		AttributeNote act = anoteRepository.findById(note.getId());
		EntityComparator.verifyEqual(exp, act);
		assertEquals(attr.getId(), note.getAttribute().getId());
		
	}
	
	/*
	 * Test update()
	 */
	private void makeChanges(AttributeNote note) {
		note.setText(note.getText()+"-updated");
		// For dynamic properties, add new, update existing and remove existing
		note.getProperties()
			.setProperty("new-property", "new-value")
			.setProperty("text", "text-value-new")
			.removeProperty("number");	
	}
	
	@Test
	public void update() {	
		AttributeNote exp = createDefaultNote();
		
		// Update
		AttributeNote note = anoteRepository.findById(exp.getId());
		makeChanges(note);
		anoteRepository.update(note);
		flushAndEvict(note);
		
		// Verify 
		makeChanges(exp);
		AttributeNote act = anoteRepository.findById(note.getId());	
		EntityComparator.verifyEqual(exp, act);
	}
	
	/*
	 * Test delete()
	 */
	@Test
	public void delete() {		
		AttributeNote note = createDefaultNote();
		super.verifyRowCount(Tables.ATTRIBUTE_NOTE, 1);
		anoteRepository.delete(note);
		flushAndEvict(note);
		super.verifyRowCount(Tables.ATTRIBUTE_NOTE, 0);
	}	
}