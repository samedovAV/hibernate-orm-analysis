/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot;

import org.hibernate.boot.jaxb.Origin;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Indicates a problem parsing a mapping document.
 *
 * @author Steve Ebersole
 */
public class MappingException extends org.hibernate.MappingException {
	private final Origin origin;

	public MappingException(String message, Origin origin) {
		super( message );
		this.origin = origin;
	}

	public MappingException(String message, Throwable root, Origin origin) {
		super( message, root );
		this.origin = origin;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getMessage() {
		final String message = super.getMessage();
		return origin != null
				? message + " [" + origin.getName() + "]"
				: message;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Origin getOrigin() {
		return origin;
	}
}
