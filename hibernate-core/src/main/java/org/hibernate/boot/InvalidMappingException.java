/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot;

import org.hibernate.boot.jaxb.Origin;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Indicates a problem parsing the mapping document at a given {@link Origin}.
 *
 * @author Brett Meyer
 */
public class InvalidMappingException extends org.hibernate.InvalidMappingException {
	private final Origin origin;

	public InvalidMappingException(Origin origin) {
		super(
				String.format( "Could not parse mapping document: %s (%s)", origin.getName(), origin.getType() ),
				origin
		);
		this.origin = origin;
	}

	public InvalidMappingException(Origin origin, Throwable e) {
		super(
				String.format( "Could not parse mapping document: %s (%s)", origin.getName(), origin.getType() ),
				origin.getType().getLegacyTypeText(),
				origin.getName(),
				e
		);
		this.origin = origin;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Origin getOrigin() {
		return origin;
	}
}
