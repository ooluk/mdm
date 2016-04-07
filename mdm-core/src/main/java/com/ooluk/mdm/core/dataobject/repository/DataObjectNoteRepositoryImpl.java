package com.ooluk.mdm.core.dataobject.repository;

import org.springframework.stereotype.Repository;

import com.ooluk.mdm.core.base.data.GenericRepositoryImpl;
import com.ooluk.mdm.core.dataobject.data.DataObjectNote;

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