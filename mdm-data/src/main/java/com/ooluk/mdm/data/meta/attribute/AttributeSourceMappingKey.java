package com.ooluk.mdm.data.meta.attribute;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;

import com.ooluk.mdm.data.meta.RelationshipEntityKey;

/**
 * Composite key for attribute target-source relationship.
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
@Embeddable
@AssociationOverrides ( {
	@AssociationOverride(name = "entity1", joinColumns = @JoinColumn ( name = "attr_id")),
	@AssociationOverride(name = "entity2", joinColumns = @JoinColumn ( name = "source_attr_id"))
})
public class AttributeSourceMappingKey extends RelationshipEntityKey<Attribute, Attribute> {

	private static final long serialVersionUID = 1L;
	
	public AttributeSourceMappingKey() {
	}
	
	/**
	 * Constructs a attribute to attribute target-source mapping key.
	 * 
	 * @param target
	 *            target attribute
	 * @param source
	 *            source attribute
	 */
	public AttributeSourceMappingKey(Attribute target, Attribute source) {
		super(target, source);
	}

	/**
	 * Returns the target attribute
	 *  
	 * @return target attribute
	 */
	public Attribute getTargetAttribute() {
		return super.getEntity1();
	}

	/**
	 * Sets the target attribute
	 * 
	 * @param targetAttribute
	 *            target attribute
	 *            
	 * @return a reference to this object to allow chaining of methods            
	 */
	public AttributeSourceMappingKey setTargetAttribute(Attribute targetAttribute) {
		super.setEntity1(targetAttribute);
		return this;
	}

	/**
	 * Returns the source attribute.
	 * 
	 * @return source attribute
	 */
	public Attribute getSourceAttribute() {
		return super.getEntity2();
	}


	/**
	 * Sets the source attribute.
	 * 
	 * @param sourceAttribute
	 *            source attribute
	 *            
	 * @return a reference to this object to allow chaining of methods            
	 */
	public AttributeSourceMappingKey setSourceAttribute(Attribute sourceAttribute) {
		super.setEntity2(sourceAttribute);
		return this;
	}
}