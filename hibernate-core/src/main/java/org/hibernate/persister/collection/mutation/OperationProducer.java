/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.persister.collection.mutation;

import org.hibernate.sql.model.ast.MutatingTableReference;
import org.hibernate.sql.model.jdbc.JdbcMutationOperation;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Callback for producing a {@link JdbcMutationOperation} given
 * a collection-table reference
 *
 * @see RowMutationOperations
 * @see UpdateRowsCoordinator
 * @see RowMutationOperations
 *
 * @author Steve Ebersole
 */
@FunctionalInterface
public interface OperationProducer {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JdbcMutationOperation createOperation(MutatingTableReference tableReference);
}
