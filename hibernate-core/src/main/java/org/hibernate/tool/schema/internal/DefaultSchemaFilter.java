/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.tool.schema.internal;

import org.hibernate.boot.model.relational.Namespace;
import org.hibernate.boot.model.relational.Sequence;
import org.hibernate.mapping.Table;
import org.hibernate.tool.schema.spi.SchemaFilter;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Default implementation of the SchemaFilter contract, which is to just include everything.
 */
public class DefaultSchemaFilter implements SchemaFilter {
	public static final DefaultSchemaFilter INSTANCE = new DefaultSchemaFilter();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean includeNamespace( Namespace namespace ) {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean includeTable( Table table ) {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean includeSequence( Sequence sequence ) {
		return true;
	}
}
