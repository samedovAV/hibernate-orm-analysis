/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.model.ast;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.hibernate.jdbc.Expectation;
import org.hibernate.sql.exec.spi.JdbcParameterBinder;
import org.hibernate.sql.model.MutationOperation;
import org.hibernate.sql.model.MutationTarget;
import org.hibernate.sql.model.TableMapping;
import org.hibernate.sql.model.jdbc.JdbcUpdateMutation;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Base support for TableUpdate implementations
 *
 * @author Steve Ebersole
 */
public abstract class AbstractTableUpdate<O extends MutationOperation>
		extends AbstractRestrictedTableMutation<O>
		implements TableUpdate<O> {
	private final List<ColumnValueBinding> valueBindings;

	public AbstractTableUpdate(
			MutatingTableReference mutatingTable,
			MutationTarget<?,?> mutationTarget,
			String sqlComment,
			List<ColumnValueBinding> valueBindings,
			List<ColumnValueBinding> keyRestrictionBindings,
			List<ColumnValueBinding> optLockRestrictionBindings) {
		super(
				mutatingTable,
				mutationTarget,
				sqlComment,
				keyRestrictionBindings,
				optLockRestrictionBindings,
				collectParameters( valueBindings, keyRestrictionBindings, optLockRestrictionBindings )
		);

		this.valueBindings = valueBindings;
	}

	public <T> AbstractTableUpdate(
			MutatingTableReference tableReference,
			MutationTarget<?,?> mutationTarget,
			String sqlComment,
			List<ColumnValueBinding> valueBindings,
			List<ColumnValueBinding> keyRestrictionBindings,
			List<ColumnValueBinding> optLockRestrictionBindings,
			List<ColumnValueParameter> parameters) {
		super(
				tableReference,
				mutationTarget,
				sqlComment,
				keyRestrictionBindings,
				optLockRestrictionBindings,
				parameters
		);
		this.valueBindings = valueBindings;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static List<ColumnValueParameter> collectParameters(
			List<ColumnValueBinding> valueBindings,
			List<ColumnValueBinding> keyRestrictionBindings,
			List<ColumnValueBinding> optLockRestrictionBindings) {
		final List<ColumnValueParameter> params = new ArrayList<>();

		final BiConsumer<Integer,ColumnValueBinding> intermediateConsumer = (index, binding) -> {
			final ColumnWriteFragment valueExpression = binding.getValueExpression();
			if ( valueExpression != null ) {
				params.addAll( valueExpression.getParameters() );
			}
		};

		forEachThing( valueBindings, intermediateConsumer );
		forEachThing( keyRestrictionBindings, intermediateConsumer );
		forEachThing( optLockRestrictionBindings, intermediateConsumer );

		return params;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected String getLoggableName() {
		return "TableUpdate";
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Expectation getExpectation() {
		return getMutatingTable().getTableMapping().getUpdateDetails().getExpectation();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<ColumnValueBinding> getValueBindings() {
		return valueBindings;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void forEachValueBinding(BiConsumer<Integer, ColumnValueBinding> consumer) {
		forEachThing( valueBindings, consumer );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void forEachParameter(Consumer<ColumnValueParameter> consumer) {
		final BiConsumer<Integer,ColumnValueBinding> intermediateConsumer = (index, binding) -> {
			for ( ColumnValueParameter parameter : binding.getValueExpression().getParameters() ) {
				consumer.accept( parameter );
			}
		};

		forEachThing( getValueBindings(), intermediateConsumer );
		forEachThing( getKeyBindings(), intermediateConsumer );
		forEachThing( getOptimisticLockBindings(), intermediateConsumer );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected O createMutationOperation(TableMapping tableDetails, String sql, List<JdbcParameterBinder> effectiveBinders) {
		//noinspection unchecked
		return (O) new JdbcUpdateMutation(
				tableDetails,
				getMutationTarget(),
				sql,
				isCallable(),
				getExpectation(),
				effectiveBinders
		);
	}
}
