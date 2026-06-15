/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.produce.function;

import org.hibernate.query.sqm.function.FunctionKind;
import org.hibernate.query.sqm.function.PatternBasedSqmFunctionDescriptor;
import org.hibernate.query.sqm.function.SqmFunctionDescriptor;
import org.hibernate.query.sqm.function.SqmFunctionRegistry;
import org.hibernate.query.sqm.produce.function.internal.PatternRenderer;
import org.hibernate.sql.ast.SqlAstNodeRenderingMode;
import org.hibernate.type.BasicType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class PatternFunctionDescriptorBuilder {
	private final SqmFunctionRegistry registry;
	private final String registrationKey;
	private final FunctionKind functionKind;
	private final String pattern;
	private String argumentListSignature;

	private ArgumentsValidator argumentsValidator;
	private FunctionReturnTypeResolver returnTypeResolver;
	private FunctionArgumentTypeResolver argumentTypeResolver;
	private SqlAstNodeRenderingMode argumentRenderingMode = SqlAstNodeRenderingMode.DEFAULT;

	public PatternFunctionDescriptorBuilder(
			SqmFunctionRegistry registry,
			String registrationKey,
			FunctionKind functionKind,
			String pattern) {
		this.registry = registry;
		this.registrationKey = registrationKey;
		this.functionKind = functionKind;
		this.pattern = pattern;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PatternFunctionDescriptorBuilder setArgumentsValidator(ArgumentsValidator argumentsValidator) {
		this.argumentsValidator = argumentsValidator;
		return this;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PatternFunctionDescriptorBuilder setArgumentTypeResolver(FunctionArgumentTypeResolver argumentTypeResolver) {
		this.argumentTypeResolver = argumentTypeResolver;
		return this;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PatternFunctionDescriptorBuilder setParameterTypes(FunctionParameterType... types) {
		setArgumentsValidator( new ArgumentTypesValidator(argumentsValidator, types) );
		setArgumentTypeResolver( StandardFunctionArgumentTypeResolvers.invariant( types ) );
		return this;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PatternFunctionDescriptorBuilder setMinArgumentCount(int min) {
		return setArgumentsValidator( StandardArgumentsValidators.min( min ) );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PatternFunctionDescriptorBuilder setExactArgumentCount(int exactArgumentCount) {
		return setArgumentsValidator( StandardArgumentsValidators.exactly( exactArgumentCount ) );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PatternFunctionDescriptorBuilder setReturnTypeResolver(FunctionReturnTypeResolver returnTypeResolver) {
		this.returnTypeResolver = returnTypeResolver;
		return this;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PatternFunctionDescriptorBuilder setInvariantType(BasicType<?> invariantType) {
		setReturnTypeResolver( StandardFunctionReturnTypeResolvers.invariant( invariantType ) );
		return this;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PatternFunctionDescriptorBuilder setArgumentListSignature(String argumentListSignature) {
		this.argumentListSignature = argumentListSignature;
		return this;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PatternFunctionDescriptorBuilder setArgumentRenderingMode(SqlAstNodeRenderingMode argumentRenderingMode) {
		this.argumentRenderingMode = argumentRenderingMode;
		return this;
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SqmFunctionDescriptor register() {
		return registry.register( registrationKey, descriptor() );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqmFunctionDescriptor descriptor() {
		return new PatternBasedSqmFunctionDescriptor(
				new PatternRenderer( pattern, argumentRenderingMode ),
				argumentsValidator,
				returnTypeResolver,
				argumentTypeResolver,
				registrationKey,
				functionKind,
				argumentListSignature
		);
	}
}
