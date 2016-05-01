package com.ooluk.mdm.data.meta.index;

import java.util.Objects;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;

import com.ooluk.mdm.data.meta.RelationshipEntityKey;
import com.ooluk.mdm.data.meta.attribute.Attribute;

/**
 * Composite key for index-attribute relationship. An index can be defined on several attributes.
 * 
 * @author Siddhesh Prabhu
 * @since 1.0
 *
 */
@Embeddable
@AssociationOverrides ( {
	@AssociationOverride(name = "entity1", joinColumns = @JoinColumn ( name = "idx_id")),
	@AssociationOverride(name = "entity2", joinColumns = @JoinColumn ( name = "attr_id"))
})
public class IndexAttributeMappingKey extends RelationshipEntityKey<Index, Attribute> {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs a new index-attribute mapping.
	 */
	public IndexAttributeMappingKey() {
		super();
	}
	
	/**
	 * Constructs a new index-attribute mapping with the specified key.
	 * 
	 * @param index
	 *            the index
	 * @param attribute
	 *            the attribute
	 */
	public IndexAttributeMappingKey(Index index, Attribute attribute) {
		super(index, attribute);
	}
	
	/**
	 * Returns the index.
	 * 
	 * @return index of the relationship
	 */
	public Index getIndex() {
		return super.getEntity1();
	}

	/**
	 * Sets the index.
	 * 
	 * @param index
	 *            index
	 *            
	 * @return a reference to this object to allow chaining of methods calls.
	 */
	public void setIndex(Index index) {
		super.setEntity1(index);
	}
	
	/**
	 * Returns the attribute.
	 * 
	 * @return attribute of the relationship
	 */
	public Attribute getAttribute() {
		return super.getEntity2();
	}

	/**
	 * Sets the attribute.
	 * 
	 * @param attribute
	 *            attribute in the index 
	 *            
	 * @return a reference to this object to allow chaining of methods calls.
	 */
	public void setAttribute(Attribute attribute) {
		super.setEntity2(attribute);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getAttribute() == null) ? 0 :  getAttribute().hashCode());
		result = prime * result + ((getIndex() == null) ? 0 : getIndex().hashCode());
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
		IndexAttributeMappingKey other = (IndexAttributeMappingKey) obj;
		return Objects.equals(this.getAttribute(), other.getAttribute()) &&
				Objects.equals(this.getIndex(), other.getIndex());
	}
}