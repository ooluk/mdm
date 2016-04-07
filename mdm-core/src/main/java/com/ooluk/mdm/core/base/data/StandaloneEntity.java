package com.ooluk.mdm.core.base.data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;

import com.ooluk.mdm.hibernate.DynamicPropertiesUserType;
import com.ooluk.mdm.hibernate.DynamicPropertyFieldBridge;

/**
 * A standalone entity has its own ID as its primary key. 
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
@MappedSuperclass
@TypeDefs ( { @TypeDef ( name = "jsonb", typeClass = DynamicPropertiesUserType.class ) } )
public abstract class StandaloneEntity<T extends StandaloneEntity<T>> implements MetaObject {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue ( strategy = GenerationType.IDENTITY )
	@DocumentId
	private Long id;

	@Type ( type = "jsonb" )	
	@Field(name="", bridge = @FieldBridge( impl = DynamicPropertyFieldBridge.class))
	private DynamicProperties properties;
	
	/**
	 * Constructs a StandaloneEntity
	 */
	public StandaloneEntity() {
		properties = new DynamicProperties();
	}

	/**
	 * Returns the ID
	 * 
	 * @return ID
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Returns the dynamic properties
	 * 
	 * @return dynamic properties
	 */
	public DynamicProperties getProperties() {
		return properties;
	}

	/**
	 * Sets the dynamic properties
	 * 
	 * @param properties
	 *            dynamic properties to set
	 *            
	 * @return a reference to this object to allow chaining of method calls
	 */
	@SuppressWarnings ( "unchecked" )
	public T setProperties(DynamicProperties properties) {
		this.properties = properties;
		return (T) this;
	}
}