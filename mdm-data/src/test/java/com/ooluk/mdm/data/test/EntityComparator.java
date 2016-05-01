package com.ooluk.mdm.data.test;

import static org.junit.Assert.*;

import java.util.Set;

import com.ooluk.mdm.data.meta.DynamicProperties;
import com.ooluk.mdm.data.meta.DynamicProperty;
import com.ooluk.mdm.data.meta.ListValue;
import com.ooluk.mdm.data.meta.PropertyGroup;
import com.ooluk.mdm.data.meta.app.Label;
import com.ooluk.mdm.data.meta.app.LabelType;
import com.ooluk.mdm.data.meta.app.Tag;
import com.ooluk.mdm.data.meta.attribute.Attribute;
import com.ooluk.mdm.data.meta.attribute.AttributeNote;
import com.ooluk.mdm.data.meta.attribute.AttributeSourceMapping;
import com.ooluk.mdm.data.meta.dataobject.DataObject;
import com.ooluk.mdm.data.meta.dataobject.DataObjectNote;
import com.ooluk.mdm.data.meta.dataobject.DataObjectSourceMapping;
import com.ooluk.mdm.data.meta.dataobject.Namespace;
import com.ooluk.mdm.data.meta.index.Index;
import com.ooluk.mdm.data.meta.index.IndexAttributeMapping;

/**
 * This class provides methods to deep compare all entities. It is vital to avoid cyclic
 * comparisons.
 * 
 * @author Siddhesh Prabhu
 * @since 1.0
 *
 */
public class EntityComparator {
	
	/**
	 * Verifies dynamic properties.
	 * 
	 * @param exp
	 *            expected dynamic properties
	 * @param act
	 *            actual dynamic properties
	 */            
	private static void verifyEqual(DynamicProperties exp, DynamicProperties act) {
		assertEquals(exp, act);
	}
	
	/**
	 * Compares 2 sets using verifyEqual() on its elements; this allows deep comparison. The element
	 * pair for verification is picked up using the equals() semantics of the objects.
	 * 
	 * @param exp
	 *            expected list
	 * @param act
	 *            actual list
	 */
	public static <T> void verifyLists(Set<T> exp, Set<T> act) {
		assertEquals(exp.size(), act.size());
		for (T eV : exp) {
			boolean found = false;
			for (T aV : act) {
				if (eV.equals(aV)) {
					verifyEqual(eV, aV);
					found = true;
					break;
				}
			}
			if (!found) 
				fail("List value " + eV + " not found");
		}
	}
	
	public static void verifyEqual(Object exp, Object act) {
		Class<?> cls = exp.getClass();
		if (cls.equals(PropertyGroup.class)) 
			verifyEqual((PropertyGroup)exp, (PropertyGroup)act);
		else if (cls.equals(ListValue.class)) 
			verifyEqual((ListValue)exp, (ListValue)act);	
		else if (cls.equals(Tag.class)) 
			verifyEqual((Tag)exp, (Tag)act);		
		else if (cls.equals(Label.class)) 
			verifyEqual((Label)exp, (Label)act);		
		else if (cls.equals(DataObjectNote.class)) 
			verifyEqual((DataObjectNote)exp, (DataObjectNote)act);			
		else if (cls.equals(DataObjectSourceMapping.class)) 
			verifyEqual((DataObjectSourceMapping)exp, (DataObjectSourceMapping)act);		
		else if (cls.equals(Attribute.class)) 
			verifyEqual((Attribute)exp, (Attribute)act);				
		else if (cls.equals(AttributeNote.class)) 
			verifyEqual((AttributeNote)exp, (AttributeNote)act);			
		else if (cls.equals(AttributeSourceMapping.class)) 
			verifyEqual((AttributeSourceMapping)exp, (AttributeSourceMapping)act);			
		else if (cls.equals(Index.class)) 
			verifyEqual((Index)exp, (Index)act);			
		else if (cls.equals(IndexAttributeMapping.class)) 
			verifyEqual((IndexAttributeMapping)exp, (IndexAttributeMapping)act);	
		else 
			fail("Unexpected class " + exp.getClass());
	}
	
	public static void verifyEqual(PropertyGroup exp, PropertyGroup act) {
		//assertEquals(exp.getId(), act.getId());
		assertEquals(exp.getName(), act.getName());	
		verifyEqual(exp.getProperties(), act.getProperties());
	}
	
	public static void verifyEqual(ListValue exp, ListValue act) {
		assertEquals(exp.getValue(), act.getValue());	
		verifyEqual(exp.getProperties(), act.getProperties());
	}
	
	public static void verifyEqual(DynamicProperty exp, DynamicProperty act) {
		assertEquals(exp.getMetaObjectType(), act.getMetaObjectType());
		verifyLists(exp.getPropertyGroups(), act.getPropertyGroups());
		assertEquals(exp.getKey(), act.getKey());
		assertEquals(exp.getType(), act.getType());
		assertEquals(exp.getSize(), act.getSize());
		assertEquals(exp.isMandatory(), act.isMandatory());
		assertEquals(exp.getDefaultValue(), act.getDefaultValue());
		assertEquals(exp.getFormat(), act.getFormat());
		assertEquals(exp.getDescription(), act.getDescription());
		assertEquals(exp.getVisualAttributes().getCaption(), act.getVisualAttributes().getCaption());
		assertEquals(exp.getVisualAttributes().getMessage(), act.getVisualAttributes().getMessage());
		assertEquals(exp.getVisualAttributes().getOrdinalPosition(), act.getVisualAttributes().getOrdinalPosition());
		// Compare list values
		verifyLists(exp.getValueList(), act.getValueList());		
	}
	
	public static void verifyEqual(LabelType exp, LabelType act) {
		assertEquals(exp.getName(), act.getName());	
		verifyEqual(exp.getProperties(), act.getProperties());
	}
	
	public static void verifyEqual(Label exp, Label act) {
		verifyEqual(exp.getType(), act.getType());	
		assertEquals(exp.getName(), act.getName());	
		verifyEqual(exp.getProperties(), act.getProperties());
	}
	
	public static void verifyEqual(Tag exp, Tag act) {
		assertEquals(exp.getName(), act.getName());	
		verifyEqual(exp.getProperties(), act.getProperties());
	}
	
	public static void verifyEqual(Namespace exp, Namespace act) {
		assertEquals(exp.getName(), act.getName());	
		verifyEqual(exp.getProperties(), act.getProperties());
		verifyEqual(exp.getPropertyGroup(), act.getPropertyGroup());
	}
	
	public static void verifyEqual(DataObject exp, DataObject act) {
		verifyEqual(exp.getNamespace(), act.getNamespace());
		assertEquals(exp.getName(), act.getName());	
		verifyEqual(exp.getProperties(), act.getProperties());
		// labels
		verifyLists(exp.getLabels(), act.getLabels());
		// tags
		verifyLists(exp.getTags(), act.getTags());
		// notes
		verifyLists(exp.getNotes(), act.getNotes());
		// Do NOT verify attributes and indexes here - will result in a cycle
	}
	
	public static void verifyEqual(DataObjectSourceMapping exp, DataObjectSourceMapping act) {
		assertEquals(exp.getKey().getSourceDataObject(), act.getKey().getSourceDataObject());	
		assertEquals(exp.getKey().getTargetDataObject(), act.getKey().getTargetDataObject());
		verifyEqual(exp.getProperties(), act.getProperties());
	}
	
	public static void verifyEqual(DataObjectNote exp, DataObjectNote act) {
		assertEquals(exp.getText(), act.getText());	
		verifyEqual(exp.getProperties(), act.getProperties());
	}
	
	public static void verifyEqual(Attribute exp, Attribute act) {
		assertEquals(exp.getName(), act.getName());	
		verifyEqual(exp.getDataObject(), act.getDataObject());
		verifyEqual(exp.getProperties(), act.getProperties());
		// labels
		verifyLists(exp.getLabels(), act.getLabels());
		// tags
		verifyLists(exp.getTags(), act.getTags());
		// notes
		verifyLists(exp.getNotes(), act.getNotes());
		// Do NOT verify indexes here - will result in a cycle
	}
	
	public static void verifyEqual(AttributeSourceMapping exp, AttributeSourceMapping act) {
		assertEquals(exp.getKey().getSourceAttribute(), act.getKey().getSourceAttribute());	
		assertEquals(exp.getKey().getTargetAttribute(), act.getKey().getTargetAttribute());
		verifyEqual(exp.getProperties(), act.getProperties());
	}
	
	public static void verifyEqual(AttributeNote exp, AttributeNote act) {
		assertEquals(exp.getText(), act.getText());	
		verifyEqual(exp.getProperties(), act.getProperties());
	}
	
	public static void verifyEqual(Index exp, Index act) {
		assertEquals(exp.getName(), act.getName());	
		verifyEqual(exp.getProperties(), act.getProperties());
		assertEquals(exp.getDataObject(), act.getDataObject());
	}
	
	public static void verifyEqual(IndexAttributeMapping exp, IndexAttributeMapping act) {
		verifyEqual(exp.getKey().getIndex(), act.getKey().getIndex());
		assertEquals(exp.getKey().getAttribute(), act.getKey().getAttribute());
		verifyEqual(exp.getProperties(), act.getProperties());
	}
}