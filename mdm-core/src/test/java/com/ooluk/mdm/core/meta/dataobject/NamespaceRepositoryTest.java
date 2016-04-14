package com.ooluk.mdm.core.meta.dataobject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;
import org.springframework.context.annotation.Configuration;

import com.ooluk.mdm.core.meta.PropertyGroup;
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
 * - Find namespace by its ID
 * - Create a namespace
 * - Update a namespace
 * - Delete a namespace
 * - Get all namespaces 
 * - Find a namespace by its natural key: name
 * </pre>
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
public class NamespaceRepositoryTest extends AbstractRepositoryTest {

	@Configuration
	static class ContextConfiguration {		
	}
	
	private static String NS_NAME = "ns1";
	
	private Namespace getDefaultNamespace() {
		return TestData.getNamespace(NS_NAME);
	}
	
	private Namespace createDefaultNamespace() {
		Namespace ns = getDefaultNamespace();
		// INSERT property group
		PropertyGroup pg = ns.getPropertyGroup();
		database.insertPropertyGroup(pg, 1L);
		// INSERT namespace
		database.insertNamespace(ns, 1L);
		return ns;
	}
	
	/*
	 * Test findById()
	 */		
	@Test
	public void findById() {
		Namespace exp = createDefaultNamespace();
		Namespace act = nsRepository.findById(exp.getId());
		EntityComparator.verifyEqual(exp, act);
		
		// Bad ID
		act = nsRepository.findById(exp.getId() + 1);
		assertNull(act);
	}
	
	/*
	 * Test create()
	 */	
	@Test
	public void create() {
		Namespace ns = getDefaultNamespace();
		// INSERT property group
		PropertyGroup pg = ns.getPropertyGroup();
		database.insertPropertyGroup(pg, 1L);
		ns.setPropertyGroup(pg);
		
		// Create
		super.verifyRowCount(Tables.NAMESPACE, 0);
		nsRepository.create(ns);
		super.flushAndEvict(ns);
		
		// Verify
		super.verifyRowCount(Tables.NAMESPACE, 1);
		Namespace exp = getDefaultNamespace();
		Namespace act = nsRepository.findById(ns.getId());
		EntityComparator.verifyEqual(exp, act);
	}
	
	/*
	 * Test update()
	 */	
	private void makeChanges(Namespace ns) {
		ns.setName(ns.getName()+"-updated");
		// For dynamic properties, add new, update existing and remove existing
		ns.getProperties()
			.setProperty("new-property", "new-value")
			.setProperty("text", "text-value-new")
			.removeProperty("number");		
	}
	
	@Test
	public void update() {	
		Namespace exp = createDefaultNamespace();
		
		// Update
		Namespace ns = nsRepository.findById(exp.getId());
		makeChanges(ns);
		nsRepository.update(ns);
		flushAndEvict(ns);
		
		// Verify by making the same changes to expected
		makeChanges(exp);
		Namespace act = nsRepository.findById(ns.getId());
		EntityComparator.verifyEqual(exp, act);
	}
	
	/*
	 * Test delete()
	 */
	@Test
	public void delete() {		
		Namespace ns = createDefaultNamespace();
		super.verifyRowCount(Tables.NAMESPACE, 1);
		nsRepository.delete(ns);
		flushAndEvict(ns);
		super.verifyRowCount(Tables.NAMESPACE, 0);
	}
	
	/*
	 * Test getAll()
	 */
	private void addNamespaces(int count) {
		PropertyGroup pg = TestData.getPropertyGroup("PG1");
		database.insertPropertyGroup(pg, 1L);
		for (int i = 1; i <= count; i++) {
			Namespace ns = getDefaultNamespace();
			ns.setPropertyGroup(pg);
			database.insertNamespace(ns, Long.valueOf(i));
		}		
	}	
	
	@Test
	public void getAll() {
		addNamespaces(5);
		List<Namespace> act = nsRepository.getAll();
		assertEquals(5, act.size());
	}
	
	/*
	 * Test findByName()
	 */
	@Test
	public void findByName() {			
		Namespace exp = createDefaultNamespace();
		Namespace act = nsRepository.findByName(exp.getName());
		EntityComparator.verifyEqual(exp, act);
		
		// Bad name
		act = nsRepository.findByName(exp.getName().substring(1));
		assertNull(act);
	}
}