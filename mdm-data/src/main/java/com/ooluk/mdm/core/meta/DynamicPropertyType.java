package com.ooluk.mdm.core.meta;

/**
 * Enumeration of dynamic property types.
 * 
 * @author Siddhesh Prabhu
 * @since  1.0.0
 *
 */
public enum DynamicPropertyType {

	// Text
	SINGLE_LINE_TEXT,	
	MULTI_LINE_TEXT,
	
	// Numbers
	NUMBER,
	
	// List of values
	SINGLE_CHOICE_TEXT_LIST,	
	MULTI_CHOICE_TEXT_LIST,
	SINGLE_CHOICE_NUMBER_LIST,	
	MULTI_CHOICE_NUMBER_LIST,
	
	// Date & Time
	DATE,	
	DATE_TIME,
	
	// Hyperlink
	HYPERLINK;
	
	public boolean isListType() {
		return this.equals(SINGLE_CHOICE_TEXT_LIST) || 
				this.equals(MULTI_CHOICE_TEXT_LIST) || 
				this.equals(MULTI_CHOICE_NUMBER_LIST) || 
				this.equals(MULTI_CHOICE_NUMBER_LIST);
	}
}