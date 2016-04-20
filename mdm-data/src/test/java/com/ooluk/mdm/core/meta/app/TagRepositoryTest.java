package com.ooluk.mdm.core.meta.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;
import org.springframework.context.annotation.Configuration;

import com.ooluk.mdm.core.meta.app.Tag;
import com.ooluk.mdm.core.test.AbstractRepositoryTest;
import com.ooluk.mdm.core.test.EntityComparator;
import com.ooluk.mdm.core.test.Tables;
import com.ooluk.mdm.core.test.TestData;

/**
 * <pre>
 * ----------
 * TEST SCOPE
 * ----------
 * - Find a tag by its ID
 * - Create a tag
 * - Update a tag
 * - Delete a tag
 * - Get all tags 
 * - Find a tag by its natural key: name
 * </pre>
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
public class TagRepositoryTest extends AbstractRepositoryTest {

	@Configuration
	static class ContextConfiguration {		
	}
	
	private static String TAG_NAME = "TAG1";
	
	/**
	 * Returns a transient default tag
	 * 
	 * @return tag
	 */
	private Tag getDefaultTag() {
		return TestData.getTag(TAG_NAME);
	}
	
	/**
	 * Creates a tag entity
	 * 
	 * @return entity 
	 */
	private Tag createTestEntity() {
		Tag entity = getDefaultTag();
		database.insertTag(entity, 1L);
		return entity;
	}
	
	/*
	 * Test findById()
	 */		
	@Test
	public void findById() {
		Tag exp = createTestEntity();
		Tag act = tagRepository.findById(exp.getId());
		EntityComparator.verifyEqual(exp, act);
		
		// Bad ID
		act = tagRepository.findById(exp.getId() + 1);
		assertNull(act);
	}
	
	/*
	 * Test create()
	 */	
	@Test
	public void create() {
		Tag tag = getDefaultTag();
		tagRepository.create(tag);
		super.flushAndEvict(tag);
		Tag exp = getDefaultTag();
		Tag act = tagRepository.findById(tag.getId());
		EntityComparator.verifyEqual(exp, act);
	}
	
	/*
	 * Test update()
	 */	
	private void makeChanges(Tag tag) {
		tag.setName(tag.getName()+"-updated");
		// For dynamic properties, add new, update existing and remove existing
		tag.getProperties()
			.setProperty("new-property", "new-value")
			.setProperty("text", "text-value-new")
			.removeProperty("number");		
	}
	
	@Test
	public void update() {
		Tag exp = createTestEntity();
		
		// Update
		Tag tag = tagRepository.findById(exp.getId());
		makeChanges(tag);
		tagRepository.update(tag);
		flushAndEvict(tag);
		
		// Verify
		makeChanges(exp);
		Tag act = tagRepository.findById(tag.getId());
		EntityComparator.verifyEqual(exp, act);
	}
	
	/*
	 * Test delete()
	 */
	@Test
	public void delete() {	
		Tag tag = createTestEntity();
		super.verifyRowCount(Tables.TAG, 1);
		tagRepository.delete(tag);
		flushAndEvict(tag);
		super.verifyRowCount(Tables.TAG, 0);
	}
	
	/*
	 * Test findByName()
	 */
	@Test
	public void findByName() {	
		Tag exp = createTestEntity();
		Tag act = tagRepository.findByName(exp.getName());
		EntityComparator.verifyEqual(exp, act);
		
		// Bad ID
		act = tagRepository.findByName(exp.getName().substring(1));
		assertNull(act);
	}
	
	/*
	 * Test getAll()
	 */
	@Test
	public void getAll() {
		for (int i = 1; i <= 5; i++) {
			database.insertTag(new Tag("T-"+i), Long.valueOf(i));
		}
		super.verifyRowCount(Tables.TAG, 5);
		List<Tag> act = tagRepository.getAll();
		assertEquals(5, act.size());
	}
}