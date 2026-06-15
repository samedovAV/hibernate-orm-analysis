/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Indicates a request against an unknown fetch profile name.
 *
 * @author Steve Ebersole
 *
 * @see org.hibernate.annotations.FetchProfile
 * @see Session#enableFetchProfile(String)
 */
public class UnknownProfileException extends HibernateException {
	private final String name;

	/**
	 * Constructs an {@code UnknownProfileException} for the given name.
	 *
	 * @param name The profile name that was unknown.
	 */
	public UnknownProfileException(String name) {
		super( "No fetch profile named '" + name + "'" );
		this.name = name;
	}

	/**
	 * The unknown fetch profile name.
	 *
	 * @return The unknown fetch profile name.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getName() {
		return name;
	}
}
