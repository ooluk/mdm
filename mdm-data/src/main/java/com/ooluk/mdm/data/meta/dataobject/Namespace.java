package com.ooluk.mdm.data.meta.dataobject;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import com.ooluk.mdm.data.meta.PropertyGroup;
import com.ooluk.mdm.data.meta.StandaloneEntity;

/**
 * A namespace serves to logically group data objects. A namespace is uniquely identified by its name.
 * 
 * @author Siddhesh Prabhu
 * @since 1.0
 *
 */
@Entity
@Table (name = "mdm_namespace")
@Indexed
@AttributeOverrides ( value = { 
		@AttributeOverride ( name = "id", column = @Column ( name = "nspace_id") ),
		@AttributeOverride ( name = "properties", column = @Column ( name = "nspace_properties") )  
}) 
public class Namespace extends StandaloneEntity<Namespace> {

	private static final long serialVersionUID = 1L;
	
	@Field
	@Column ( name="nspace_name" )
	private String name;
	
	@ManyToOne 
    @JoinColumn ( name = "pgroup_id", nullable = false )
	private PropertyGroup propertyGroup;
	
	@OneToMany ( fetch = FetchType.LAZY, mappedBy = "namespace" )
	private Set<DataObject> dataObjects;
	
	/**
	 * Creates a new namespace
	 */
	public Namespace() {
		dataObjects = new HashSet<>();
	}
	
	/**
	 * Constructs a new namespace with the specified name
	 * 
	 * @param name
	 *            namespace name
	 */
	public Namespace(String name) {
		this();
		this.name = name;
	}

	/**
	 * Returns the namespace name.
	 * 
	 * @return name of the namespace
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the namespace name
	 * 
	 * @param name
	 *            namespace name
	 *            
	 * @return a reference to this object to allow chaining of method calls
	 */
	public Namespace setName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * Returns the property group for this namespace
	 * 
	 * @return property group of the namespace
	 */
	public PropertyGroup getPropertyGroup() {
		return propertyGroup;
	}

	/**
	 * Sets the property group.
	 * 
	 * @param propertyGroup
	 *            property group
	 *            
	 * @return a reference to this object to allow chaining of method calls
	 */
	public Namespace setPropertyGroup(PropertyGroup propertyGroup) {
		this.propertyGroup = propertyGroup;
		return this;
	}

	/**
	 * Returns the data objects that belong to this namespace.
	 * 
	 * @return list of data objects that belong to this namespace.
	 */
	public Set<DataObject> getDataObjects() {
		return dataObjects;
	}
	
	/**
	 * Adds a new data object to the namespace.
	 * 
	 * @param dataObject
	 *            the new data object
	 * 
	 * @return a reference to this object to allow chaining of method calls.
	 */
	public Namespace addDataObject(DataObject dataObject) {
		dataObject.setNamespace(this);	
		dataObjects.add(dataObject);
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
		Namespace other = (Namespace) obj;
		return Objects.equals(this.getName(), other.getName());
	}

	@Override
	public String toString() {
		return "Namespace [id=" + getId() + ", name=" + name + ", propertyGroup=" + propertyGroup
				+ ", properties=" + getProperties() + "]";
	}
}