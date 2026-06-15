/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.sql.internal;

import org.hibernate.metamodel.mapping.ModelPart;
import org.hibernate.spi.NavigablePath;
import org.hibernate.query.sqm.tree.domain.SqmPath;
import org.hibernate.sql.ast.tree.expression.Expression;

import jakarta.annotation.Nullable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Interpretation of a {@link SqmPath} as part of the translation to SQL AST.  We need specialized handling
 * for path interpretations because it can (and likely) contains multiple SqlExpressions (entity to its columns, e.g.)
 *
 * @see org.hibernate.query.sqm.sql.SqmToSqlAstConverter
 *
 * @author Steve Ebersole
 */
public interface SqmPathInterpretation<T> extends Expression, DomainResultProducer<T> {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NavigablePath getNavigablePath();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ModelPart getExpressionType();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default Expression getSqlExpression() {
		return this;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default @Nullable String getAffectedTableName() {
		return null;
	}
}
