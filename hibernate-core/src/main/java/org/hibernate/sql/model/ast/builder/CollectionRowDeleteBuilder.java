/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.model.ast.builder;

import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.jdbc.Expectation;
import org.hibernate.persister.collection.mutation.CollectionTableMapping;
import org.hibernate.sql.model.MutationTarget;
import org.hibernate.sql.model.ast.MutatingTableReference;
import org.hibernate.sql.model.ast.TableDelete;
import org.hibernate.sql.model.internal.TableDeleteCustomSql;
import org.hibernate.sql.model.internal.TableDeleteStandard;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Custom table delete builder for many-to-many collection join tables that handles row deletes
 *
 * @author Marco Belladelli
 */
public class CollectionRowDeleteBuilder extends TableDeleteBuilderStandard {
	public CollectionRowDeleteBuilder(
			MutationTarget<?,?> mutationTarget,
			MutatingTableReference tableReference,
			SessionFactoryImplementor sessionFactory,
			String whereFragment) {
		super( mutationTarget, tableReference, sessionFactory, whereFragment );
		assert tableReference.getTableMapping() instanceof CollectionTableMapping;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public TableDelete buildMutation() {
		final CollectionTableMapping tableMapping = (CollectionTableMapping) getMutatingTable().getTableMapping();
		if ( tableMapping.getDeleteRowDetails().getCustomSql() != null ) {
			return new TableDeleteCustomSql(
					getMutatingTable(),
					getMutationTarget(),
					getSqlComment(),
					getKeyRestrictionBindings(),
					getOptimisticLockBindings(),
					getParameters()
			) {
				@Override
				@Prove(complexity = Complexity.O_N, n = "", count = {})
				public String getCustomSql() {
					return tableMapping.getDeleteRowDetails().getCustomSql();
				}

				@Override
				@Prove(complexity = Complexity.O_N, n = "", count = {})
				public boolean isCallable() {
					return tableMapping.getDeleteRowDetails().isCallable();
				}

				@Override
				@Prove(complexity = Complexity.O_N, n = "", count = {})
				public Expectation getExpectation() {
					return tableMapping.getDeleteRowDetails().getExpectation();
				}
			};
		}
		return new TableDeleteStandard(
				getMutatingTable(),
				getMutationTarget(),
				getSqlComment(),
				getKeyRestrictionBindings(),
				getOptimisticLockBindings(),
				getParameters(),
				getWhereFragment()
		) {
			@Override
			@Prove(complexity = Complexity.O_N, n = "", count = {})
			public Expectation getExpectation() {
				return tableMapping.getDeleteRowDetails().getExpectation();
			}
		};
	}
}
