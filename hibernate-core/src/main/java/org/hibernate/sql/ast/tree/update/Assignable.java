/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.update;

import java.util.List;
import java.util.function.Consumer;

import org.hibernate.sql.ast.tree.expression.ColumnReference;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface Assignable {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<ColumnReference> getColumnReferences();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void visitColumnReferences(Consumer<ColumnReference> columnReferenceConsumer) {
		getColumnReferences().forEach( columnReferenceConsumer );
	}

}
