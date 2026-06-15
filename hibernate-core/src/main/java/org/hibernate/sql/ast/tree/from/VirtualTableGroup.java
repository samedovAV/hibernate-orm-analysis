/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.from;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Marker interface for TableGroup impls that are virtual - should not be rendered
 * into the SQL.
 *
 * @author Steve Ebersole
 */
public interface VirtualTableGroup extends TableGroup {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	TableGroup getUnderlyingTableGroup();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean isVirtual() {
		return true;
	}
}
