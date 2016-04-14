package com.ooluk.mdm.core.meta;

import java.util.Objects;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * A ListValue represents a single value of a list of values that apply to a {@link DynamicProperty}
 * of list type. If list values are directly stored in JSON it can be difficult to update their
 * values once stored. Storing surrogate IDs pointing to the actual values allows the values to be
 * easily modified.
 * 
 * @author Siddhesh Prabhu
 * @since 1.0
 *
 */
@Entity
@Table ( name = "mdm_list_value")
@AttributeOverrides ( value = { 
		@AttributeOverride ( name = "id", column = @Column ( name = "lv_id") ),
		@AttributeOverride ( name = "properties", column = @Column ( name = "lv_properties") )  
}) 
public class ListValue extends StandaloneEntity<ListValue> {

	private static final long serialVersionUID = 1L;

	@Column ( name = "lv_value" )
	private String value;
	
	@ManyToOne
	@JoinColumn ( name = "dymn_prop_id" )
	private DynamicProperty property;
	
	/**
	 * Creates a new blank value for a list 
	 */
	public ListValue() {
		value = "";
	}
	
	/**
	 * Creates a new value for a list 
	 * 
	 * @param value
	 *            the value
	 */
	public ListValue(String value) {
		this.value = value;
	}

	/**
	 * Returns the value.
	 * 
	 * @return value
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * Sets the value
	 * 
	 * @param value
	 *            value
	 *            
	 * @return a reference to this object to allow method chaining
	 */
	public ListValue setValue(String value) {
		this.value = value;
		return this;
	}

	/**
	 * Returns the dynamic property with which this list value is associated
	 * 
	 * @return dynamic property
	 */
	public DynamicProperty getProperty() {
		return property;
	}

	/**
	 * Sets the dynamic property for this list value
	 * 
	 * @param property
	 *            dynamic property
	 *            
	 * @return a reference to this object to allow method chaining
	 */
	public ListValue setProperty(DynamicProperty property) {
		this.property = property;
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getValue() == null) ? 0 : getValue().hashCode());
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
		ListValue other = (ListValue) obj;
		return Objects.equals(this.getValue(), other.getValue());
	}

	@Override
	public String toString() {
		return "ListValue [value=" + value + "]";
	}
}