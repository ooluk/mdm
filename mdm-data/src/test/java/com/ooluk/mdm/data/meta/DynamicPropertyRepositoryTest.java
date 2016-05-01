package com.ooluk.mdm.data.meta;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.springframework.context.annotation.Configuration;

import com.ooluk.mdm.data.meta.DynamicProperty;
import com.ooluk.mdm.data.meta.DynamicPropertyType;
import com.ooluk.mdm.data.meta.ListValue;
import com.ooluk.mdm.data.meta.PropertyGroup;
import com.ooluk.mdm.data.test.AbstractRepositoryTest;
import com.ooluk.mdm.data.test.EntityComparator;
import com.ooluk.mdm.data.test.Tables;
import com.ooluk.mdm.data.test.TestData;

/**
 * <pre>
 * ----------
 * TEST SCOPE
 * ----------
 * - Create a dynamic property
 * - Find a dynamic property by ID
 * - Update a dynamic property
 * - Delete a dynamic property 
 * - Create list value
 * - Update list value
 * - Delete list value
 * - Find a dynamic property by key
 * - Get all dynamic properties  
 * + Attach a property group  
 * + Detach a property group
 * </pre>
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
public class DynamicPropertyRepositoryTest extends AbstractRepositoryTest {

	@Configuration
	static class ContextConfiguration {
	}
	
	/**
	 * Creates a dynamic property entity of specified type. Does not evict the entity.
	 * 
	 * @param type
	 *            property type
	 * 
	 * @return entity ID
	 */
	private DynamicProperty createTestEntity(DynamicPropertyType type) {
				
		// Create dynamic property 
		DynamicProperty dp = TestData.getDynamicProperty(type);
		database.insertDynamicProperty(dp, 1L);
		
		// Attach to property groups
		int i = 1;
		for (PropertyGroup pg : dp.getPropertyGroups()) {
			database.insertPropertyGroup(pg, Long.valueOf(i));
			database.insertDynamicPropertyGroup(dp, pg);
		}
		
		// Create list values
		i = 1;
		for (ListValue lv : dp.getValueList()) 
			database.insertListValue(lv, Long.valueOf(i++), dp);
		return dp;
	}
	
	/**
	 * Creates a dynamic property entity of SINGLE_LINE_TEXT type
	 * 
	 * @return entity
	 */
	private DynamicProperty createTestEntity() {
		return createTestEntity(DynamicPropertyType.SINGLE_LINE_TEXT);
	}
	
	/*
	 * Test create()
	 */	
	@Test
	public void create() {
		// Tested with find()
	}
	
	/*
	 * Test findById()
	 */		
	@Test
	public void findById_With_Value_List() {
		DynamicProperty exp = createTestEntity(DynamicPropertyType.SINGLE_CHOICE_TEXT_LIST);
		DynamicProperty act = dpRepository.findById(exp.getId());
		EntityComparator.verifyEqual(exp, act);
	}	
	
	@Test
	public void findById_Without_ValueList() {
		DynamicProperty exp = createTestEntity();
		DynamicProperty act = dpRepository.findById(exp.getId());
		EntityComparator.verifyEqual(exp, act);
		
		// Bad ID
		act = dpRepository.findById(exp.getId()+1);
		assertNull(act);
	}
	
	/*
	 * Test update
	 */
	private void makeChanges(DynamicProperty dp) {
		dp.setKey(dp.getKey()+"-updated");
		dp.setMandatory(!dp.isMandatory());
		// List values are managed separately
	}
	
	@Test
	public void update() {		
		DynamicProperty exp = createTestEntity(DynamicPropertyType.SINGLE_CHOICE_TEXT_LIST);	
		
		// Update
		DynamicProperty dp = dpRepository.findById(exp.getId());
		makeChanges(dp);
		dpRepository.update(dp);
		super.flushAndEvict(dp);
		
		// Verify
		makeChanges(exp);
		DynamicProperty act = dpRepository.findById(dp.getId());
		EntityComparator.verifyEqual(exp, act);
	}
	
	/*
	 * Test delete()
	 */	
	@Test
	public void delete() {	
		
		// Prepare data
		DynamicProperty dp = createTestEntity(DynamicPropertyType.SINGLE_CHOICE_TEXT_LIST);
		super.verifyRowCount(Tables.LIST_VALUES, 2);
		
		// Delete
		dp = dpRepository.findById(dp.getId());
		dpRepository.delete(dp);
		super.flushAndEvict(dp);
		
		// Verify
		DynamicProperty act = dpRepository.findById(dp.getId());
		assertNull(act);
		// Ensure all list values have been deleted
		super.verifyRowCount(Tables.LIST_VALUES, 0);
	}
	
	/*
	 * Test create() on ListValueRepository
	 */
	@Test
	public void create_List_Value() {
		DynamicProperty dp = createTestEntity();
		
		// Create
		ListValue lv = (new ListValue("V1")).setProperty(dp);
		lvRepository.create(lv);
		super.flushAndEvict(lv);
		super.verifyRowCount(Tables.LIST_VALUES, 1);
		
		// Verify
		ListValue exp = (new ListValue("V1")).setProperty(dp);
		ListValue act = lvRepository.findById(lv.getId());
		assertEquals(exp.getProperty().getId(), act.getProperty().getId());
		EntityComparator.verifyEqual(exp, act);		
	}
	
	/*
	 * Test update() on ListValueRepository
	 */
	@Test
	public void update_List_Value() {
		DynamicProperty dp = createTestEntity();
		
		// Create
		ListValue lv = (new ListValue("V1"));
		database.insertListValue(lv, 1L, dp);
		super.verifyRowCount(Tables.LIST_VALUES, 1);
		
		// Retrieve and update
		lv = lvRepository.findById(lv.getId());
		lv.setValue("V2");
		lvRepository.update(lv);
		super.flushAndEvict(lv);
		
		// Retrieve and verify
		ListValue exp = (new ListValue(lv.getValue())).setProperty(dp);
		ListValue act = lvRepository.findById(lv.getId());
		assertEquals(exp.getProperty().getId(), act.getProperty().getId());
		EntityComparator.verifyEqual(exp, act);		
	}
	
	/*
	 * Test delete() on ListValueRepository
	 */
	@Test
	public void delete_List_Value() {
		DynamicProperty dp = createTestEntity();
		
		// Create
		ListValue lv = (new ListValue("V1"));
		database.insertListValue(lv, 1L, dp);
		super.verifyRowCount(Tables.LIST_VALUES, 1);
		
		// Delete
		lv = lvRepository.findById(lv.getId());
		lvRepository.delete(lv);
		super.flushAndEvict(lv);
		
		// Verify
		super.verifyRowCount(Tables.LIST_VALUES, 0);
	}
	
	/*
	 * Test findByKey
	 */
	@Test
	public void findByKey() {
		createTestEntity();	
		DynamicProperty exp = TestData.getDynamicProperty();
		DynamicProperty act = dpRepository.findByKey(exp.getKey());
		EntityComparator.verifyEqual(exp, act);		
		
		// Bad key
		act = dpRepository.findByKey(exp.getKey().substring(1));
		assertNull(act);		
	}	
	
	/*
	 * Test getAll()
	 */
	@Test
	public void getAll() {
		for (int i = 1; i <= 5; i++) {
			DynamicProperty dp = TestData.getDynamicProperty();
			database.insertDynamicProperty(dp, Long.valueOf(i));
		}
		super.verifyRowCount(Tables.DYNAMIC_PROPERTY, 5);
		List<DynamicProperty> dps = dpRepository.getAll();
		assertEquals(5, dps.size());
	}
	
	/*
	 * Attach property group
	 */
	@Test
	public void attachPropertyGroup() {
		super.verifyRowCount(Tables.DYNAMIC_PROPERTY_GROUP, 0);
		
		// Create dynamic property with 1 property group
		DynamicProperty dp  = createTestEntity();
		super.verifyRowCount(Tables.DYNAMIC_PROPERTY_GROUP, 1);
		
		PropertyGroup pg = new PropertyGroup("PG2");
		database.insertPropertyGroup(pg, 2L);
		super.verifyRowCount(Tables.PROPERTY_GROUP, 2);

		// Attach 2nd property group
		dp = dpRepository.findById(dp.getId());
		dp.attachPropertyGroup(pg);
		super.flushAndEvict(dp);
		super.verifyRowCount(Tables.DYNAMIC_PROPERTY_GROUP, 2);
		
		// Verify there are 2 property groups for the dynamic property 
		DynamicProperty act = dpRepository.findById(dp.getId());
		assertEquals(2, act.getPropertyGroups().size());
	}
	
	/*
	 * Detach property group
	 */
	@Test
	public void detachPropertyGroup() {
		// Create dynamic property with 1 property group
		DynamicProperty dp = createTestEntity();
		super.verifyRowCount(Tables.DYNAMIC_PROPERTY_GROUP, 1);
		
		// Detach the property group
		PropertyGroup pg = dp.getPropertyGroups().iterator().next();
		dp = dpRepository.findById(dp.getId());
		dp.detachPropertyGroup(pg);
		super.flushAndEvict(dp);
		super.verifyRowCount(Tables.DYNAMIC_PROPERTY_GROUP, 0);
		
		// Verify the property group is removed from the dynamic property
		DynamicProperty act = dpRepository.findById(dp.getId());
		assertTrue(act.getPropertyGroups().isEmpty());
	}
}