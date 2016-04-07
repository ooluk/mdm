package com.ooluk.mdm.core.test;

import static com.ooluk.mdm.core.test.DatabaseUtils.insertSQL;
import static com.ooluk.mdm.core.test.DatabaseUtils.json;
import static com.ooluk.mdm.core.test.DatabaseUtils.quote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ooluk.mdm.core.app.data.Label;
import com.ooluk.mdm.core.app.data.LabelType;
import com.ooluk.mdm.core.app.data.Tag;
import com.ooluk.mdm.core.attribute.data.Attribute;
import com.ooluk.mdm.core.attribute.data.AttributeNote;
import com.ooluk.mdm.core.attribute.data.AttributeSourceMapping;
import com.ooluk.mdm.core.base.data.DynamicProperty;
import com.ooluk.mdm.core.base.data.ListValue;
import com.ooluk.mdm.core.base.data.PropertyGroup;
import com.ooluk.mdm.core.dataobject.data.DataObject;
import com.ooluk.mdm.core.dataobject.data.DataObjectNote;
import com.ooluk.mdm.core.dataobject.data.DataObjectSourceMapping;
import com.ooluk.mdm.core.dataobject.data.Namespace;
import com.ooluk.mdm.core.index.data.Index;
import com.ooluk.mdm.core.index.data.IndexAttributeMapping;

/**
 * This class provides methods to insert data into each of the database tables. This allows easy data
 * preparation for testing. The methods take entities as arguments allowing easy verification later
 * based on entity property values.
 * 
 * @author Siddhesh Prabhu
 * @since 1.0
 *
 */
@Component
public class Database {

	@Autowired
	protected JdbcTemplate jdbc;
	
	public void insertPropertyGroup(PropertyGroup pg, Long id) {		
		TestUtils.setIdField(pg, id);
		String[] fields = { "pgroup_id", "pgroup_name", "pgroup_properties" }; 
		StringBuilder values = (new StringBuilder())
				.append(pg.getId()).append(",")
				.append(quote(pg.getName())).append(",")
				.append(quote(json(pg.getProperties())));
		String sql = insertSQL(Tables.PROPERTY_GROUP, fields, values.toString());
		jdbc.execute(sql);
	}
	
	public void insertDynamicProperty(DynamicProperty dp, Long id) {		
		TestUtils.setIdField(dp, id);
		String[] fields = { "dymn_prop_id", "dymn_prop_meta_type", "dymn_prop_key",
				"dymn_prop_type", "dymn_prop_size", "dymn_prop_mandatory",
				"dymn_prop_default", "dymn_prop_format", "dymn_prop_description",
				"dymn_prop_ui_caption", "dymn_prop_ui_message", "dymn_prop_ui_position",
				"dymn_properties"}; 
		StringBuilder values = (new StringBuilder())
				.append(dp.getId()).append(",")
				.append(quote(dp.getMetaObjectType().toString())).append(",")
				.append(quote(dp.getKey())).append(",")
				.append(quote(dp.getType().toString())).append(",")
				.append(dp.getSize()).append(",")
				.append(dp.isMandatory()).append(",")
				.append(quote(dp.getDefaultValue())).append(",")
				.append(quote(dp.getFormat())).append(",")
				.append(quote(dp.getDescription())).append(",")
				.append(quote(dp.getVisualAttributes().getCaption())).append(",")
				.append(quote(dp.getVisualAttributes().getMessage())).append(",")
				.append(dp.getVisualAttributes().getOrdinalPosition()).append(",")
				.append(quote(json(dp.getProperties())));
		String sql = insertSQL(Tables.DYNAMIC_PROPERTY, fields, values.toString());
		jdbc.execute(sql);		
	}
	
	public void insertDynamicPropertyGroup(DynamicProperty dp, PropertyGroup pg) {
		String[] fields = { "dymn_prop_id", "pgroup_id"}; 
		StringBuilder values = (new StringBuilder())
				.append(dp.getId()).append(",")
				.append(pg.getId());				
		String sql = insertSQL(Tables.DYNAMIC_PROPERTY_GROUP, fields, values.toString());
		jdbc.execute(sql);
	}
	
	public void insertListValue(ListValue lv, Long id, DynamicProperty dp) {	
		TestUtils.setIdField(lv, id);
		String[] fields = { "lv_id", "lv_value", "dymn_prop_id", "lv_properties"}; 
		StringBuilder values = (new StringBuilder())
				.append(lv.getId()).append(",")
				.append(quote(lv.getValue())).append(",")
				.append(dp.getId()).append(",")
				.append(quote(json(dp.getProperties())));			
		String sql = insertSQL(Tables.LIST_VALUES, fields, values.toString());
		jdbc.execute(sql);
	}
	
	public void insertLabelType(LabelType type, Long id) {
		TestUtils.setIdField(type, id);
		String[] fields = { "lbl_type_id", "lbl_type_name", "lbl_type_properties" }; 
		StringBuilder values = (new StringBuilder())
				.append(type.getId()).append(",")
				.append(quote(type.getName())).append(",")
				.append(quote(json(type.getProperties())));
		String sql = insertSQL(Tables.LABEL_TYPE, fields, values.toString());
		jdbc.execute(sql);
	}
	
	public void insertLabel(Label lbl, Long id) {
		TestUtils.setIdField(lbl, id);
		String[] fields = { "lbl_id", "lbl_name", "lbl_properties", "lbl_type_id" }; 
		StringBuilder values = (new StringBuilder())
				.append(lbl.getId()).append(",")
				.append(quote(lbl.getName())).append(",")
				.append(quote(json(lbl.getProperties()))).append(",")
				.append(lbl.getType().getId());
		String sql = insertSQL(Tables.LABEL, fields, values.toString());
		jdbc.execute(sql);
	}
	
	public void insertChildLabel(Label parent, Label child) {
		String[] fields = { "parent_lbl_id", "child_lbl_id" }; 
		StringBuilder values = (new StringBuilder())
				.append(parent.getId()).append(",")
				.append(child.getId());
		String sql = insertSQL(Tables.LABEL_HIERARCHY, fields, values.toString());
		jdbc.execute(sql);
	}	
	
	public void insertTag(Tag tag, Long id) {
		TestUtils.setIdField(tag, id);
		String[] fields = { "tag_id", "tag_name", "tag_properties" }; 
		StringBuilder values = (new StringBuilder())
				.append(tag.getId()).append(",")
				.append(quote(tag.getName())).append(",")
				.append(quote(json(tag.getProperties())));
		String sql = insertSQL(Tables.TAG, fields, values.toString());
		jdbc.execute(sql);
	}
	
	public void insertNamespace(Namespace ns, Long id) {
		TestUtils.setIdField(ns, id);
		String[] fields = { "nspace_id", "nspace_name", "nspace_properties", "pgroup_id"}; 
		StringBuilder values = (new StringBuilder())
				.append(ns.getId()).append(",")
				.append(quote(ns.getName())).append(",")
				.append(quote(json(ns.getProperties()))).append(",")
				.append(ns.getPropertyGroup().getId());
		String sql = insertSQL(Tables.NAMESPACE, fields, values.toString());
		jdbc.execute(sql);
	}	
	
	public void insertDataObject(DataObject obj, Long id) {
		TestUtils.setIdField(obj, id);
		String[] fields = { "obj_id", "obj_name", "obj_properties", "nspace_id"}; 
		StringBuilder values = (new StringBuilder())
				.append(obj.getId()).append(",")
				.append(quote(obj.getName())).append(",")
				.append(quote(json(obj.getProperties()))).append(",")
				.append(obj.getNamespace().getId());
		String sql = insertSQL(Tables.DATA_OBJECT, fields, values.toString());
		jdbc.execute(sql);
	}
	
	public void insertDataObjectLabel(DataObject obj, Label lbl) {
		String[] fields = { "obj_id", "lbl_id"}; 
		StringBuilder values = (new StringBuilder())
				.append(obj.getId()).append(",")
				.append(lbl.getId());
		String sql = insertSQL(Tables.DATA_OBJECT_LABEL, fields, values.toString());
		jdbc.execute(sql);
	}
	
	public void insertDataObjectTag(DataObject obj, Tag tag) {
		String[] fields = { "obj_id", "tag_id"}; 
		StringBuilder values = (new StringBuilder())
				.append(obj.getId()).append(",")
				.append(tag.getId());
		String sql = insertSQL(Tables.DATA_OBJECT_TAG, fields, values.toString());
		jdbc.execute(sql);
	}
	
	public void insertDataObjectSource(DataObjectSourceMapping map) {
		String[] fields = { "obj_id", "source_obj_id", "properties"}; 
		StringBuilder values = (new StringBuilder())
				.append(map.getKey().getTargetDataObject().getId()).append(",")
				.append(map.getKey().getSourceDataObject().getId()).append(",")
				.append(quote(json(map.getProperties())));
		String sql = insertSQL(Tables.DATA_OBJECT_SOURCE, fields, values.toString());
		jdbc.execute(sql);
	}	
	
	public void insertDataObjectNote(DataObjectNote note, Long id) {
		TestUtils.setIdField(note, id);
		String[] fields = { "obj_note_id", "obj_note_text", "obj_note_properties", "obj_id"}; 
		StringBuilder values = (new StringBuilder())
				.append(note.getId()).append(",")
				.append(quote(note.getText())).append(",")
				.append(quote(json(note.getProperties()))).append(",")
				.append(note.getDataObject().getId());
		String sql = insertSQL(Tables.DATA_OBJECT_NOTE, fields, values.toString());
		jdbc.execute(sql);
	}
	
	public void insertAttribute(Attribute attr, Long id) {
		TestUtils.setIdField(attr, id);
		String[] fields = { "attr_id", "attr_name", "attr_properties", "obj_id"}; 
		StringBuilder values = (new StringBuilder())
				.append(attr.getId()).append(",")
				.append(quote(attr.getName())).append(",")
				.append(quote(json(attr.getProperties()))).append(",")
				.append(attr.getDataObject().getId());
		String sql = insertSQL(Tables.ATTRIBUTE, fields, values.toString());
		jdbc.execute(sql);
	}
	
	public void insertAttributeLabel(Attribute attr, Label lbl) {
		String[] fields = { "attr_id", "lbl_id"}; 
		StringBuilder values = (new StringBuilder())
				.append(attr.getId()).append(",")
				.append(lbl.getId());
		String sql = insertSQL(Tables.ATTRIBUTE_LABEL, fields, values.toString());
		jdbc.execute(sql);
	}
	
	public void insertAttributeTag(Attribute attr, Tag tag) {
		String[] fields = { "attr_id", "tag_id"}; 
		StringBuilder values = (new StringBuilder())
				.append(attr.getId()).append(",")
				.append(tag.getId());
		String sql = insertSQL(Tables.ATTRIBUTE_TAG, fields, values.toString());
		jdbc.execute(sql);
	}
	
	public void insertAttributeSource(AttributeSourceMapping map) {
		String[] fields = { "attr_id", "source_attr_id", "properties"}; 
		StringBuilder values = (new StringBuilder())
				.append(map.getKey().getTargetAttribute().getId()).append(",")
				.append(map.getKey().getSourceAttribute().getId()).append(",")
				.append(quote(json(map.getProperties())));
		String sql = insertSQL(Tables.ATTRIBUTE_SOURCE, fields, values.toString());
		jdbc.execute(sql);
	}
	
	public void insertAttributeNote(AttributeNote note, Long id) {
		TestUtils.setIdField(note, id);
		String[] fields = { "attr_note_id", "attr_note_text", "attr_note_properties", "attr_id"}; 
		StringBuilder values = (new StringBuilder())
				.append(note.getId()).append(",")
				.append(quote(note.getText())).append(",")
				.append(quote(json(note.getProperties()))).append(",")
				.append(note.getAttribute().getId());
		String sql = insertSQL(Tables.ATTRIBUTE_NOTE, fields, values.toString());
		jdbc.execute(sql);
	}
	
	public void insertIndex(Index idx, Long id) {
		TestUtils.setIdField(idx, id);
		String[] fields = { "idx_id", "idx_name", "idx_properties", "obj_id"}; 
		StringBuilder values = (new StringBuilder())
				.append(idx.getId()).append(",")
				.append(quote(idx.getName())).append(",")
				.append(quote(json(idx.getProperties()))).append(",")
				.append(idx.getDataObject().getId());
		String sql = insertSQL(Tables.INDEX, fields, values.toString());
		jdbc.execute(sql);
	}
	
	public void insertIndexAttribute(IndexAttributeMapping map) {
		String[] fields = { "idx_id", "attr_id", "properties"}; 
		StringBuilder values = (new StringBuilder())
				.append(map.getKey().getIndex().getId()).append(",")
				.append(map.getKey().getAttribute().getId()).append(",")
				.append(quote(json(map.getProperties())));
		String sql = insertSQL(Tables.ATTRIBUTE_SOURCE, fields, values.toString());
		jdbc.execute(sql);
	}
}