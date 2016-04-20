/* 
 *  Copyright 2016 Ooluk Corporation
 */
package com.ooluk.mdm.core.meta;

import javax.persistence.Embeddable;

/**
 * This class encapsulates the visual attributes of a dynamic property.
 * 
 * @author Siddhesh Prabhu
 * @since 1.0
 *
 */
@Embeddable
public class VisualAttributes {
	
	/*
	 * This refers to the caption used for the field representing the dynamic property. For example
	 * this could be displayed as a label above a combo box.
	 */
	private String caption;
	
	/*
	 * Refers to any helpful message provided with the field on the UI. This could take the form of
	 * a message below the field or a tool tip or a message in a hint box.
	 */
	private String message;
	
	/*
	 * In what order the field should be displayed w.r.t. all other dynamic properties.
	 */
	private int ordinalPosition;

	/**
	 * Returns the caption of the field.
	 * 
	 * @return caption for the field.
	 */
	public String getCaption() {
		return caption;
	}
	
	/**
	 * Sets the caption of the field.
	 * 
	 * @param caption
	 *            caption text
	 * 
	 * @return a reference to this object to allow chaining of method calls
	 */
	public VisualAttributes setCaption(String caption) {
		this.caption = caption;
		return this;
	}

	/**
	 * Returns the message for the field.
	 * 
	 * @return message for the field.
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * Sets the message for the field.
	 * 
	 * @param message
	 *            message text
	 * 
	 * @return a reference to this object to allow chaining of method calls
	 */
	public VisualAttributes setMessage(String message) {
		this.message = message;
		return this;
	}

	/**
	 * Returns the position for the field.
	 * 
	 * @return position for the field.
	 */
	public int getOrdinalPosition() {
		return ordinalPosition;
	}
	
	/**
	 * Sets the ordinal position for the field.
	 * 
	 * @param ordinalPosition
	 *            position of the field
	 * 
	 * @return a reference to this object to allow chaining of method calls
	 */	
	public VisualAttributes setOrdinalPosition(int ordinalPosition) {
		this.ordinalPosition = ordinalPosition;
		return this;
	}
	
	@Override
	public String toString() {
		return "VisualAttribute [caption=" + caption + ", message=" + message
				+ ", ordinalPosition=" + ordinalPosition + "]";
	}
}