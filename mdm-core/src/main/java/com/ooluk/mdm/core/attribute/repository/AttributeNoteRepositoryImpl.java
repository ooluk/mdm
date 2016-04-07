package com.ooluk.mdm.core.attribute.repository;

import org.springframework.stereotype.Repository;

import com.ooluk.mdm.core.attribute.data.AttributeNote;
import com.ooluk.mdm.core.base.data.GenericRepositoryImpl;

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