/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.function;

import java.util.List;

import jakarta.annotation.Nullable;
import org.hibernate.metamodel.model.domain.ReturnableType;
import org.hibernate.query.spi.QueryEngine;
import org.hibernate.query.sqm.produce.function.ArgumentsValidator;
import org.hibernate.query.sqm.produce.function.FunctionArgumentTypeResolver;
import org.hibernate.query.sqm.produce.function.FunctionReturnTypeResolver;
import org.hibernate.query.sqm.produce.function.StandardArgumentsValidators;
import org.hibernate.query.sqm.produce.function.StandardFunctionArgumentTypeResolvers;
import org.hibernate.query.sqm.produce.function.StandardFunctionReturnTypeResolvers;
import org.hibernate.query.sqm.tree.SqmTypedNode;
import org.hibernate.query.sqm.tree.predicate.SqmPredicate;
import org.hibernate.query.sqm.tree.select.SqmOrderByClause;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public abstract class AbstractSqmFunctionDescriptor implements SqmFunctionDescriptor {
	private final ArgumentsValidator argumentsValidator;
	private final FunctionReturnTypeResolver returnTypeResolver;
	private final FunctionArgumentTypeResolver functionArgumentTypeResolver;
	private final String name;

	public AbstractSqmFunctionDescriptor(String name) {
		this( name, null, null, null );
	}

	public AbstractSqmFunctionDescriptor(
			String name,
			@Nullable ArgumentsValidator argumentsValidator) {
		this( name, argumentsValidator, null, null );
	}

	public AbstractSqmFunctionDescriptor(
			String name,
			@Nullable ArgumentsValidator argumentsValidator,
			@Nullable FunctionArgumentTypeResolver argumentTypeResolver) {
		this( name, argumentsValidator, null, argumentTypeResolver );
	}

	public AbstractSqmFunctionDescriptor(
			String name,
			@Nullable ArgumentsValidator argumentsValidator,
			@Nullable FunctionReturnTypeResolver returnTypeResolver,
			@Nullable FunctionArgumentTypeResolver argumentTypeResolver) {
		this.name = name;
		this.argumentsValidator = argumentsValidator == null
				? StandardArgumentsValidators.NONE
				: argumentsValidator;
		this.returnTypeResolver = returnTypeResolver == null
				? StandardFunctionReturnTypeResolvers.useFirstNonNull()
				: returnTypeResolver;
		this.functionArgumentTypeResolver = argumentTypeResolver == null
				? StandardFunctionArgumentTypeResolvers.NULL
				: argumentTypeResolver;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getName() {
		return name;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getSignature(String name) {
		return getReturnSignature() + name + getArgumentListSignature();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ArgumentsValidator getArgumentsValidator() {
		return argumentsValidator;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public FunctionReturnTypeResolver getReturnTypeResolver() {
		return returnTypeResolver;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public FunctionArgumentTypeResolver getArgumentTypeResolver() {
		return functionArgumentTypeResolver;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getReturnSignature() {
		String result = returnTypeResolver.getReturnType();
		return result.isEmpty() ? "" : result + " ";
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getArgumentListSignature() {
		String args = argumentsValidator.getSignature();
		return alwaysIncludesParentheses() ? args : "()".equals(args) ? "" : "[" + args + "]";
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public final <T> SelfRenderingSqmFunction<T> generateSqmExpression(
			List<? extends SqmTypedNode<?>> arguments,
			ReturnableType<T> impliedResultType,
			QueryEngine queryEngine) {
		argumentsValidator.validate( arguments, getName(), queryEngine );

		return generateSqmFunctionExpression(
				arguments,
				impliedResultType,
				queryEngine
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public final <T> SelfRenderingSqmFunction<T> generateAggregateSqmExpression(
			List<? extends SqmTypedNode<?>> arguments,
			SqmPredicate filter,
			ReturnableType<T> impliedResultType,
			QueryEngine queryEngine) {
		argumentsValidator.validate( arguments, getName(), queryEngine );

		return generateSqmAggregateFunctionExpression(
				arguments,
				filter,
				impliedResultType,
				queryEngine
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public final <T> SelfRenderingSqmFunction<T> generateOrderedSetAggregateSqmExpression(
			List<? extends SqmTypedNode<?>> arguments,
			SqmPredicate filter,
			SqmOrderByClause withinGroupClause,
			ReturnableType<T> impliedResultType,
			QueryEngine queryEngine) {
		argumentsValidator.validate( arguments, getName(), queryEngine );

		return generateSqmOrderedSetAggregateFunctionExpression(
				arguments,
				filter,
				withinGroupClause,
				impliedResultType,
				queryEngine
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public final <T> SelfRenderingSqmFunction<T> generateWindowSqmExpression(
			List<? extends SqmTypedNode<?>> arguments,
			SqmPredicate filter,
			Boolean respectNulls,
			Boolean fromFirst,
			ReturnableType<T> impliedResultType,
			QueryEngine queryEngine) {
		argumentsValidator.validate( arguments, getName(), queryEngine );

		return generateSqmWindowFunctionExpression(
				arguments,
				filter,
				respectNulls,
				fromFirst,
				impliedResultType,
				queryEngine
		);
	}

	/**
	 * Return an SQM node or subtree representing an invocation of this function
	 * with the given arguments. This method may be overridden in the case of
	 * function descriptors that wish to customize creation of the node.
	 *
	 * @param arguments         the arguments of the function invocation
	 * @param impliedResultType the function return type as inferred from its usage
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected abstract <T> SelfRenderingSqmFunction<T> generateSqmFunctionExpression(
			List<? extends SqmTypedNode<?>> arguments,
			ReturnableType<T> impliedResultType,
			QueryEngine queryEngine);

	/**
	 * Return an SQM node or subtree representing an invocation of this aggregate function
	 * with the given arguments. This method may be overridden in the case of
	 * function descriptors that wish to customize creation of the node.
	 *
	 * @param arguments         the arguments of the function invocation
	 * @param impliedResultType the function return type as inferred from its usage
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected <T> SelfRenderingSqmAggregateFunction<T> generateSqmAggregateFunctionExpression(
			List<? extends SqmTypedNode<?>> arguments,
			SqmPredicate filter,
			ReturnableType<T> impliedResultType,
			QueryEngine queryEngine) {
		return (SelfRenderingSqmAggregateFunction<T>) generateSqmExpression(
				arguments,
				impliedResultType,
				queryEngine
		);
	}

	/**
	 * Return an SQM node or subtree representing an invocation of this ordered set-aggregate function
	 * with the given arguments. This method may be overridden in the case of
	 * function descriptors that wish to customize creation of the node.
	 *
	 * @param arguments         the arguments of the function invocation
	 * @param impliedResultType the function return type as inferred from its usage
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected <T> SelfRenderingSqmAggregateFunction<T> generateSqmOrderedSetAggregateFunctionExpression(
			List<? extends SqmTypedNode<?>> arguments,
			SqmPredicate filter,
			SqmOrderByClause withinGroupClause,
			ReturnableType<T> impliedResultType,
			QueryEngine queryEngine) {
		return (SelfRenderingSqmAggregateFunction<T>) generateSqmExpression(
				arguments,
				impliedResultType,
				queryEngine
		);
	}

	/**
	 * Return an SQM node or subtree representing an invocation of this window function
	 * with the given arguments. This method may be overridden in the case of
	 * function descriptors that wish to customize creation of the node.
	 *
	 * @param arguments         the arguments of the function invocation
	 * @param impliedResultType the function return type as inferred from its usage
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected <T> SelfRenderingSqmWindowFunction<T> generateSqmWindowFunctionExpression(
			List<? extends SqmTypedNode<?>> arguments,
			SqmPredicate filter,
			Boolean respectNulls,
			Boolean fromFirst,
			ReturnableType<T> impliedResultType,
			QueryEngine queryEngine) {
		return (SelfRenderingSqmWindowFunction<T>) generateSqmExpression(
				arguments,
				impliedResultType,
				queryEngine
		);
	}
}
