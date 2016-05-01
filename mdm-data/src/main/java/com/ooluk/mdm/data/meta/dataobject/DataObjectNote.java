package com.ooluk.mdm.data.meta.dataobject;

import java.util.Objects;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.search.annotations.Indexed;

import com.ooluk.mdm.data.meta.app.Note;

/**
 * Represents notes for data objects.
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
@Entity
@Table ( name = "mdm_data_object_note" )
@Indexed
@AttributeOverrides ( value = { 
		@AttributeOverride ( name = "id", column = @Column ( name = "obj_note_id") ),
		@AttributeOverride ( name = "text", column = @Column ( name = "obj_note_text") ),
		@AttributeOverride ( name = "properties", column = @Column ( name = "obj_note_properties") )  
}) 
public class DataObjectNote extends Note {

	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	@JoinColumn ( name = "obj_id" )
	private DataObject dataObject;

	/**
	 * Returns the data object for the note.
	 * 
	 * @return a reference to this object to allow chaining of method calls.
	 */
	public DataObject getDataObject() {
		return dataObject;
	}

	/**
	 * Sets the data object on a note. A note must be added to a data object instead of setting the
	 * data object on a note.
	 * 
	 * @param dataObject
	 *            data object
	 *            
	 * @return a reference to this to allow chaining of method calls.
	 */
	public DataObjectNote setDataObject(DataObject dataObject) {
		this.dataObject = dataObject;
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getDataObject() == null) ? 0 : getDataObject().hashCode());
		result = prime * result + ((super.getId() == null) ? 0 : super.getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataObjectNote other = (DataObjectNote) obj;
		return Objects.equals(this.getDataObject(), other.getDataObject()) &&
				Objects.equals(getId(), other.getId());
	}
}