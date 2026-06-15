/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.results.internal;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.query.results.spi.ResultSetMapping;
import org.hibernate.spi.NavigablePath;
import org.hibernate.sql.ast.spi.FromClauseAccess;
import org.hibernate.sql.ast.tree.from.TableGroup;

import jakarta.annotation.Nullable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * FromClauseAccess implementation used while building
 * {@linkplain ResultSetMapping} references.
 *
 * @author Steve Ebersole
 */
public class FromClauseAccessImpl implements FromClauseAccess {
	private Map<String, TableGroup> tableGroupBySqlAlias;
	private Map<NavigablePath, TableGroup> tableGroupByPath;

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public TableGroup findByAlias(String alias) {
		return tableGroupBySqlAlias == null ? null : tableGroupBySqlAlias.get( alias );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public @Nullable TableGroup findTableGroupByIdentificationVariable(String identificationVariable) {
		for ( var tableGroup : tableGroupByPath.values() ) {
			if ( tableGroup.findTableReference( identificationVariable ) != null ) {
				return tableGroup;
			}
		}
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public TableGroup findTableGroupOnCurrentFromClause(NavigablePath navigablePath) {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public TableGroup findTableGroup(NavigablePath navigablePath) {
		return tableGroupByPath == null ? null : tableGroupByPath.get( navigablePath );

	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void registerTableGroup(NavigablePath navigablePath, TableGroup tableGroup) {
		if ( tableGroupByPath == null ) {
			tableGroupByPath = new HashMap<>();
		}
		tableGroupByPath.put( navigablePath, tableGroup );

		final String groupAlias = tableGroup.getGroupAlias();
		if ( groupAlias != null ) {
			if ( tableGroupBySqlAlias == null ) {
				tableGroupBySqlAlias = new HashMap<>();
			}
			tableGroupBySqlAlias.put( groupAlias, tableGroup );
		}
	}
}
