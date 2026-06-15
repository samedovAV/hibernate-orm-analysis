/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.jdbc.env.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Enumerated values representing the level of support for catalog and schema.
 *
 * @author Steve Ebersole
 */
public enum NameQualifierSupport {
	/**
	 * Only catalog is supported
	 */
	CATALOG,
	/**
	 * Only schema is supported
	 */
	SCHEMA,
	/**
	 * Both catalog and schema are supported.
	 */
	BOTH,
	/**
	 * Neither catalog nor schema are supported.
	 */
	NONE;

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean supportsCatalogs() {
		return this == CATALOG || this == BOTH;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean supportsSchemas() {
		return this == SCHEMA || this == BOTH;
	}
}
