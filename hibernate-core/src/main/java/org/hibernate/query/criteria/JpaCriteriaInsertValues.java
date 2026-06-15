/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.criteria;

import java.util.List;

import jakarta.annotation.Nonnull;
import org.hibernate.Incubating;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A representation of SqmInsertValuesStatement at the
 * {@link org.hibernate.query.criteria} level, even though JPA does
 * not define support for insert-values criteria.
 *
 * @see org.hibernate.query.sqm.tree.insert.SqmInsertValuesStatement
 *
 * @apiNote Incubating mainly for 2 purposes:<ul>
 *     <li>
 *         to decide how to handle the typing for the "selection part".  Should it
 *         be {@code <T>} or {@code <X>}.  For the time being we expose it as
 *         {@code <T>} because that is what was done (without intention) originally,
 *         and it is the easiest form to validate
 *     </li>
 *     <li>
 *         Would be better to expose non-SQM contracts here
 *     </li>
 * </ul>
 *
 * @author Gavin King
 */
@Incubating
public interface JpaCriteriaInsertValues<T> extends JpaCriteriaInsert<T> {

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaCriteriaInsertValues<T> values(@Nonnull JpaValues... values);

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaCriteriaInsertValues<T> values(@Nonnull List<? extends JpaValues> values);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaCriteriaInsertValues<T> onConflict(@Nonnull JpaConflictClause<T> conflictClause);

}
