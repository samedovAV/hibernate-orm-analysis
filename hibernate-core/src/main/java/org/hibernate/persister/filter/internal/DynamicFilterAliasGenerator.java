/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.persister.filter.internal;

import org.hibernate.persister.filter.FilterAliasGenerator;

import static org.hibernate.persister.entity.AbstractEntityPersister.generateTableAlias;
import static org.hibernate.persister.entity.AbstractEntityPersister.getTableId;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Rob Worsnop
 */
public class DynamicFilterAliasGenerator implements FilterAliasGenerator {
	private final String[] tables;
	private final String rootAlias;

	public DynamicFilterAliasGenerator(String[] tables, String rootAlias) {
		this.tables = tables;
		this.rootAlias = rootAlias;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getAlias(String table) {
		return table == null
				? rootAlias
				: generateTableAlias( rootAlias,
						getTableId( table, tables ) );
	}
}
