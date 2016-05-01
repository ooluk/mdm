package com.ooluk.mdm.data.meta.app;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.search.annotations.Indexed;

import com.ooluk.mdm.data.meta.StandaloneEntity;

/**
 * Labels allow multi classification of data objects and attributes. A label is uniquely identified
 * by its type and name. Labels participate in a hierarchy. A label many have any number of children
 * and any number of parents.
 * 
 * @author Siddhesh Prabhu
 * @since 1.0
 *
 */
@Entity
@Indexed
@Table (name = "mdm_label")
@AttributeOverrides ( value = { 
		@AttributeOverride ( name = "id", column = @Column ( name = "lbl_id") ),
		@AttributeOverride ( name = "properties", column = @Column ( name = "lbl_properties") )  
}) 
public class Label extends StandaloneEntity<Label> {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn ( name = "lbl_type_id", nullable = false)
	private LabelType type;

	@Column ( name = "lbl_name")
	private String name;
		
	@ManyToMany (fetch = FetchType.LAZY )
	@JoinTable (
		name = "mdm_label_hierarchy", 
		joinColumns = { @JoinColumn ( name = "parent_lbl_id") },
		inverseJoinColumns = { @JoinColumn ( name = "child_lbl_id" ) }
	)
	private Set<Label> children;
		
	@ManyToMany (fetch = FetchType.LAZY, mappedBy = "children" )
	private Set<Label> parents;
	
	/**
	 * Constructs a new label
	 */
	public Label() {
		children = new HashSet<>();
		parents = new HashSet<>();
	}
	
	/**
	 * Constructs a new label with the specified type and name.
	 * 
	 * @param type
	 *            label type
	 * @param name
	 *            label name
	 */
	public Label(LabelType type, String name) {
		this();
		this.type = type;
		this.name = name;
	}

	/**
	 * Returns the label type.
	 * 
	 * @return label type
	 */
	public LabelType getType() {
		return type;
	}

	/**
	 * Sets the label type.
	 * 
	 * @param type
	 *            label type
	 * 
	 * @return a reference to this object to allow chaining of method calls.
	 */
	public Label setType(LabelType type) {
		this.type = type;
		return this;
	}
	
	/**
	 * Returns the label name.
	 * 
	 * @return label name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the label name
	 * 
	 * @param name
	 *            label name
	 * 
	 * @return a reference to this object to allow chaining of method calls.
	 */
	public Label setName(String name) {
		this.name = name;
		return this;
	}
	
	/**
	 * Returns the label's child labels.
	 * 
	 * @return set of child labels
	 */
	public Set<Label> getChildren() {
		return children;
	}

	/**
	 * Adds a parent-child relationship between this label and the specified label.
	 * 
	 * @param child
	 *            child label
	 * 
	 * @return reference to this object to allow chaining of method calls.
	 */
	public Label addChild(Label child) {
		children.add(child);
		return this;
	}
	
	/**
	 * Removes the parent-child relationship between this label and the specified label
	 * 
	 * @param child
	 *            child label
	 */
	public void removeChild(Label child) {

		Iterator<Label> itr = children.iterator();
		while (itr.hasNext()) {
			Label l = itr.next();
			if (Objects.equals(l.getId(), child.getId())) {
				itr.remove();
				break;
			}
		}
	}
	
	/**
	 * Returns the label's parent labels.
	 * 
	 * @return set of parent labels
	 */
	public Set<Label> getParents() {
		return parents;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
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
		Label other = (Label) obj;
		return Objects.equals(getType(), other.getType()) && 
				Objects.equals(getName(), other.getName());
	}

	@Override
	public String toString() {
		return "Label [id=" + getId() + ", type=" + type + ", name=" + name + ", properties="
				+ getProperties() + "]";
	}
}