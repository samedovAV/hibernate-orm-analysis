/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.dialect.type;

import org.hibernate.metamodel.mapping.EmbeddableMappingType;
import org.hibernate.metamodel.spi.RuntimeModelCreationContext;
import org.hibernate.type.descriptor.jdbc.AggregateJdbcType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Christian Beikov
 */
public class PostgreSQLJsonPGObjectJsonType extends AbstractPostgreSQLJsonPGObjectType {
	public PostgreSQLJsonPGObjectJsonType() {
		this( null, false );
	}
	private PostgreSQLJsonPGObjectJsonType(EmbeddableMappingType embeddableMappingType, boolean jsonb) {
		super( embeddableMappingType, jsonb );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public AggregateJdbcType resolveAggregateJdbcType(
			EmbeddableMappingType mappingType,
			String sqlType,
			RuntimeModelCreationContext creationContext) {
		return new PostgreSQLJsonPGObjectJsonType( mappingType, false );
	}
}
