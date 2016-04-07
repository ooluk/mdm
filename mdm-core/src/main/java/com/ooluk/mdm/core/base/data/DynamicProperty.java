package com.ooluk.mdm.core.base.data;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * A dynamic property allows dynamically configurable properties to be attached to meta objects.
 * This allows extending the metadata without further code changes.
 * 
 * <p>
 * Each dynamic property belongs to a {@link MetaObjectType}. Dynamic properties bear a many-to-many
 * relationship with {@link PropertyGroup}s.
 * 
 * <p>
 * The <code>key</code> attribute uniquely identifies a dynamic property within an instance. It is
 * also the key name used to store the value for the property in JSON. <b>This should not be changed
 * once set unless you have a way to update all the keys in the JSON column of the corresponding
 * table.</b>
 * 
 * @author Siddhesh Prabhu
 * @since 1.0
 *
 */
@Entity
@Table (name = "mdm_dynamic_property")
@AttributeOverrides ( value = { 
		@AttributeOverride ( name = "id", column = @Column ( name = "dymn_prop_id") ),
		@AttributeOverride ( name = "properties", column = @Column ( name = "dymn_properties") ) 
}) 
public class DynamicProperty extends StandaloneEntity<DynamicProperty> {

	private static final long serialVersionUID = 1L;
	
	@ManyToMany
	@JoinTable (name = "mdm_dynamic_property_group",
		joinColumns = @JoinColumn (name = "dymn_prop_id"),
		inverseJoinColumns = @JoinColumn (name = "pgroup_id")
	)	
	private Set<PropertyGroup> propertyGroups;
 
	@Enumerated(EnumType.STRING)
	@Column ( name = "dymn_prop_meta_type" )
	private MetaObjectType metaObjectType;
	
	/*
	 * The key used to map a value to this property. The key must not be changed once used otherwise
	 * there will be no way to access the properties mapped with the old key.
	 */
	@Column ( name = "dymn_prop_key" )
	private String key;
	
	@Enumerated(EnumType.STRING)
	@Column ( name = "dymn_prop_type" )
	private DynamicPropertyType type;

	/*
	 * The maximum length of the property value
	 */
	@Column ( name = "dymn_prop_size" )
	private int size;

	/*
	 * A list of acceptable values. This is only applicable for list types.
	 */
	@OneToMany ( cascade = CascadeType.REMOVE, orphanRemoval = true, mappedBy = "property" )
	private Set<ListValue> valueList;

	/*
	 * Indicates whether this property must be specified.
	 */
	@Column ( name = "dymn_prop_mandatory" )
	private boolean mandatory;

	/*
	 * The default value for the property
	 */
	@Column ( name = "dymn_prop_default" )
	private String defaultValue;

	/*
	 * A regular expression indicating the format for the property value
	 */
	@Column ( name = "dymn_prop_format" )
	private String format;
	
	/*
	 * A description for the property
	 */
	@Column ( name = "dymn_prop_description" )
	private String description;

	/*
	 * Visual properties for the dynamic property
	 */
	@AttributeOverrides({
        @AttributeOverride(name="caption",
                           	column=@Column(name="dymn_prop_ui_caption")),
        @AttributeOverride(name="message",
        					column=@Column(name="dymn_prop_ui_message")),
        @AttributeOverride(name="ordinalPosition",
        					column=@Column(name="dymn_prop_ui_position"))
    })
	@Embedded
	private VisualAttributes visualAttributes;
	
	/**
	 * Constructs a new empty dynamic property
	 */
	public DynamicProperty() {
		valueList = new HashSet<>();
		propertyGroups = new HashSet<>();
	}

	/**
	 * Returns the property groups for this dynamic property
	 * 
	 * @return set of property group
	 */
	public Set<PropertyGroup> getPropertyGroups() {
		return propertyGroups;
	}
	
	/**
	 * Sets the property groups.
	 * 
	 * @param groups
	 *            set of property groups
	 *            
	 * @return reference to this object to allow chaining of method calls.
	 */
	public DynamicProperty setPropertyGroup(Set<PropertyGroup> groups) {
		propertyGroups.clear();
		propertyGroups.addAll(groups);
		return this;
	}
	
	/**
	 * Attaches a property group to this dynamic property
	 * 
	 * @param group
	 *            property group
	 *            
	 * @return reference to this object to allow chaining of method calls.
	 */
	public DynamicProperty attachPropertyGroup(PropertyGroup group) {
		propertyGroups.add(group);
		return this;
	}

	/**
	 * Detaches a property group from a dynamic property.
	 * 
	 * @param group
	 *            property group to detach
	 */
	public void detachPropertyGroup(PropertyGroup group) {

		Iterator<PropertyGroup> itr = propertyGroups.iterator();
		while (itr.hasNext()) {
			PropertyGroup t = itr.next();
			if (Objects.equals(t.getId(), group.getId())) {
				itr.remove();
				break;
			}
		}
	}

	/**
	 * Returns the meta object type.
	 * 
	 * @return meta object type
	 */
	public MetaObjectType getMetaObjectType() {
		return metaObjectType;
	}
	
	/**
	 * Sets a meta object type.
	 * 
	 * @param metaObjectType
	 *            meta object type
	 *            
	 * @return reference to this object to allow chaining of method calls.
	 */
	public DynamicProperty setMetaObjectType(MetaObjectType metaObjectType) {
		this.metaObjectType = metaObjectType;
		return this;
	}

	/**
	 * Returns the property key.
	 * 
	 * @return property key
	 */
	public String getKey() {
		return key;
	}
	
	/**
	 * Sets the property key.
	 * 
	 * @param key
	 *            property key
	 *            
	 * @return reference to this object to allow chaining of method calls.
	 */
	public DynamicProperty setKey(String key) {
		this.key = key;
		return this;
	}

	/**
	 * Returns the property type.
	 * 
	 * @return property type
	 */
	public DynamicPropertyType getType() {
		return type;
	}
	
	/**
	 * Sets the property type.
	 * 
	 * @param type
	 *            property type
	 *            
	 * @return reference to this object to allow chaining of method calls.
	 */
	public DynamicProperty setType(DynamicPropertyType type) {
		this.type = type;
		return this;
	}

	/**
	 * Returns the property size.
	 * 
	 * @return property size
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * Sets the property size.
	 * 
	 * @param size
	 *            property size
	 *            
	 * @return reference to this object to allow chaining of method calls.
	 */
	public DynamicProperty setSize(int size) {
		this.size = size;
		return this;
	}

	/**
	 * Returns the list of values associated with the property.
	 * 
	 * @return a comma-separated list of values.
	 */
	public Set<ListValue> getValueList() {
		return valueList;
	}

	/**
	 * Sets a new value list. Only valid for list types.
	 * 
	 * @param valueList
	 *            list of values.
	 * 
	 * @return a reference to this object to allow chaining of methods
	 */
	public DynamicProperty setValueList(Set<ListValue> valueList) {
		if (!type.isListType() && !valueList.isEmpty()) {
			throw new UnsupportedOperationException("Value can only be specified for list types");
		}
		this.valueList.clear();
		this.valueList.addAll(valueList);
		return this;
	}

	/**
	 * Adds a value to a value list. Only valid for list types.
	 * 
	 * @param value
	 *            value to add
	 * 
	 * @return a reference to this object to allow chaining of methods
	 */
	public DynamicProperty addValue(ListValue value) {
		if (!type.isListType()) {
			throw new UnsupportedOperationException("Value can only be specified for list types");
		}
		value.setProperty(this);
		this.valueList.add(value);
		return this;
	}

	/**
	 * Returns whether the property is mandatory.
	 * 
	 * @return true if mandatory, false otherwise
	 */
	public boolean isMandatory() {
		return mandatory;
	}
	
	/**
	 * Sets the mandatory property.
	 * 
	 * @param mandatory
	 *            true if mandatory false otherwise
	 *            
	 * @return reference to this object to allow chaining of method calls.
	 */
	public DynamicProperty setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
		return this;
	}

	/**
	 * Returns the property value format
	 * 
	 * @return a REGEX string depicting the format for the property value
	 */
	public String getFormat() {
		return format;
	}
	
	/**
	 * Sets the property format.
	 * 
	 * @param format
	 *            a regex expression defining the format
	 *            
	 * @return reference to this object to allow chaining of method calls.
	 */
	public DynamicProperty setFormat(String format) {
		this.format = format;
		return this;
	}
	
	/**
	 * Returns the default value for the property
	 * 
	 * @return the default value for the property
	 */
	public String getDefaultValue() {
		return defaultValue;
	}
	
	/**
	 * Sets the property default value.
	 * 
	 * @param defaultValue
	 *            default value
	 *            
	 * @return reference to this object to allow chaining of method calls.
	 */
	public DynamicProperty setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
		return this;
	}

	/**
	 * Returns the property description
	 * 
	 * @return property description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Sets the property description.
	 * 
	 * @param description
	 *            default value
	 *            
	 * @return reference to this object to allow chaining of method calls.
	 */
	public DynamicProperty setDescription(String description) {
		this.description = description;
		return this;
	}

	/**
	 * Returns the visual attributes for the property.
	 * 
	 * @return visual attributes for the property
	 */
	public VisualAttributes getVisualAttributes() {
		return visualAttributes;
	}
	
	/**
	 * Sets the property's visual attributes.
	 * 
	 * @param visualAttributes
	 *            property's visual attributes
	 *            
	 * @return reference to this object to allow chaining of method calls.
	 */	
	public DynamicProperty setVisualAttributes(VisualAttributes visualAttributes) {
		this.visualAttributes = visualAttributes;
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
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
		DynamicProperty other = (DynamicProperty) obj;
		return Objects.equals(this.key, other.key);
	}

	@Override
	public String toString() {
		return "DynamicProperty [id=" + getId() + ", groups=" + propertyGroups + ", metaObjectType="
				+ metaObjectType + ", key=" + key + "]";
	}
}