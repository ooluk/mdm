package com.ooluk.mdm.core.index.data;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.search.annotations.Indexed;

import com.ooluk.mdm.core.base.data.StandaloneEntity;
import com.ooluk.mdm.core.dataobject.data.DataObject;

/**
 * Represents an index on a data object. An index is uniquely identified by its name within a data
 * object. An index maintains reference to the attributes on which the index is defined.
 * 
 * @author Siddhesh Prabhu
 * @since 1.0
 *
 */
@Entity
@Table (name = "mdm_index")
@Indexed
@AttributeOverrides ( value = { 
		@AttributeOverride ( name = "id", column = @Column ( name = "idx_id") ),
		@AttributeOverride ( name = "properties", column = @Column ( name = "idx_properties") )  
}) 
public class Index extends StandaloneEntity<Index> {

	private static final long serialVersionUID = 1L;

	@Column ( name = "idx_name")
	private String name;
		
	@ManyToOne
	@JoinColumn ( name = "obj_id" )
	private DataObject dataObject;
		
	@OneToMany ( mappedBy = "key.entity1" )
	private Set<IndexAttributeMapping> attributes;
	
	/**
	 * Constructs a new label
	 */
	public Index() {
		attributes = new HashSet<>();
	}
	
	/**
	 * Constructs a new label with the specified type and name.
	 * 
	 * @param type
	 *            label type
	 * @param name
	 *            label name
	 */
	public Index(String name) {
		this();
		this.name = name;
	}
	
	/**
	 * Returns the index name
	 * 
	 * @return name of the index
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name of the index
	 * 
	 * @param name
	 *            index name
	 * 
	 * @return reference to this object to allow chaining of method calls
	 */
	public Index setName(String name) {
		this.name = name;
		return this;
	}
	
	/**
	 * Returns the data object for this index.
	 * 
	 * @return data object on which this index is defined
	 */
	public DataObject getDataObject() {
		return dataObject;
	}

	/**
	 * Sets the data object attribute. You should not set the data object on an index but instead
	 * add an index to a data object.
	 * 
	 * @param parentAttribute
	 *            parent attribute
	 * 
	 * @return reference to this object to allow chaining of method calls.
	 */
	public Index setDataObject(DataObject dataObject) {
		this.dataObject = dataObject;
		return this;
	}

	/**
	 * Returns the attributes used in this index.  
	 * 
	 * @return a mapping of attributes and properties of the index-attribute relationship
	 */
	public Set<IndexAttributeMapping> getAttributes() {
		return attributes;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getDataObject() == null) ? 0 : getDataObject().hashCode());
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
		Index other = (Index) obj;
		return Objects.equals(getDataObject(), other.getDataObject()) &&
				Objects.equals(getName(), other.getName());
	}

	@Override
	public String toString() {
		return "Index [id=" + getId() + ", name=" + name + ", properties="
				+ getProperties() + "]";
	}
}