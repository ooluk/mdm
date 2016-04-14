package com.ooluk.mdm.core.meta.dataobject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.springframework.context.annotation.Configuration;

import com.ooluk.mdm.core.meta.dataobject.DataObject;
import com.ooluk.mdm.core.meta.dataobject.DataObjectNote;
import com.ooluk.mdm.core.test.AbstractRepositoryTest;
import com.ooluk.mdm.core.test.EntityComparator;
import com.ooluk.mdm.core.test.Tables;
import com.ooluk.mdm.core.test.TestData;

/**
 * <pre>
 * ----------
 * TEST SCOPE
 * ----------
 * - Find a data object note by ID
 * - Create a data object note
 * - Update a data object note
 * - Delete a data object note
 * </pre>
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
public class DataObjectNoteRepositoryTest extends AbstractRepositoryTest {

	@Configuration
	static class ContextConfiguration {		
	}
	
	private static String NOTE = "text1";
	
	/**
	 * Returns a transient default note
	 * 
	 * @return note
	 */
	private DataObjectNote getDefaultNote() {
		return TestData.getDataObjectNote(NOTE);
	}
	
	private DataObject createDefaultDataObject() {
		DataObject obj = TestData.getDataObject("NS1", "OBJ1");
		database.insertPropertyGroup(obj.getNamespace().getPropertyGroup(), 1L);
		database.insertNamespace(obj.getNamespace(), 1L);
		database.insertDataObject(obj, 1L);
		return obj;
	}
	
	private DataObjectNote createDefaultNote() {
		DataObject obj = createDefaultDataObject();
		DataObjectNote note = getDefaultNote().setDataObject(obj);
		database.insertDataObjectNote(note, 1L);
		return note;
	}
	
	/*
	 * Test findById()
	 */		
	@Test
	public void findById() {
		DataObjectNote exp = createDefaultNote();
		DataObjectNote act = dnoteRepository.findById(exp.getId());		
		EntityComparator.verifyEqual(exp, act);
		
		// Bad ID
		act = dnoteRepository.findById(exp.getId()+1);		
		assertNull(act);
	}		
	
	/*
	 * Test create()
	 */	
	@Test
	public void create() {
		
		// Create DataObject
		DataObject obj = createDefaultDataObject();
		
		// Create note
		DataObjectNote note = getDefaultNote();
		note.setDataObject(obj);
		dnoteRepository.create(note);
		super.flushAndEvict(note);
		
		// Verify
		DataObjectNote exp = getDefaultNote().setDataObject(obj);
		DataObjectNote act = dnoteRepository.findById(note.getId());
		EntityComparator.verifyEqual(exp, act);
		assertEquals(obj.getId(), note.getDataObject().getId());
		
	}
	
	/*
	 * Test update()
	 */
	private void makeChanges(DataObjectNote note) {
		note.setText(note.getText()+"-updated");
		// For dynamic properties, add new, update existing and remove existing
		note.getProperties()
			.setProperty("new-property", "new-value")
			.setProperty("text", "text-value-new")
			.removeProperty("number");	
	}
	
	@Test
	public void update() {	
		DataObjectNote exp = createDefaultNote();
		
		// Update
		DataObjectNote note = dnoteRepository.findById(exp.getId());
		makeChanges(note);
		dnoteRepository.update(note);
		flushAndEvict(note);
		
		// Verify 
		makeChanges(exp);
		DataObjectNote act = dnoteRepository.findById(note.getId());	
		EntityComparator.verifyEqual(exp, act);
	}
	
	/*
	 * Test delete()
	 */
	@Test
	public void delete() {		
		DataObjectNote note = createDefaultNote();
		super.verifyRowCount(Tables.DATA_OBJECT_NOTE, 1);
		dnoteRepository.delete(note);
		flushAndEvict(note);
		super.verifyRowCount(Tables.DATA_OBJECT_NOTE, 0);
	}	
}