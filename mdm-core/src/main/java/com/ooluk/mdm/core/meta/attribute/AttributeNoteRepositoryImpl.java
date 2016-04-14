package com.ooluk.mdm.core.meta.attribute;

import org.springframework.stereotype.Repository;

import com.ooluk.mdm.core.meta.GenericRepositoryImpl;

/**
 * Repository implementation for attribute notes.
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
@Repository
public class AttributeNoteRepositoryImpl extends GenericRepositoryImpl<AttributeNote> 
		implements AttributeNoteRepository {	

	public AttributeNoteRepositoryImpl() {
		super(AttributeNote.class);
	}
}