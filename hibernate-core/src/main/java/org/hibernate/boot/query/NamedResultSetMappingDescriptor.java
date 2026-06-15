/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.query;

import org.hibernate.query.internal.ResultSetMappingResolutionContext;
import org.hibernate.query.named.NamedObjectRepository;
import org.hibernate.query.named.NamedResultSetMappingMemento;
import org.hibernate.query.spi.QueryEngine;

import jakarta.annotation.Nullable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Models the "boot view" of a ResultSet mapping used in the mapping
 * of native and procedure queries.
 *
 * Ultimately used to generate a NamedResultSetMappingMemento that is
 * stored in the {@link NamedObjectRepository}
 * for availability at runtime
 *
 * @author Steve Ebersole
 */
public interface NamedResultSetMappingDescriptor {
	/**
	 * The name under which the result-set-mapping is to be registered
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getRegistrationName();

	/**
	 * The location at which the defining result set mapping annotation occurs,
	 * usually a class or package name. Null for result set mappings declared
	 * in XML or otherwise not associated with a static metamodel class.
	 */
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default String getLocation() {
		return null;
	}

	/**
	 * Create a representation of the described ResultSet mapping for the purpose of
	 * being stored in Hibernate's {@link NamedObjectRepository}
	 *
	 * @see QueryEngine#getNamedObjectRepository()
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NamedResultSetMappingMemento resolve(ResultSetMappingResolutionContext resolutionContext);
}
