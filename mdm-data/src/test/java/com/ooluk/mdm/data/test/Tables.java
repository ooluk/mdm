package com.ooluk.mdm.data.test;

/**
 * Table name definitions to allow easy refactoring should names change.
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
public interface Tables {

	String PROPERTY_GROUP = "mdm_property_group";
	String DYNAMIC_PROPERTY = "mdm_dynamic_property";
	String DYNAMIC_PROPERTY_GROUP = "mdm_dynamic_property_group";
	String LIST_VALUES = "mdm_list_value";
	
	String LABEL_TYPE = "mdm_label_type";
	String LABEL = "mdm_label";
	String LABEL_HIERARCHY = "mdm_label_hierarchy";
	String TAG = "mdm_tag";
	
	String NAMESPACE = "mdm_namespace";
	String DATA_OBJECT = "mdm_data_object";
	String DATA_OBJECT_LABEL = "mdm_data_object_label";
	String DATA_OBJECT_TAG = "mdm_data_object_tag";
	String DATA_OBJECT_SOURCE = "mdm_data_object_source";
	String DATA_OBJECT_NOTE = "mdm_data_object_note";
	
	String ATTRIBUTE = "mdm_attribute";
	String ATTRIBUTE_LABEL = "mdm_attribute_label";
	String ATTRIBUTE_TAG = "mdm_attribute_tag";
	String ATTRIBUTE_SOURCE = "mdm_attribute_source";
	String ATTRIBUTE_NOTE = "mdm_attribute_note";
	
	String INDEX = "mdm_index";
	String INDEX_ATTRIBUTE = "mdm_index_attribute";
}