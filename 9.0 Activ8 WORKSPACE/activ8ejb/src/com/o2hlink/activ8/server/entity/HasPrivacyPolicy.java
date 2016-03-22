package com.o2hlink.activ8.server.entity;

import java.util.List;

import com.o2hlink.activ8.common.entity.HasIdentifier;
import com.o2hlink.activ8.common.entity.PrivacyLevel;

/**
 * A policy for defining privacy of objects
 * @author Miguel Angel Hernandez
 * @see	com.o2hlink.activ8.client.entity.HasPrivacyPolicy
 **/
public interface HasPrivacyPolicy extends HasIdentifier {
	/**
	 * Set the privacy level of this particular object.
	 * @param	level The privacy level
	 **/
	public void setPrivacyLevel(PrivacyLevel level);
	/**
	 * Get the privacy level of this particular object.
	 * @return	The privacy level
	 **/
	public PrivacyLevel getPrivacyLevel();
	/**
	 * Get the default privacy level for this particular object. This privacy level should be assigned when creating the object if no privacy level is indicated.
	 **/
	public PrivacyLevel getDefaultPrivacyLevel();
	/**
	 * Get the owner of this object
	 * @return	The privacy owner
	 **/
	public User getOwner();
	/**
	 * If the privacy level is {@link PrivacyLevel#PRIVATE}, {@link PrivacyLevel#RESTRICTED} return the set of users that have access to this object. A privacy level of {@link PrivacyLevel#PUBLIC} will ignore the resulting value of this property.
	 **/
	public List<User> getAllowedUsers();
}
