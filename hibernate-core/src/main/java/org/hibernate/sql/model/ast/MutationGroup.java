/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.model.ast;

import java.util.function.BiConsumer;

import org.hibernate.sql.model.MutationOperation;
import org.hibernate.sql.model.MutationTarget;
import org.hibernate.sql.model.MutationType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Grouping of table mutations for the given target for
 * the given type of mutation
 *
 * @author Steve Ebersole
 */
public interface MutationGroup {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	MutationType getMutationType();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	MutationTarget<?,?> getMutationTarget();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	int getNumberOfTableMutations();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	TableMutation getSingleTableMutation();

	@Deprecated(forRemoval = true)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<O extends MutationOperation, M extends TableMutation<O>> M getTableMutation(String tableName);

	@Deprecated(forRemoval = true)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<O extends MutationOperation, M extends TableMutation<O>> void forEachTableMutation(BiConsumer<Integer, M> action);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	TableMutation getTableMutation(int i);
}
