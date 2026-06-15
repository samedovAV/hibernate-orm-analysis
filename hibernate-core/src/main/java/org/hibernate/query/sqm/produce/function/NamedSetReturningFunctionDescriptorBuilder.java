/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.produce.function;

import org.hibernate.Incubating;
import org.hibernate.query.sqm.function.NamedSqmSetReturningFunctionDescriptor;
import org.hibernate.query.sqm.function.SqmFunctionRegistry;
import org.hibernate.query.sqm.function.SqmSetReturningFunctionDescriptor;
import org.hibernate.sql.ast.SqlAstNodeRenderingMode;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @since 7.0
 */
@Incubating
public class NamedSetReturningFunctionDescriptorBuilder {

	private final SqmFunctionRegistry registry;
	private final String registrationKey;

	private final String functionName;
	private final SetReturningFunctionTypeResolver setReturningTypeResolver;

	private ArgumentsValidator argumentsValidator;
	private FunctionArgumentTypeResolver argumentTypeResolver;

	private String argumentListSignature;
	private SqlAstNodeRenderingMode argumentRenderingMode = SqlAstNodeRenderingMode.DEFAULT;

	public NamedSetReturningFunctionDescriptorBuilder(
			SqmFunctionRegistry registry,
			String registrationKey,
			String functionName,
			SetReturningFunctionTypeResolver typeResolver) {
		this.registry = registry;
		this.registrationKey = registrationKey;
		this.functionName = functionName;
		this.setReturningTypeResolver = typeResolver;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public NamedSetReturningFunctionDescriptorBuilder setArgumentsValidator(ArgumentsValidator argumentsValidator) {
		this.argumentsValidator = argumentsValidator;
		return this;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public NamedSetReturningFunctionDescriptorBuilder setArgumentTypeResolver(FunctionArgumentTypeResolver argumentTypeResolver) {
		this.argumentTypeResolver = argumentTypeResolver;
		return this;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public NamedSetReturningFunctionDescriptorBuilder setArgumentCountBetween(int min, int max) {
		return setArgumentsValidator( StandardArgumentsValidators.between( min, max ) );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public NamedSetReturningFunctionDescriptorBuilder setExactArgumentCount(int exactArgumentCount) {
		return setArgumentsValidator( StandardArgumentsValidators.exactly( exactArgumentCount ) );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public NamedSetReturningFunctionDescriptorBuilder setMinArgumentCount(int min) {
		return setArgumentsValidator( StandardArgumentsValidators.min( min ) );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public NamedSetReturningFunctionDescriptorBuilder setParameterTypes(FunctionParameterType... types) {
		setArgumentsValidator( new ArgumentTypesValidator(argumentsValidator, types) );
		setArgumentTypeResolver( StandardFunctionArgumentTypeResolvers.invariant( types ) );
		return this;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public NamedSetReturningFunctionDescriptorBuilder setArgumentListSignature(String argumentListSignature) {
		this.argumentListSignature = argumentListSignature;
		return this;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public NamedSetReturningFunctionDescriptorBuilder setArgumentRenderingMode(SqlAstNodeRenderingMode argumentRenderingMode) {
		this.argumentRenderingMode = argumentRenderingMode;
		return this;
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SqmSetReturningFunctionDescriptor register() {
		return registry.register( registrationKey, descriptor() );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqmSetReturningFunctionDescriptor descriptor() {
		return new NamedSqmSetReturningFunctionDescriptor(
				functionName,
				argumentsValidator,
				setReturningTypeResolver,
				argumentTypeResolver,
				registrationKey,
				argumentListSignature,
				argumentRenderingMode
		);
	}

}
