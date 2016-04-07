package com.ooluk.mdm.core.dataobject.data;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import com.ooluk.mdm.core.app.data.Label;
import com.ooluk.mdm.core.app.data.Tag;
import com.ooluk.mdm.core.attribute.data.Attribute;
import com.ooluk.mdm.core.base.data.DynamicProperties;
import com.ooluk.mdm.core.base.data.StandaloneEntity;
import com.ooluk.mdm.core.index.data.Index;

/**
 * A data object is a container for similar data. In the relational world a data object would be a
 * table. In a file system it could be a file. A data object belongs to a namespace. A data object
 * is uniquely identified by its name within a namespace.
 * 
 * @author Siddhesh Prabhu
 * @since 1.0
 *
 */
@Entity
@Table ( name = "mdm_data_object" )
@Indexed
@AttributeOverrides ( value = { 
		@AttributeOverride ( name = "id", column = @Column ( name = "obj_id") ),
		@AttributeOverride ( name = "properties", column = @Column ( name = "obj_properties") )  
}) 
public class DataObject extends StandaloneEntity<DataObject> {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn ( name = "nspace_id", nullable = false )
	private Namespace namespace;

	@Field
	@Column ( name = "obj_name" )
	private String name;

	@OneToMany ( fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true, mappedBy = "dataObject" )
	private Set<Attribute> attributes;

	@OneToMany ( fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true, mappedBy = "dataObject" )
	private Set<Index> indexes;

	@ManyToMany ( fetch = FetchType.LAZY )
	@JoinTable ( 
			name = "mdm_data_object_label", 
			joinColumns = @JoinColumn ( name = "obj_id" ), 
			inverseJoinColumns = @JoinColumn ( name = "lbl_id" ) 
		)
	private Set<Label> labels;

	@ManyToMany ( fetch = FetchType.LAZY )
	@JoinTable ( 
			name = "mdm_data_object_tag", 
			joinColumns = @JoinColumn ( name = "obj_id" ), 
			inverseJoinColumns = @JoinColumn ( name = "tag_id" ) 
		)
	private Set<Tag> tags;

	@OneToMany ( fetch = FetchType.LAZY, mappedBy = "key.entity1" )
	private Set<DataObjectSourceMapping> sources;

	@OneToMany ( fetch = FetchType.LAZY, mappedBy = "key.entity2" )
	private Set<DataObjectSourceMapping> targets;
	
	@OneToMany ( fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true, mappedBy = "dataObject" )
	private Set<DataObjectNote> notes;

	/**
	 * Constructs a data object
	 */
	public DataObject() {
		attributes = new HashSet<>();
		indexes = new HashSet<>();
		labels = new HashSet<>();
		tags = new HashSet<>();
		sources = new HashSet<>();
		targets = new HashSet<>();	
		notes = new HashSet<>();
	}

	/**
	 * Constructs a data object.
	 * 
	 * @param namespace
	 *            data object namespace
	 * @param name
	 *            data object name
	 */
	public DataObject(Namespace namespace, String name) {
		this();
		this.name = name;
		namespace.addDataObject(this);
	}

	/**
	 * Returns the data object namespace.
	 * 
	 * @return data object namespace
	 */
	public Namespace getNamespace() {
		return namespace;
	}

	/**
	 * Sets this data object's namespace
	 * 
	 * @param namespace
	 *            namespace
	 *            
	 * @return a reference to this object to allow chaining of method calls.
	 */
	public DataObject setNamespace(Namespace namespace) {
		this.namespace = namespace;
		return this;
	}

	/**
	 * Returns the data object name.
	 * 
	 * @return data object name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the data object name.
	 * 
	 * @param name
	 *            data object name
	 *            
	 * @return a reference to this object to allow chaining of method calls.
	 */
	public DataObject setName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * Returns the data object's attributes.
	 * 
	 * @return list of attributes
	 */
	public Set<Attribute> getAttributes() {
		return attributes;
	}

	/**
	 * Adds an attribute to this data object.
	 * 
	 * @param attribute
	 *            attribute
	 *            
	 * @return a reference to this object to allow chaining of method calls.
	 */
	public DataObject addAttribute(Attribute attribute) {
		attribute.setDataObject(this);
		attributes.add(attribute);
		return this;
	}

	/**
	 * Reorders the specified property on the data object's attributes. Assume there is a property
	 * 'position' denoting the ordinal position of an attribute. If a data object has 10 attributes
	 * and you want to add an attribute in position 4, you would have to move all attributes from
	 * position 4 up by 1. You can do so calling this method with arguments ("position", 4, 2) and
	 * then adding the new attribute at position 4. It is vital that the property specified is of
	 * number type. You can also use this for properties that denote beginning or ending offsets.
	 * 
	 * @param property
	 *            name of the property
	 * @param start
	 *            the start for reordering
	 * @param step
	 *            the increment step size; specify a negative value for decrement.
	 */
	public void reorderAttributes(String property, int start, int step) {
		for (Attribute attr : attributes) {
			DynamicProperties props = attr.getProperties();
			if (props.containsProperty(property)) {
				Integer position = (Integer) props.getProperty(property);
				if (position >= start) {
					props.setProperty(property, position + step);
				}
			}
		}
	}

	/**
	 * Returns the indexes on this data object.
	 * 
	 * @return set of indexes.
	 */
	public Set<Index> getIndexes() {
		return indexes;
	}

	/**
	 * Adds an index to the data object.
	 * 
	 * @param idx
	 *            index to add
	 * 
	 * @return reference to this object to allow chaining of method calls.
	 */
	public DataObject addIndex(Index idx) {
		idx.setDataObject(this);
		indexes.add(idx);
		return this;
	}

	/**
	 * Returns the labels attached to this data object.
	 * 
	 * @return set of labels
	 */
	public Set<Label> getLabels() {
		return labels;
	}

	/**
	 * Attaches a label to the data object.
	 * 
	 * @param label
	 *            label to attach
	 * 
	 * @return reference to this object to allow chaining of method calls.
	 */
	public DataObject attachLabel(Label label) {
		labels.add(label);
		return this;
	}

	/**
	 * Detaches a label from a data object.
	 * 
	 * @param label
	 *            label to detach
	 */
	public void detachLabel(Label label) {
		Iterator<Label> itr = labels.iterator();
		while (itr.hasNext()) {
			Label l = itr.next();
			if (Objects.equals(l.getId(), label.getId())) {
				itr.remove();
				break;
			}
		}
	}

	/**
	 * Returns the tags attached to this data object
	 * 
	 * @return set of tags
	 */
	public Set<Tag> getTags() {
		return tags;
	}

	/**
	 * Attaches a tag to the data object.
	 * 
	 * @param tag
	 *            tag to attach
	 * 
	 * @return reference to this object to allow chaining of method calls.
	 */
	public DataObject attachTag(Tag tag) {
		tags.add(tag);
		return this;
	}

	/**
	 * Detaches a tag from a data object.
	 * 
	 * @param tag
	 *            tag to detach
	 */
	public void detachTag(Tag tag) {

		Iterator<Tag> itr = tags.iterator();
		while (itr.hasNext()) {
			Tag t = itr.next();
			if (Objects.equals(t.getId(), tag.getId())) {
				itr.remove();
				break;
			}
		}
	}

	/**
	 * Returns the data object's sources.
	 * 
	 * @return a mapping of source data objects and the corresponding mapping rules.
	 */
	public Set<DataObjectSourceMapping> getSources() {
		return sources;
	}

	/**
	 * Returns the target data objects for this data object. 
	 * 
	 * @return a mapping of target data objects and the corresponding mapping rules.
	 */
	public Set<DataObjectSourceMapping> getTargets() {
		return targets;
	}

	/**
	 * Returns the data object's notes.
	 * 
	 * @return set of notes.
	 */
	public Set<DataObjectNote> getNotes() {
		return notes;
	}

	/**
	 * Adds a note to this data object.
	 * 
	 * @param note
	 *            data object note
	 * 
	 * @return reference to this object to allow chaining of method calls.
	 */
	public DataObject addNote(DataObjectNote note) {
		note.setDataObject(this);
		notes.add(note);
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((namespace == null) ? 0 : namespace.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		DataObject other = (DataObject) obj;
		return Objects.equals(this.getNamespace(), other.getNamespace())
				&& Objects.equals(this.getName(), other.getName());
	}

	@Override
	public String toString() {
		return "DataObject [id=" + getId() + ", namespace=" + namespace + ", name=" + name
				+ ", properties=" + getProperties() + "]";
	}
}