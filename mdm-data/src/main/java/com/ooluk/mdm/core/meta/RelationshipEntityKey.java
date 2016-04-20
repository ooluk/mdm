package com.ooluk.mdm.core.meta;

import java.util.Objects;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 * This is a superclass for all entities that represent a relationship between two standalone
 * entities. This class simply uses names entity1 and entity2. What they refer to is for each
 * subclass to decide. It is vital to use them in a consistent manner.
 * 
 * @author Siddhesh Prabhu
 * @since 1.0
 *
 * @param <E1>
 * @param <E2>
 */
@MappedSuperclass
public abstract class RelationshipEntityKey
	<E1 extends StandaloneEntity<E1>, E2 extends StandaloneEntity<E2>> implements MetaObject {

	private static final long serialVersionUID = 1L;

	@ManyToOne 
	private E1 entity1;

	@ManyToOne 
	private E2 entity2;
	
	/**
	 * Default constructor
	 */
	public RelationshipEntityKey() {
	}
	
	/**
	 * Constructs a key for a relationship entity.
	 * 
	 * @param entity1
	 *            first entity
	 * @param entity2
	 *            second entity
	 */
	public RelationshipEntityKey(E1 entity1, E2 entity2) {
		this.entity1 = entity1;
		this.entity2 = entity2;
	}

	/**
	 * Returns the first entity of the relationship
	 * 
	 * @return first entity
	 */
	public E1 getEntity1() {
		return entity1;
	}

	/**
	 * Sets the first entity of the relationship
	 * 
	 * @param entity1
	 *            first entity
	 */
	public void setEntity1(E1 entity1) {
		this.entity1 = entity1;
	}

	/**
	 * Returns the second entity of the relationship
	 * 
	 * @return second entity
	 */
	public E2 getEntity2() {
		return entity2;
	}

	/**
	 * Sets the second entity of the relationship
	 * 
	 * @param entity2
	 *            second entity
	 */
	public void setEntity2(E2 entity2) {
		this.entity2 = entity2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((entity1 == null) ? 0 : entity1.hashCode());
		result = prime * result + ((entity2 == null) ? 0 : entity2.hashCode());
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
		RelationshipEntityKey other = (RelationshipEntityKey) obj;
		return Objects.equals(this.entity1, other.entity1) &&
				Objects.equals(this.entity2, other.entity2);
	}
}