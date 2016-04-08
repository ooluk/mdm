package com.ooluk.mdm.core.app.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

import com.ooluk.mdm.core.app.data.Label;
import com.ooluk.mdm.core.app.data.LabelType;
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
 * - Find a label by ID
 * - Create a label
 * - Update a label
 * - Delete a label
 * - Find a label by natural key: type and name
 * - Find all labels by type
 * + Get child labels
 * + Get parent labels
 * + Add a child label
 * + Remove a child label  
 * - Get root labels
 * </pre>
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
@ContextConfiguration
public class LabelRepositoryTest extends AbstractRepositoryTest {

	@Configuration
	static class ContextConfiguration {	
		
		@Bean
		public LabelRepository labelRepository() {
			return new LabelRepositoryImpl();
		}	
	}
	
	@Autowired
	private LabelRepository lblRepository;
	
	private static String LABEL_TYPE = "type1";
	private static String LABEL_NAME = "name1";
	
	/**
	 * Returns a transient default label
	 * 
	 * @return label
	 */
	private Label getDefaultLabel() {
		return TestData.getLabel(LABEL_TYPE, LABEL_NAME);
	}
	
	/**
	 * Creates a label entity
	 * 
	 * @return entity ID
	 */
	private Label createTestEntity() {
		Label label = getDefaultLabel();
		database.insertLabelType(label.getType(), 1L);
		database.insertLabel(label, 1L);
		return label;
	}
	
	/*
	 * Test findById()
	 */		
	@Test
	public void findById() {	
		Label exp = createTestEntity();
		Label act = lblRepository.findById(exp.getId());
		EntityComparator.verifyEqual(exp, act);
		
		// Bad ID
		act = lblRepository.findById(exp.getId() + 1);
		assertNull(act);
	}
	
	/*
	 * Test create()
	 */	
	@Test
	public void create() {
		Label lbl = getDefaultLabel();
		database.insertLabelType(lbl.getType(), 1L);
		lblRepository.create(lbl);
		super.flushAndEvict(lbl);
		Label exp = getDefaultLabel();
		Label act = lblRepository.findById(lbl.getId());
		EntityComparator.verifyEqual(exp, act);
	}
	
	/*
	 * Test update()
	 */	
	private void makeChanges(Label label, LabelType newType) {
		label.setType(newType);
		label.setName(label.getName()+"-updated");
		// For dynamic properties, add new, update existing and remove existing
		label.getProperties()
			.setProperty("new-property", "new-value")
			.setProperty("text", "text-value-new")
			.removeProperty("number");		
	}
	
	@Test
	public void update() {
		Label exp = createTestEntity();	
		
		// Create new type to change the label's type
		LabelType type = new LabelType("T-2");
		database.insertLabelType(type, 2L);
		super.verifyRowCount(Tables.LABEL_TYPE, 2);
		
		// Update
		Label lbl = lblRepository.findById(exp.getId());
		makeChanges(lbl, type);
		lblRepository.update(lbl);
		flushAndEvict(lbl);
		
		// Verify
		makeChanges(exp, type);
		Label act = lblRepository.findById(lbl.getId());
		EntityComparator.verifyEqual(exp, act);
	}
	
	/*
	 * Test delete()
	 */
	@Test
	public void delete() {	
		Label lbl = createTestEntity();	
		super.verifyRowCount(Tables.LABEL, 1);
		lblRepository.delete(lbl);
		flushAndEvict(lbl);	
		super.verifyRowCount(Tables.LABEL, 0);		
	}
	
	/*
	 * Test findByTypeAndName()
	 */
	@Test
	public void findByTypeAndName() {	
		Label exp = createTestEntity();
		Label act = lblRepository.findByTypeAndName(exp.getType(), exp.getName());
		EntityComparator.verifyEqual(exp, act);	
		
		// Bad type
		LabelType type = new LabelType("T-2");
		TestUtils.setIdField(type, 100L);
		act = lblRepository.findByTypeAndName(type, exp.getName());
		assertNull(act);
		
		// Bad name
		act = lblRepository.findByTypeAndName(exp.getType(), exp.getName().substring(1));
		assertNull(act);
	}
	
	/*
	 * Test findByType()
	 */
	@Test
	public void findByType() {
		// Create 2 label types
		LabelType type1 = TestData.getLabelType("type1");
		database.insertLabelType(type1, 1L);
		LabelType type2 = TestData.getLabelType("type2");
		database.insertLabelType(type2, 2L);
		super.verifyRowCount(Tables.LABEL_TYPE, 2);
		
		// Create 1 label for type1 and 2 labels for type2
		Label l1 = getDefaultLabel().setType(type1);
		Label l2 = getDefaultLabel().setType(type2);
		Label l3 = getDefaultLabel().setType(type2);
		database.insertLabel(l1, 1L);
		database.insertLabel(l2, 2L);
		database.insertLabel(l3, 3L);
		super.flush();
		super.verifyRowCount(Tables.LABEL, 3);
		
		// Fetch labels of type1
		assertEquals(1, lblRepository.findByType(type1).size());
		// Fetch labels of type2
		assertEquals(2, lblRepository.findByType(type2).size());
	}
	
	/*
	 * Test retrieval of child labels
	 */	
	
	/**
	 * Creates labels
	 * 
	 * @param count
	 *            number of labels
	 *            
	 * @return array of labels
	 */
	private Label[] createLabels(int count) {
		LabelType type = new LabelType(LABEL_TYPE);
		database.insertLabelType(type, 1L);
		Label[] labels = new Label[count];
		for (int i = 0; i < count; i++) {
			labels[i] = getDefaultLabel()
					.setType(type).setName("L"+i);
			database.insertLabel(labels[i], Long.valueOf(i));
		}
		return labels;
	}
	
	@Test
	public void _getChildren() {
		Label[] labels = createLabels(3);
		database.insertChildLabel(labels[0], labels[1]);
		database.insertChildLabel(labels[0], labels[2]);
		Label label = lblRepository.findById(labels[0].getId());
		HashSet<Label> lbls = new HashSet<>(label.getChildren());
		assertEquals(2, lbls.size());
		assertTrue(lbls.contains(labels[1]));
		assertTrue(lbls.contains(labels[2]));
	}
	
	/*
	 * Test retrieval of parent labels
	 */	
	@Test
	public void _getParents() {
		Label[] labels = createLabels(3);
		database.insertChildLabel(labels[1], labels[0]);
		database.insertChildLabel(labels[2], labels[0]);
		Label label = lblRepository.findById(labels[0].getId());
		assertEquals(2, label.getParents().size());
		HashSet<Label> lbls = new HashSet<>(label.getParents());
		assertEquals(2, lbls.size());
		assertTrue(lbls.contains(labels[1]));
		assertTrue(lbls.contains(labels[2]));
	}
	
	/*
	 * Test addition of a child label
	 */
	@Test
	public void _addChild() {
		Label[] labels = createLabels(3);
		Label l1 = lblRepository.findById(labels[0].getId());
		Label l2 = lblRepository.findById(labels[1].getId());
		Label l3 = lblRepository.findById(labels[2].getId());
		l1.addChild(l2);
		l1.addChild(l3);
		super.flushAndEvict(l1);
		Label act = lblRepository.findById(l1.getId());
		assertEquals(2, act.getChildren().size());
	}
	
	/*
	 * Test removal of a child label
	 */
	@Test
	public void _removeChild() {
		Label[] labels = createLabels(3);
		database.insertChildLabel(labels[0], labels[1]);
		database.insertChildLabel(labels[0], labels[2]);
		Label label = lblRepository.findById(labels[0].getId());
		label.getChildren().remove(labels[1]);
		super.flushAndEvict(label);
		Label act = lblRepository.findById(labels[0].getId());
		assertEquals(1, act.getChildren().size());
	}
	
	/*
	 * Test getRootLabels()
	 */
	
	@Test
	public void getRootLabels() {
		Label[] labels = createLabels(5);
		database.insertChildLabel(labels[0], labels[1]);
		database.insertChildLabel(labels[0], labels[2]);
		List<Label> roots = lblRepository.findRootLabels();
		// Of labels 0-4, label 1 and label 2 are children of 0.
		assertEquals(3, roots.size());
	}
	
	@Test
	public void getRootLabels_No_Roots() {
		Label[] labels = createLabels(2);
		database.insertChildLabel(labels[0], labels[1]);
		database.insertChildLabel(labels[1], labels[0]);
		List<Label> roots = lblRepository.findRootLabels();
		assertEquals(0, roots.size());
	}
}