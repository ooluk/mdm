package com.ooluk.mdm.core.meta;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;
import org.springframework.context.annotation.Configuration;

import com.ooluk.mdm.core.meta.PropertyGroup;
import com.ooluk.mdm.core.test.AbstractRepositoryTest;
import com.ooluk.mdm.core.test.EntityComparator;
import com.ooluk.mdm.core.test.Tables;
import com.ooluk.mdm.core.test.TestData;

/**
 * <pre>
 * ----------
 * TEST SCOPE
 * ----------
 * - Find a property group by ID
 * - Create a property group
 * - Update a property group
 * - Delete a property group
 * - Get all property groups 
 * - Find a property group by its natural key: name
 * </pre>
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
public class PropertyGroupRepositoryTest extends AbstractRepositoryTest {

	@Configuration
	static class ContextConfiguration {		
	}
	
	private static String PG_NAME = "PG";
	
	private PropertyGroup getDefaultPropertyGroup() {
		return TestData.getPropertyGroup(PG_NAME);
	}
	
	private PropertyGroup createDefaultPropertyGroup() {
		PropertyGroup pg = getDefaultPropertyGroup();
		database.insertPropertyGroup(pg, 1L);
		return pg;
	}
	
	/*
	 * Test findById()
	 */		
	@Test
	public void findById() {	
		PropertyGroup exp = createDefaultPropertyGroup();
		PropertyGroup act = pgRepository.findById(exp.getId());
		EntityComparator.verifyEqual(exp, act);
		
		// Bad ID
		act = pgRepository.findById(exp.getId() + 1);
		assertNull(act);
	}
	
	/*
	 * Test create()
	 */	
	@Test
	public void create() {
		PropertyGroup pg = getDefaultPropertyGroup();
		pgRepository.create(pg);
		super.flushAndEvict(pg);
		PropertyGroup exp = getDefaultPropertyGroup();
		PropertyGroup act = pgRepository.findById(pg.getId());
		EntityComparator.verifyEqual(exp, act);
	}	
	
	/*
	 * Test update()
	 */	
	private void makeChanges(PropertyGroup pg) {
		pg.setName(pg.getName()+"-updated");
		// For dynamic properties, add new, update existing and remove existing
		pg.getProperties()
			.setProperty("new-property", "new-value")
			.setProperty("text", "text-value-new")
			.removeProperty("number");		
	}
	
	@Test
	public void update() {
		PropertyGroup exp = createDefaultPropertyGroup();
		
		// Update
		PropertyGroup pg = pgRepository.findById(exp.getId());
		makeChanges(pg);
		pgRepository.update(pg);
		flushAndEvict(pg);
		
		// Verify 
		makeChanges(exp);
		PropertyGroup act = pgRepository.findById(pg.getId());
		EntityComparator.verifyEqual(exp, act);
	}
	
	/*
	 * Test delete()
	 */
	@Test
	public void delete() {	
		PropertyGroup pg = createDefaultPropertyGroup();
		super.verifyRowCount(Tables.PROPERTY_GROUP, 1);
		pgRepository.delete(pg);
		flushAndEvict(pg);
		super.verifyRowCount(Tables.PROPERTY_GROUP, 0);
	}
	
	/*
	 * Test getAll()
	 */
	@Test
	public void getAll() {
		// Insert 2 property groups
		PropertyGroup pg1 = new PropertyGroup("PG1");
		database.insertPropertyGroup(pg1, 1L);
		PropertyGroup pg2 = new PropertyGroup("PG2");
		database.insertPropertyGroup(pg2, 2L);
		List<PropertyGroup> all = pgRepository.getAll();
		assertEquals(2, all.size());
	}
	
	/*
	 * Test findByName()
	 */
	@Test
	public void findByName() {	
		PropertyGroup exp = createDefaultPropertyGroup();
		PropertyGroup act = pgRepository.findByName(exp.getName());
		EntityComparator.verifyEqual(exp, act);

		// Bad name
		act = pgRepository.findByName(exp.getName().substring(1));
		assertNull(act);
	}
}