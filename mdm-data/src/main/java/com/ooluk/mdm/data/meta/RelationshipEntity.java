package com.ooluk.mdm.data.meta;

import java.util.Objects;

import javax.persistence.EmbeddedId;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Indexed;

import com.ooluk.mdm.data.hibernate.DynamicPropertiesUserType;

/**
 * A relationship entity is an entity for a many-to-many relationship. Such entities do not have
 * their own key(ID). Their ID is formed from the IDs of the related standalone entities.
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 * @param <E1> type for first standalone entity 
 * @param <E2> type for second standalone entity 
 * @param <K> type for the key
 */
@MappedSuperclass
@Indexed
@TypeDefs ( { @TypeDef ( name = "jsonb", typeClass = DynamicPropertiesUserType.class ) } )
public abstract class RelationshipEntity<
	E1 extends StandaloneEntity<E1>, 
	E2 extends StandaloneEntity<E2>, 
	K extends RelationshipEntityKey<E1, E2>> implements MetaObject {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	@DocumentId
	private K key;

	@Type ( type = "jsonb" )
	private DynamicProperties properties;
	
	/**
	 * Constructs a new relationship entity
	 */
	public RelationshipEntity(){		
	}
	
	/**
	 * Constructs a new relationship entity with the specified key
	 * 
	 * @param key
	 *            relationship key
	 */
	public RelationshipEntity(K key) {
		this.key = key;
	}
	
	/**
	 * Returns the relationship entity key
	 *  
	 * @return relationship entity key
	 */
	public K getKey() {
		return key;
	}

	/**
	 * Returns the dynamic properties for the relationship entity.
	 * 
	 * @return dynamic properties
	 */
	public DynamicProperties getProperties() {
		return properties;
	}

	/**
	 * Sets the dynamic properties for the relationship entity.
	 * 
	 * @param properties
	 *            dynamic properties
	 *            
	 * @return reference to this object to allow chaining of method calls.
	 */
	public RelationshipEntity<E1, E2, K> setProperties(DynamicProperties properties) {
		this.properties = properties;
		return this;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getKey() == null) ? 0 : getKey().hashCode());
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
		@SuppressWarnings ( "rawtypes" )
		RelationshipEntity other = (RelationshipEntity) obj;
		return Objects.equals(this.getKey(), other.getKey());
	}
}