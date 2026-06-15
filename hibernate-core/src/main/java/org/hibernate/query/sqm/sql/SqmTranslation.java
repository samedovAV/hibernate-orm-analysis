/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.sql;

import java.util.List;
import java.util.Map;

import org.hibernate.metamodel.mapping.MappingModelExpressible;
import org.hibernate.query.sqm.tree.expression.SqmParameter;
import org.hibernate.sql.ast.spi.FromClauseAccess;
import org.hibernate.sql.ast.spi.SqlExpressionResolver;
import org.hibernate.sql.ast.tree.Statement;
import org.hibernate.sql.ast.tree.expression.JdbcParameter;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Information obtained from the interpretation of an SqmStatement
 *
 * @author Steve Ebersole
 */
public interface SqmTranslation<T extends Statement> {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T getSqlAst();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqlExpressionResolver getSqlExpressionResolver();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	FromClauseAccess getFromClauseAccess();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Map<SqmParameter<?>, List<List<JdbcParameter>>> getJdbcParamsBySqmParam();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Map<SqmParameter<?>, MappingModelExpressible<?>> getSqmParameterMappingModelTypeResolutions();
}
