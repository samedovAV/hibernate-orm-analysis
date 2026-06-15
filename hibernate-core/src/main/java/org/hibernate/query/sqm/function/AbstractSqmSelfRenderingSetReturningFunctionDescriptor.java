/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.function;

import java.util.List;

import org.hibernate.Incubating;
import org.hibernate.query.spi.QueryEngine;
import org.hibernate.query.sqm.produce.function.ArgumentsValidator;
import org.hibernate.query.sqm.produce.function.FunctionArgumentTypeResolver;
import org.hibernate.query.sqm.produce.function.SetReturningFunctionTypeResolver;
import org.hibernate.query.sqm.tree.SqmTypedNode;

import jakarta.annotation.Nullable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @since 7.0
 */
@Incubating
public abstract class AbstractSqmSelfRenderingSetReturningFunctionDescriptor
		extends AbstractSqmSetReturningFunctionDescriptor implements SetReturningFunctionRenderer {

	public AbstractSqmSelfRenderingSetReturningFunctionDescriptor(
			String name,
			@Nullable ArgumentsValidator argumentsValidator,
			SetReturningFunctionTypeResolver typeResolver,
			@Nullable FunctionArgumentTypeResolver argumentTypeResolver) {
		super( name, argumentsValidator, typeResolver, argumentTypeResolver );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected <T> SelfRenderingSqmSetReturningFunction<T> generateSqmSetReturningFunctionExpression(
			List<? extends SqmTypedNode<?>> arguments,
			QueryEngine queryEngine) {
		return new SelfRenderingSqmSetReturningFunction<>(
				this,
				this,
				arguments,
				getArgumentsValidator(),
				getSetReturningTypeResolver(),
				queryEngine.getCriteriaBuilder(),
				getName()
		);
	}
}
