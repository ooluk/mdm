package com.ooluk.mdm.core.app.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.springframework.context.annotation.Configuration;

import com.ooluk.mdm.core.app.data.LabelType;
import com.ooluk.mdm.core.test.AbstractRepositoryTest;
import com.ooluk.mdm.core.test.EntityComparator;
import com.ooluk.mdm.core.test.Tables;
import com.ooluk.mdm.core.test.TestData;

/**
 * <pre>
 * ----------
 * TEST SCOPE
 * ----------
 * - Find a label type by ID
 * - Create a label type
 * - Update a label type
 * - Delete a label type
 * - Get all label types 
 * - Find a label type by natural key: name 
 * </pre>
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
public class LabelTypeRepositoryTest extends AbstractRepositoryTest {

	@Configuration
	static class ContextConfiguration {		
	}
	
	private static String LABEL_TYPE = "TYPE1";
	
	/**
	 * Returns a transient default label type
	 * 
	 * @return label
	 */
	private LabelType getDefaultLabelType() {
		return TestData.getLabelType(LABEL_TYPE);
	}
	
	/**
	 * Creates a label type entity
	 * 
	 * @return entity ID
	 */
	private LabelType createTestEntity() {
		LabelType entity = getDefaultLabelType();
		database.insertLabelType(entity, 1L);
		return entity;
	}
	
	/*
	 * Test findById()
	 */		
	@Test
	public void findById() {	
		LabelType exp = createTestEntity();
		LabelType act = lblTypeRepository.findById(exp.getId());
		EntityComparator.verifyEqual(exp, act);
		
		// Bad ID
		LabelType type = lblTypeRepository.findById(exp.getId() + 1);
		assertNull(type);
	}
	
	/*
	 * Test create()
	 */	
	@Test
	public void create() {
		LabelType type = getDefaultLabelType();
		lblTypeRepository.create(type);
		super.flushAndEvict(type);
		LabelType exp = getDefaultLabelType();
		LabelType act = lblTypeRepository.findById(type.getId());
		EntityComparator.verifyEqual(exp, act);		
	}
	
	/*
	 * Test update()
	 */	
	private void makeChanges(LabelType type) {
		type.setName(type.getName()+"-updated");
		// For dynamic properties, add new, update existing and remove existing
		type.getProperties()
			.setProperty("new-property", "new-value")
			.setProperty("text", "text-value-new")
			.removeProperty("number");		
	}
	
	@Test
	public void update() {
		LabelType exp = createTestEntity();
		
		// Update
		LabelType type = lblTypeRepository.findById(exp.getId());
		makeChanges(type);
		lblTypeRepository.update(type);
		flushAndEvict(type);
		
		// Verify
		// Make same changes to expected
		makeChanges(exp);
		LabelType act = lblTypeRepository.findById(type.getId());
		EntityComparator.verifyEqual(exp, act);
	}
	
	/*
	 * Test delete()
	 */
	@Test
	public void delete() {	
		LabelType type = createTestEntity();
		super.verifyRowCount(Tables.LABEL_TYPE, 1);
		lblTypeRepository.delete(type);
		flushAndEvict(type);
		super.verifyRowCount(Tables.LABEL_TYPE, 0);
	}
	
	/*
	 * Test getAll()
	 */
	@Test
	public void getAll() {
		for (int i = 1; i <= 5; i++) {
			LabelType type = new LabelType("T"+i);
			database.insertLabelType(type, Long.valueOf(i));
		}
		List<LabelType> types = lblTypeRepository.getAll();
		assertEquals(5, types.size());
	}
	
	@Test
	public void getAll_No_Label_Types() {
		List<LabelType> types = lblTypeRepository.getAll();
		assertTrue(types.isEmpty());
	}
	
	/*
	 * Test findByName()
	 */
	@Test
	public void findByName() {
		LabelType exp = createTestEntity();
		LabelType act = lblTypeRepository.findByName(exp.getName());
		EntityComparator.verifyEqual(exp, act);
		
		// Bad name
		act = lblTypeRepository.findByName(exp.getName().substring(1));
		assertNull(act);
	}
}