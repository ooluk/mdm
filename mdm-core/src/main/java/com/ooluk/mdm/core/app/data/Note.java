package com.ooluk.mdm.core.app.data;

import javax.persistence.MappedSuperclass;

import org.hibernate.search.annotations.Field;

import com.ooluk.mdm.core.base.data.StandaloneEntity;

/**
 * Notes are allowed on certain meta objects to provide temporary additional information.
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
@MappedSuperclass
public abstract class Note extends StandaloneEntity<Note> {
	
	private static final long serialVersionUID = 1L;
	
	@Field
	private String text;

	/**
	 * Returns the note text.
	 * 
	 * @return note text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets the note text.
	 * 
	 * @param text
	 *            note text
	 *            
	 * @return a reference to this to allow chaining of method calls.
	 */
	public Note setText(String text) {
		this.text = text;
		return this;
	}
}