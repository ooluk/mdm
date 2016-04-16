package com.ooluk.mdm.rest.test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.ooluk.mdm.core.meta.DynamicProperties;
import com.ooluk.mdm.core.meta.DynamicProperty;
import com.ooluk.mdm.core.meta.DynamicPropertyType;
import com.ooluk.mdm.core.meta.ListValue;
import com.ooluk.mdm.core.meta.MetaObjectType;
import com.ooluk.mdm.core.meta.PropertyGroup;
import com.ooluk.mdm.core.meta.VisualAttributes;
import com.ooluk.mdm.core.meta.app.Label;
import com.ooluk.mdm.core.meta.app.LabelType;
import com.ooluk.mdm.core.meta.app.Tag;
import com.ooluk.mdm.core.meta.attribute.Attribute;
import com.ooluk.mdm.core.meta.attribute.AttributeNote;
import com.ooluk.mdm.core.meta.dataobject.DataObject;
import com.ooluk.mdm.core.meta.dataobject.DataObjectNote;
import com.ooluk.mdm.core.meta.dataobject.Namespace;
import com.ooluk.mdm.core.meta.index.Index;

/**
 * Class for building in-memory representation of test data. 
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
public class TestData {
	
	/**
	 * Returns a map of possible types of dynamic property values.
	 */
	public static DynamicProperties getDynamicProperties() {
		Map<String, Object> map = new HashMap<>();
		map.put("text", "text-value");
		map.put("number", 100);
		map.put("text-list", Arrays.asList(new String[]{"t1", "t2"}));
		map.put("number-list", Arrays.asList(new Integer[]{1, 2}));
		return new DynamicProperties(map);		
	}
		
	/**
	 * Returns a default property group.
	 * 
	 * @return PropertyGroup
	 */
	public static PropertyGroup getPropertyGroup(String name) {
		PropertyGroup pgroup = (new PropertyGroup())
				.setName(name)
				.setProperties(getDynamicProperties());
		return pgroup;
	}
	
	/**
	 * Returns a dynamic property of SINGLE_LINE_TEXT type
	 *            
	 * @return DynamicProperty
	 */
	public static  DynamicProperty getDynamicProperty() {
		return getDynamicProperty(DynamicPropertyType.SINGLE_LINE_TEXT);
	}
	
	/**
	 * Returns a dynamic property of the specified type
	 * 
	 * @param type
	 *            dynamic property type
	 *            
	 * @return DynamicProperty
	 */
	public static  DynamicProperty getDynamicProperty(DynamicPropertyType type) {
		
		DynamicProperty dp = (new DynamicProperty())
				.setMetaObjectType(MetaObjectType.DATA_OBJECT)
				.attachPropertyGroup(getPropertyGroup("pg1"))
				.setKey("key1")
				.setType(type)
				.setMandatory(true)
				.setDefaultValue("default1")
				.setSize(5)
				.setFormat("format1")
				.setDescription("description1")
				.setVisualAttributes((new VisualAttributes())
						.setCaption("caption1")
						.setMessage("message1")
						.setOrdinalPosition(1))
				.setProperties(new DynamicProperties());
		
		if (type.isListType()) {
			Set<ListValue> lv = dp.getValueList();
			for (int i = 1; i <= 2; i++) {
				ListValue v = new ListValue();
				v.setValue("V"+i).setProperties(new DynamicProperties());
				v.setProperty(dp);
				lv.add(v);
			}
		}
		return dp;
	}
	
	/**
	 * Returns a label type with the specified name.
	 * 
	 * @param name
	 *            label type name
	 * 
	 * @return label type
	 */
	public static LabelType getLabelType(String name, Long id) {		
		LabelType type = (new LabelType(name)).setProperties(getDynamicProperties());
		TestUtils.setIdField(type, id);
		return type;
	}
	
	/**
	 * Returns a label of the specified type and name.
	 * 
	 * @param type
	 *            type name
	 * @param name
	 *            label name
	 * 
	 * @return label
	 */
	public static Label getLabel(String type, String name, Long id) {		
		Label label = (new Label(getLabelType(type, 1L), name)).setProperties(getDynamicProperties());
		TestUtils.setIdField(label, id);
		return label;
	}
	
	/**
	 * Returns a tag.
	 * 
	 * @param name
	 *            tag name
	 *            
	 * @return tag
	 */
	public static Tag getTag(String name) {		
		Tag tag = (new Tag(name)).setProperties(getDynamicProperties());
		return tag;
	}
	
	/**
	 * Returns a namespace. The property group is set to the default in TestData.
	 * 
	 * @param name
	 *            namespace name
	 *            
	 * @return namespace
	 */
	public static Namespace getNamespace(String name) {		
		Namespace ns = (new Namespace(name))
				.setProperties(getDynamicProperties())
				.setPropertyGroup(getPropertyGroup(name));
		return ns;
	}
	
	/**
	 * Returns a data object.
	 * 
	 * @return data object
	 */
	public static DataObject getDataObject(String ns, String name) {		
		DataObject obj = (new DataObject(getNamespace(ns), name))
				.setProperties(getDynamicProperties());
		return obj;
	}
	
	/**
	 * Returns a data object note without a data object.
	 * 
	 * @return data object note
	 */
	public static DataObjectNote getDataObjectNote(String text) {		
		DataObjectNote note = (DataObjectNote) (new DataObjectNote())
				.setText(text)
				.setProperties(getDynamicProperties());
		return note;
	}
	
	/**
	 * Returns an attribute.
	 * 
	 * @return attribute
	 */
	public static Attribute getAttribute(String ns, String objName, String attrName) {		
		Attribute attr = new Attribute(getDataObject(ns, objName), attrName)
				.setProperties(getDynamicProperties());
		return attr;
	}
	
	/**
	 * Returns an attribute note without an attribute.
	 * 
	 * @return attribute note
	 */
	public static AttributeNote getAttributeNote(String text) {		
		AttributeNote note = (AttributeNote) (new AttributeNote())
				.setText(text)
				.setProperties(getDynamicProperties());
		return note;
	}
	
	/**
	 * Returns an index.
	 * 
	 * @return index
	 */
	public static Index getIndex(String ns, String objName, String name) {		
		Index idx = new Index(name)
				.setDataObject(getDataObject(ns, objName))
				.setProperties(getDynamicProperties());
		return idx;
	}
}