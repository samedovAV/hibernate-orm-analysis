/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.produce.function.internal;

import jakarta.annotation.Nullable;
import org.hibernate.metamodel.mapping.MappingModelExpressible;
import org.hibernate.query.sqm.produce.function.FunctionArgumentTypeResolver;
import org.hibernate.query.sqm.sql.SqmToSqlAstConverter;
import org.hibernate.query.sqm.tree.SqmTypedNode;
import org.hibernate.query.sqm.tree.expression.SqmFunction;

import java.util.List;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

@FunctionalInterface
public interface AbstractFunctionArgumentTypeResolver extends FunctionArgumentTypeResolver {
	@Override
	@SuppressWarnings("removal")
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default @Nullable MappingModelExpressible<?> resolveFunctionArgumentType(SqmFunction<?> function, int argumentIndex, SqmToSqlAstConverter converter) {
		return resolveFunctionArgumentType( function.getArguments(), argumentIndex, converter );
	}

	@Override
	@Nullable@Prove(complexity = Complexity.O_1, n = "", count = {})
 MappingModelExpressible<?> resolveFunctionArgumentType(List<? extends SqmTypedNode<?>> arguments, int argumentIndex, SqmToSqlAstConverter converter);
}
