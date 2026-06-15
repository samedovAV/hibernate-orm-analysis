/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.tool.schema.internal;

import org.hibernate.tool.schema.spi.SchemaFilter;
import org.hibernate.tool.schema.spi.SchemaFilterProvider;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Default implementation of the SchemaFilterProvider contract, which returns
 * {@link DefaultSchemaFilter} for all filters.
 */
public class DefaultSchemaFilterProvider implements SchemaFilterProvider {
	public static final DefaultSchemaFilterProvider INSTANCE = new DefaultSchemaFilterProvider();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SchemaFilter getCreateFilter() {
		return DefaultSchemaFilter.INSTANCE;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SchemaFilter getDropFilter() {
		return DefaultSchemaFilter.INSTANCE;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SchemaFilter getMigrateFilter() {
		return DefaultSchemaFilter.INSTANCE;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SchemaFilter getValidateFilter() {
		return DefaultSchemaFilter.INSTANCE;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SchemaFilter getTruncatorFilter() {
		return DefaultSchemaFilter.INSTANCE;
	}
}
