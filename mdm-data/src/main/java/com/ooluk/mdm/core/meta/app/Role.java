package com.ooluk.mdm.core.meta.app;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.ooluk.mdm.core.meta.StandaloneEntity;

/**
 * Represents a role in the metadata manager.
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
@Entity
@Table (name = "mdm_role")
@AttributeOverrides ( value = { 
		@AttributeOverride ( name = "id", column = @Column ( name = "role_id") ),
		@AttributeOverride ( name = "properties", column = @Column ( name = "role_properties") )  
}) 
public class Role extends StandaloneEntity<Role> {
	
	private static final long serialVersionUID = 1L;

	@Column ( name="role_name" )
	private String name;
	
	@ManyToMany ( fetch = FetchType.LAZY, mappedBy = "roles")
	private Set<User> users;
	
	/**
	 * Constructs a new role
	 */
	public Role() {
		users = new HashSet<>();
	}
	
	/**
	 * Constructs a new role with the specified name
	 * 
	 * @param name
	 *            role name
	 */
	public Role(String name) {
		this.name = name;
	}
	
	/**
	 * Returns the role name
	 * 
	 * @return role name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the role name.
	 * 
	 * @param type
	 *            role name
	 * 
	 * @return a reference to this object to allow chaining of method calls.
	 */
	public Role setName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * Returns the list of users with this role.
	 * 
	 * @return set of users.
	 */
	public Set<User> getUsers() {
		return users;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		Role other = (Role) obj;
		return Objects.equals(getName(), other.getName());
	}

	@Override
	public String toString() {
		return "Role [id=" + getId() + ", name=" + name + ", properties=" + getProperties() + "]";
	}
}