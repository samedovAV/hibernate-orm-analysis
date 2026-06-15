/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.tree.predicate;

import jakarta.annotation.Nonnull;
import org.hibernate.query.criteria.JpaPredicate;
import org.hibernate.query.sqm.SqmBindableType;
import org.hibernate.query.sqm.tree.SqmCopyContext;
import org.hibernate.query.sqm.tree.SqmVisitableNode;
import org.hibernate.query.sqm.tree.expression.SqmBooleanExpression;
import org.hibernate.type.descriptor.java.BooleanJavaType;
import org.hibernate.type.descriptor.java.JavaType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface SqmPredicate
		extends SqmVisitableNode, JpaPredicate, SqmBooleanExpression {
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default @Nonnull JavaType<Boolean> getJavaTypeDescriptor(){
		return BooleanJavaType.INSTANCE;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default @Nonnull JavaType<Boolean> getNodeJavaType() {
		return getNodeType().getExpressibleJavaType();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default @Nonnull SqmBindableType<Boolean> getExpressible() {
		return getNodeType();
	}

	@Nonnull @Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmBindableType<Boolean> getNodeType();

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate not();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate copy(SqmCopyContext context);
}
