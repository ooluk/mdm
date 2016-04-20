package com.ooluk.mdm.core.meta.app;

import java.util.Objects;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ooluk.mdm.core.meta.StandaloneEntity;

/**
 * Tags can be attached to data objects and attributes to allow an easy way of finding similar data
 * objects and attributes across the software instance. A tag is uniquely identified by its name.
 * 
 * @author Siddhesh Prabhu
 * @since 1.0
 *
 */
@Entity
@Table (name = "mdm_tag")
@AttributeOverrides ( value = { 
		@AttributeOverride ( name = "id", column = @Column ( name = "tag_id") ),
		@AttributeOverride ( name = "properties", column = @Column ( name = "tag_properties") )  
}) 
public class Tag extends StandaloneEntity<Tag> {
	
	private static final long serialVersionUID = 1L;
	
	@Column ( name = "tag_name")
	private String name;
	
	/**
	 * Constructs a new tag
	 */
	public Tag() {
	}
	
	/**
	 * Constructs a new tag with the specified name.
	 * 
	 * @param name
	 *            tag name
	 */
	public Tag(String name) {
		this();
		this.name = name;
	}
	
	/**
	 * Returns the name of the tag.
	 * 
	 * @return tag name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the tag name
	 * 
	 * @param name
	 *            name of the tag
	 *            
	 * @return a reference to this object to allow chaining of method calls.
	 */
	public Tag setName(String name) {
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
		Tag other = (Tag) obj;
		return Objects.equals(getName(), other.getName());
	}

	@Override
	public String toString() {
		return "Tag [id=" + getId() + ", name=" + name + ", properties="
				+ getProperties() + "]";
	}
}