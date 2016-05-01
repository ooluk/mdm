package com.ooluk.mdm.data.meta.app;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.ooluk.mdm.data.meta.StandaloneEntity;

/**
 * Represents a user of the software. A user is uniquely identified by a username.
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
@Entity
@Table (name = "mdm_user")
@AttributeOverrides ( value = { 
		@AttributeOverride ( name = "id", column = @Column ( name = "user_id") ),
		@AttributeOverride ( name = "properties", column = @Column ( name = "user_properties") )  
}) 
public class User extends StandaloneEntity<User> {

	private static final long serialVersionUID = 1L;

	@Column ( name="user_login_username" )
	private String userName;
	
	@ManyToMany ( fetch = FetchType.LAZY )
	@JoinTable (name = "mdm_user_role", 
		joinColumns = { @JoinColumn ( name = "user_id") },
		inverseJoinColumns = { @JoinColumn ( name = "role_id" ) })
	private Set<Role> roles;
	
	/**
	 * Constructs a new user
	 */
	public User() {
		roles = new HashSet<>();
	}
	
	/**
	 * Constructs a new user with the specified username.
	 * 
	 * @param userName
	 *            user name
	 */
	public User(String userName) {
		this();
		this.userName = userName;
	}
	
	/**
	 * Returns the username
	 * 
	 * @return username
	 */
	public String getUserName() {
		return userName;
	}
	
	/**
	 * Sets the username for this user
	 * 
	 * @param userName
	 *            username
	 *            
	 * @return a reference to this object to allow chaining of method calls.
	 */
	public User setUserName(String userName) {
		this.userName = userName;
		return this;
	}

	/**
	 * Returns this user's roles.
	 * 
	 * @return set of roles.
	 */
	public Set<Role> getRoles() {
		return roles;
	}	
	
	/**
	 * Adds a new role to the user.
	 * 
	 * @param role
	 *            role to add
	 *            
	 * @return reference to this object to allow chaining of method calls.
	 */
	public User addRole(Role role) {
		roles.add(role);
		return this;
	}
	
	/**
	 * Removes the specified role from the user.
	 * 
	 * @param role
	 *            role to remove
	 */
	public void removeRole(Role role) {
		Iterator<Role> itr = roles.iterator();
		while (itr.hasNext()) {
			Role r = itr.next();
			if (Objects.equals(r.getId(), role.getId())) {
				itr.remove();
				break;
			}
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getUserName() == null) ? 0 : getUserName().hashCode());
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
		User other = (User) obj;
		return Objects.equals(getUserName(), other.getUserName());
	}

	@Override
	public String toString() {
		return "User [id=" + getId() + ", userName=" + userName + ", properties=" + getProperties()
				+ ", roles=" + roles + "]";
	}
}