/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query;

import org.hibernate.MappingException;

import jakarta.persistence.NamedNativeQuery;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Indicates a request for named ResultSet mapping which could not be found
 *
 * @see NamedNativeQuery#resultSetMapping()
 * @see org.hibernate.Session#createNativeQuery(String, String)
 * @see org.hibernate.Session#createNativeQuery(String, String, Class)
 *
 * @author Steve Ebersole
 */
public class UnknownSqlResultSetMappingException extends MappingException {
	private final String unknownSqlResultSetMappingName;

	public UnknownSqlResultSetMappingException(String unknownSqlResultSetMappingName) {
		super( "The given SqlResultSetMapping name [" + unknownSqlResultSetMappingName + "] is unknown" );
		this.unknownSqlResultSetMappingName = unknownSqlResultSetMappingName;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getUnknownSqlResultSetMappingName() {
		return unknownSqlResultSetMappingName;
	}
}
