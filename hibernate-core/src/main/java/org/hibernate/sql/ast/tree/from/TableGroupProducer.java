/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.from;

import org.hibernate.metamodel.mapping.ModelPartContainer;
import org.hibernate.sql.ast.spi.SqlAliasBaseManager;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Marker interface for anything which produces a TableGroup
 *
 * @author Steve Ebersole
 * @author Andrea Boriero
 */
public interface TableGroupProducer extends ModelPartContainer {
	/**
	 * Get the "stem" used as the base for generating SQL table aliases for table
	 * references that are part of the TableGroup being generated
	 * <p>
	 * Note that this is a metadata-ive value.  It is only ever used internal to
	 * the producer producing its TableGroup.
	 *
	 * @see SqlAliasBaseManager#createSqlAliasBase
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getSqlAliasStem();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean containsTableReference(String tableExpression) {
		return false;
	}
}
