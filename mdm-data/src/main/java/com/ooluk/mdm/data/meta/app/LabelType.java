package com.ooluk.mdm.data.meta.app;

import java.util.Objects;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ooluk.mdm.data.meta.StandaloneEntity;

/**
 * A label type is a categorization for labels. A label type is uniquely identified by its type
 * name. There are 3 built-in label types : APPLICATION, TEHCNICAL and BUSINESS. These are
 * represented by the {@link ReservedTypeName} enum.
 * 
 * @author Siddhesh Prabhu
 * @since 1.0
 *
 */
@Entity
@Table (name = "mdm_label_type")
@AttributeOverrides ( value = { 
		@AttributeOverride ( name = "id", column = @Column ( name = "lbl_type_id") ),
		@AttributeOverride ( name = "properties", column = @Column ( name = "lbl_type_properties") )  
}) 
public class LabelType extends StandaloneEntity<LabelType> {

	private static final long serialVersionUID = 1L;
	
	public static enum ReservedTypeName {
		APPLICATION, TECHNICAL, BUSINESS
	}
	
	@Column ( name="lbl_type_name" )
	private String name;
	
	/**
	 * Constructs a new label type
	 */
	public LabelType() {}
	
	/**
	 * Constructs a new label type with the specified name.
	 * 
	 * @param name
	 *            type name
	 */
	public LabelType(String name) {
		this.name = name;
	}
	/**
	 * Constructs a new label type with the specified reserved type name.
	 * 
	 * @param name
	 *            reserved type name
	 */
	public LabelType(ReservedTypeName name) {
		this.name = name.toString();
	}

	/**
	 * Returns the label name
	 * 
	 * @return label name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the label's name
	 * 
	 * @param name
	 *            label name
	 *            
	 * @return a reference to this object to allow chaining of methods calls.
	 */
	public LabelType setName(String name) {
		this.name = name;
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
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
		LabelType other = (LabelType) obj;
		return Objects.equals(getName(), other.getName());
	}

	@Override
	public String toString() {
		return "LabelType [id=" + getId() + ", name=" + name + ", properties=" + getProperties() + "]";
	}
}