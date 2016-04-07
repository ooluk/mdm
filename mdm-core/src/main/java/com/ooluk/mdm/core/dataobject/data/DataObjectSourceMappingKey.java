package com.ooluk.mdm.core.dataobject.data;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;

import com.ooluk.mdm.core.base.data.RelationshipEntityKey;

/**
 * Composite key for data object target-source relationship.
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
@Embeddable
@AssociationOverrides ( {
	@AssociationOverride(name = "entity1", joinColumns = @JoinColumn ( name = "obj_id")),
	@AssociationOverride(name = "entity2", joinColumns = @JoinColumn ( name = "source_obj_id"))
})
public class DataObjectSourceMappingKey extends RelationshipEntityKey<DataObject, DataObject> {

	private static final long serialVersionUID = 1L;
	
	public DataObjectSourceMappingKey() {
		super();
	}
	
	/**
	 * Constructs a data object to data object target-source mapping key.
	 * 
	 * @param target
	 *            target data object
	 * @param source
	 *            source data object
	 */
	public DataObjectSourceMappingKey(DataObject target, DataObject source) {
		super(target, source);
	}

	/**
	 * Returns the target data object.
	 * 
	 * @return target data object
	 */
	public DataObject getTargetDataObject() {
		return super.getEntity1();
	}

	/**
	 * Sets the target data object
	 * 
	 * @param targetDataObject
	 *            target data object
	 *            
	 * @return a reference to this object to allow chaining of methods            
	 */	
	public void setTargetDataObject(DataObject targetDataObject) {
		super.setEntity1(targetDataObject);
	}

	/**
	 * Returns the source data object.
	 * 
	 * @return source data object
	 */
	public DataObject getSourceDataObject() {
		return super.getEntity2();
	}

	/**
	 * Sets the source data object
	 * 
	 * @param sourceDataObject
	 *            source data object
	 *            
	 * @return a reference to this object to allow chaining of methods            
	 */	
	public void setSourceDataObject(DataObject sourceDataObject) {
		super.setEntity2(sourceDataObject);
	}
}