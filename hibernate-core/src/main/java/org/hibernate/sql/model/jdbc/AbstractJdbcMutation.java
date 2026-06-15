/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.model.jdbc;

import java.util.List;
import java.util.Set;

import org.hibernate.engine.jdbc.mutation.ParameterUsage;
import org.hibernate.engine.jdbc.mutation.internal.JdbcValueDescriptorImpl;
import org.hibernate.jdbc.Expectation;
import org.hibernate.sql.exec.spi.JdbcParameterBinder;
import org.hibernate.sql.model.MutationTarget;
import org.hibernate.sql.model.TableMapping;

import static org.hibernate.internal.util.collections.CollectionHelper.arrayList;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public abstract class AbstractJdbcMutation implements JdbcMutationOperation {
	private final TableMapping tableDetails;
	private final MutationTarget<?,?> mutationTarget;
	private final String sql;
	private final boolean callable;
	private final Expectation expectation;

	private final List<JdbcValueDescriptor> jdbcValueDescriptors;
	private final List<? extends JdbcParameterBinder> parameterBinders;

	public AbstractJdbcMutation(
			TableMapping tableDetails,
			MutationTarget<?,?> mutationTarget,
			String sql,
			boolean callable,
			Expectation expectation,
			List<? extends JdbcParameterBinder> parameterBinders) {
		this.tableDetails = tableDetails;
		this.mutationTarget = mutationTarget;
		this.sql = sql;
		this.callable = callable;
		this.expectation = expectation;
		this.parameterBinders = parameterBinders;

		this.jdbcValueDescriptors = arrayList( parameterBinders.size() );
		for ( int i = 0; i < parameterBinders.size(); i++ ) {
			final var parameterDescriptor =
					new JdbcValueDescriptorImpl( parameterBinders.get( i ),
							expectation.getNumberOfParametersUsed() + i + 1 );
			this.jdbcValueDescriptors.add( parameterDescriptor );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public TableMapping getTableDetails() {
		return tableDetails;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Set<String> getAffectedTableNames() {
		return Set.of( getTableDetails().getTableName() );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public MutationTarget<?,?> getMutationTarget() {
		return mutationTarget;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getSqlString() {
		return sql;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<? extends JdbcParameterBinder> getParameterBinders() {
		return parameterBinders;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public JdbcValueDescriptor findValueDescriptor(String columnName, ParameterUsage usage) {
		for ( int i = 0; i < jdbcValueDescriptors.size(); i++ ) {
			final var descriptor = jdbcValueDescriptors.get( i );
			if ( descriptor.getColumnName().equals( columnName )
					&& descriptor.getUsage() == usage ) {
				return descriptor;
			}
		}
		return null;
	}



	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isCallable() {
		return callable;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Expectation getExpectation() {
		return expectation;
	}
}
