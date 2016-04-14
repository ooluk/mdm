package com.ooluk.mdm.core.meta.dataobject;

import org.springframework.stereotype.Repository;

import com.ooluk.mdm.core.meta.GenericRepositoryImpl;

/**
 * Repository implementation for data object notes.
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
@Repository
public class DataObjectNoteRepositoryImpl extends GenericRepositoryImpl<DataObjectNote> 
		implements DataObjectNoteRepository {	

	public DataObjectNoteRepositoryImpl() {
		super(DataObjectNote.class);
	}
}