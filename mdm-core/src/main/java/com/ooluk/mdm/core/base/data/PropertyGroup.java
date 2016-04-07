package com.ooluk.mdm.core.base.data;

import java.util.Objects;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * A PropertyGroup serves to group dynamic properties that are valid for a given context (Relational
 * Database, Amazon DynamoDB, Flat File, ...). A many-to-many relationship exists between dynamic
 * properties and property group.
 * 
 * <p>
 * For example the "DataObject" metaobject will have some universal properties such as "definition"
 * and "summary" and some properties that are only valid in certain contexts such as a relational
 * database or an Amazon DynamoDB database.
 * 
 * @author Siddhesh Prabhu
 * @since 1.0
 *
 */
@Entity
@Table ( name = "mdm_property_group")
@AttributeOverrides ( value = { 
		@AttributeOverride ( name = "id", column = @Column ( name = "pgroup_id") ),
		@AttributeOverride ( name = "properties", column = @Column ( name = "pgroup_properties") )  
}) 
public class PropertyGroup extends StandaloneEntity<PropertyGroup> {

	private static final long serialVersionUID = 1L;

	@Column ( name = "pgroup_name" )
	private String name;
	
	/**
	 * Creates a new property group
	 */
	public PropertyGroup() {
		super();
	}

	/**
	 * Creates a new property group with a name
	 * 
	 * @param name
	 *            group name
	 */
	public PropertyGroup(String name) {
		this();
		this.name = name;
	}

	/**
	 * Returns the property group name.
	 * 
	 * @return property group name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the property group name
	 * 
	 * @param name
	 *            property group name
	 *            
	 * @return a reference to this object to allow method chaining
	 */
	public PropertyGroup setName(String name) {
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
		PropertyGroup other = (PropertyGroup) obj;
		return Objects.equals(this.getName(), other.getName());
	}

	@Override
	public String toString() {
		return "PropertyGroup [id=" + getId() + ", name=" + name + ", properties=" + getProperties() + "]";
	}
}