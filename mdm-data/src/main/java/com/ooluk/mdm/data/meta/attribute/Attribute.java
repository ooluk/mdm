package com.ooluk.mdm.data.meta.attribute;

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

import com.ooluk.mdm.data.meta.StandaloneEntity;
import com.ooluk.mdm.data.meta.app.Label;
import com.ooluk.mdm.data.meta.app.Tag;
import com.ooluk.mdm.data.meta.dataobject.DataObject;
import com.ooluk.mdm.data.meta.index.IndexAttributeMapping;

/**
 * An attribute could be a column of a database table or a field of a file. An attribute is uniquely
 * identified by its name within a data object.
 * 
 * @author Siddhesh Prabhu
 * @since 1.0
 *
 */
@Entity
@Table ( name = "mdm_attribute" )
@Indexed
@AttributeOverrides ( value = {
		@AttributeOverride ( name = "id", column = @Column ( name = "attr_id" ) ),
		@AttributeOverride ( name = "properties", column = @Column ( name = "attr_properties" ) ) } )
public class Attribute extends StandaloneEntity<Attribute> {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn ( name = "obj_id" )
	private DataObject dataObject;

	@Field
	@Column ( name = "attr_name" )
	private String name;

	@ManyToOne
	@JoinColumn ( name = "attr_parent_id" )
	private Attribute parentAttribute;

	@OneToMany ( fetch = FetchType.LAZY, mappedBy = "parentAttribute" )
	private Set<Attribute> childAttributes;

	@OneToMany ( fetch = FetchType.LAZY, mappedBy = "key.entity2" )
	private Set<IndexAttributeMapping> indexes;

	@ManyToMany ( fetch = FetchType.LAZY )
	@JoinTable ( name = "mdm_attribute_label", 
		joinColumns = @JoinColumn ( name = "attr_id" ), 
		inverseJoinColumns = @JoinColumn ( name = "lbl_id" ) )
	private Set<Label> labels;

	@ManyToMany ( fetch = FetchType.LAZY )
	@JoinTable ( name = "mdm_attribute_tag", 
		joinColumns = @JoinColumn ( name = "attr_id" ), 
		inverseJoinColumns = @JoinColumn ( name = "tag_id" ) )
	private Set<Tag> tags;

	@OneToMany ( fetch = FetchType.LAZY, mappedBy = "key.entity1" )
	private Set<AttributeSourceMapping> sources;

	@OneToMany ( fetch = FetchType.LAZY, mappedBy = "key.entity2" )
	private Set<AttributeSourceMapping> targets;
	
	@OneToMany ( fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true, mappedBy = "attribute" )
	private Set<AttributeNote> notes;

	/**
	 * Constructs an attribute
	 */
	public Attribute() {
		indexes = new HashSet<>();
		labels = new HashSet<>();
		tags = new HashSet<>();
		sources = new HashSet<>();
		targets = new HashSet<>();
		notes = new HashSet<>();
	}

	/**
	 * Constructs an attribute with the specified name within the specified data object.
	 * 
	 * @param dataObject
	 *            containing data object
	 * @param name
	 *            attribute name
	 */
	public Attribute(DataObject dataObject, String name) {
		this();
		this.name = name;
		dataObject.addAttribute(this);
	}

	/**
	 * Returns the data object containing this attribute
	 * 
	 * @return data object
	 */
	public DataObject getDataObject() {
		return dataObject;
	}

	/**
	 * Sets the data object for an attribute. You should not set the data object on an attribute
	 * directly. An attribute should be added to a data object.
	 * 
	 * @param dataObject
	 *            data object
	 * 
	 * @return a reference to this object to allow chaining of method calls.
	 */
	public Attribute setDataObject(DataObject dataObject) {
		this.dataObject = dataObject;
		return this;
	}

	/**
	 * Returns the name of the attribute.
	 * 
	 * @return attribute name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the attribute name.
	 * 
	 * @param name
	 *            attribute name
	 * 
	 * @return a reference to this object to allow chaining of method calls.
	 */
	public Attribute setName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * Returns the parent attribute. In the relational world the parent attribute is PK for a FK.
	 * 
	 * @return parent attribute
	 */
	public Attribute getParentAttribute() {
		return parentAttribute;
	}

	/**
	 * Sets the parent attribute.
	 * 
	 * @param parentAttribute
	 *            parent attribute
	 * 
	 * @return reference to this object to allow chaining of method calls.
	 */
	public Attribute setParentAttribute(Attribute parentAttribute) {
		this.parentAttribute = parentAttribute;
		return this;
	}

	/**
	 * Returns the child attributes for this attribute.
	 * 
	 * @return set of attributes
	 */
	public Set<Attribute> getChildAttributes() {
		return childAttributes;
	}

	/**
	 * Returns the indexes using this attribute.
	 * 
	 * @return a mapping of indexes and properties of the attribute-index relationship
	 */
	public Set<IndexAttributeMapping> getIndexes() {
		return indexes;
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
	 * Attaches a label to the attribute.
	 * 
	 * @param label
	 *            label to attach
	 * 
	 * @return reference to this object to allow chaining of method calls.
	 */
	public Attribute attachLabel(Label label) {
		labels.add(label);
		return this;
	}

	/**
	 * Detaches a label from an attribute.
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
	 * Returns the tags attached to this attribute
	 * 
	 * @return set of tags
	 */
	public Set<Tag> getTags() {
		return tags;
	}

	/**
	 * Attaches a tag to the attribute.
	 * 
	 * @param tag
	 *            tag to attach
	 * 
	 * @return reference to this object to allow chaining of method calls.
	 */
	public Attribute attachTag(Tag tag) {
		tags.add(tag);
		return this;
	}

	/**
	 * Detaches a tag from an attribute.
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
	 * Returns the attribute's sources.
	 * 
	 * @return a mapping of source data objects and the corresponding mapping rules.
	 */
	public Set<AttributeSourceMapping> getSources() {
		return sources;
	}

	/**
	 * Returns the target attributes for this attribute.
	 * 
	 * @return a mapping of target data objects and the corresponding mapping rules.
	 */
	public Set<AttributeSourceMapping> getTargets() {
		return targets;
	}

	/**
	 * Returns the attribute's notes.
	 * 
	 * @return set of notes.
	 */
	public Set<AttributeNote> getNotes() {
		return notes;
	}

	/**
	 * Adds a note to this attribute.
	 * 
	 * @param note
	 *            attribute note
	 * 
	 * @return reference to this attribute to allow chaining of method calls.
	 */
	public Attribute addNote(AttributeNote note) {
		note.setAttribute(this);
		notes.add(note);
		return this;
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
		Attribute other = (Attribute) obj;
		return Objects.equals(getDataObject(), other.getDataObject())
				&& Objects.equals(getName(), other.getName());
	}

	@Override
	public String toString() {
		return "Attribute [id=" + getId() + ", dataObject=" + dataObject + ", name=" + name
				+ ", properties=" + getProperties() + "]";
	}
}