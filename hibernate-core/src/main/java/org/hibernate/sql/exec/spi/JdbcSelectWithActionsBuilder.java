/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.exec.spi;

import org.hibernate.Incubating;
import org.hibernate.LockOptions;
import org.hibernate.dialect.lock.spi.LockTimeoutType;
import org.hibernate.dialect.lock.spi.LockingSupport;
import org.hibernate.sql.ast.spi.LockingClauseStrategy;
import org.hibernate.sql.ast.tree.select.QuerySpec;
import org.hibernate.sql.exec.internal.lock.LoadedValuesCollectorFactory;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/// Contract used while building a [JdbcSelect] which might potentially
/// include [pre-][PreAction] and/or [post-][PostAction] actions.
///
/// @author Steve Ebersole
/// @author Andrea Boriero
@Incubating
// Used by Hibernate Reactive
public interface JdbcSelectWithActionsBuilder {
	/// The primary selection.
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JdbcSelectWithActionsBuilder setPrimaryAction(JdbcSelect primaryAction);

	/// Collector of loaded values for post-processing.
	/// We need a factory of LoadedValuesCollectors, which will be instantiated for each query execution.
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JdbcSelectWithActionsBuilder setLoadedValuesCollectorFactory(LoadedValuesCollectorFactory loadedValuesCollectorFactory);

	///  Lock-timeout handling type.
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JdbcSelectWithActionsBuilder setLockTimeoutType(LockTimeoutType lockTimeoutType);

	/// Dialect's support for locking.
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JdbcSelectWithActionsBuilder setLockingSupport(LockingSupport lockingSupport);

	/// Requested lock options.
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JdbcSelectWithActionsBuilder setLockOptions(LockOptions lockOptions);

	/// QuerySpec (selection) which is the target of locking.
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JdbcSelectWithActionsBuilder setLockingTarget(QuerySpec lockingTarget);

	/// Access to locking details - used for paths to lock, mainly.
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JdbcSelectWithActionsBuilder setLockingClauseStrategy(LockingClauseStrategy lockingClauseStrategy);

	/// Whether follow-on locking should be used.
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JdbcSelectWithActionsBuilder setIsFollowOnLockStrategy(boolean isFollowOnLockStrategy);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JdbcSelectWithActionsBuilder appendPreAction(PreAction... actions);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JdbcSelectWithActionsBuilder prependPreAction(PreAction... actions);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JdbcSelectWithActionsBuilder appendPostAction(PostAction... actions);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JdbcSelectWithActionsBuilder prependPostAction(PostAction... actions);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JdbcSelectWithActionsBuilder addSecondaryActionPair(SecondaryAction action);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JdbcSelectWithActionsBuilder addSecondaryActionPair(PreAction preAction, PostAction postAction);

	/// Build the appropriate JdbcSelect.
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JdbcSelect build();
}
