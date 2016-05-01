package com.ooluk.mdm.data.meta.attribute;

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
 * Represents a note for attributes.
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
@Entity
@Table ( name = "mdm_attribute_note" )
@Indexed
@AttributeOverrides ( value = { 
		@AttributeOverride ( name = "id", column = @Column ( name = "attr_note_id") ),
		@AttributeOverride ( name = "text", column = @Column ( name = "attr_note_text") ),
		@AttributeOverride ( name = "properties", column = @Column ( name = "attr_note_properties") )  
}) 
public class AttributeNote extends Note {

	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	@JoinColumn ( name = "attr_id" )
	private Attribute attribute;

	/**
	 * Returns the attribute to which this note is attached.
	 * 
	 * @return attribute for the note
	 */
	public Attribute getAttribute() {
		return attribute;
	}

	/**
	 * Sets the attribute on a note. A note must be added to an attribute instead of setting the
	 * attribute on a note.
	 * 
	 * @param attribute
	 *            attribute
	 *            
	 * @return a reference to this object to allow chaining of methods.
	 */
	public AttributeNote setAttribute(Attribute attribute) {
		this.attribute = attribute;
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getAttribute() == null) ? 0 : getAttribute().hashCode());
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
		AttributeNote other = (AttributeNote) obj;
		return Objects.equals(this.getAttribute(), other.getAttribute()) &&
				Objects.equals(getId(), other.getId());
	}
}