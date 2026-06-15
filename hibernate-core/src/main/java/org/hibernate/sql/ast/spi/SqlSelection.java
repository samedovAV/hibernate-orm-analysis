/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.spi;

import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.metamodel.mapping.JdbcMappingContainer;
import org.hibernate.sql.ast.tree.SqlAstNode;
import org.hibernate.sql.ast.tree.expression.Expression;
import org.hibernate.sql.results.jdbc.spi.JdbcValuesMetadata;
import org.hibernate.sql.ast.SqlAstWalker;
import org.hibernate.type.descriptor.ValueExtractor;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/// Represents a selection at the SQL/JDBC level.  Essentially made up of:
/// * [#getJdbcValueExtractor] - How to read a value from JDBC (conceptually similar to a method reference)
/// * [#getValuesArrayPosition] - The position for this selection in relation to the "JDBC values array" (see [org.hibernate.sql.results.jdbc.spi.RowProcessingState#getJdbcValue])
/// * [#getJdbcResultSetIndex()] - The position for this selection in relation to the JDBC object (ResultSet, etc)
///
/// @author Steve Ebersole
public interface SqlSelection extends SqlAstNode {
	/// Get the extractor that can be used to extract JDBC values for this selection
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ValueExtractor getJdbcValueExtractor();

	/// Get the position within the "JDBC values" array (0-based).  Negative indicates this is
	/// not a "real" selection
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	int getValuesArrayPosition();

	/// Get the JDBC position (1-based)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default int getJdbcResultSetIndex() {
		return getValuesArrayPosition() + 1;
	}

	/// The underlying expression.
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Expression getExpression();

	/// Get the type of the expression
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JdbcMappingContainer getExpressionType();

	/// Whether this is a virtual or a real selection item.
	/// Virtual selection items are not rendered into the SQL select clause.
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isVirtual();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void accept(SqlAstWalker sqlAstWalker);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqlSelection resolve(JdbcValuesMetadata jdbcResultsMetadata, SessionFactoryImplementor sessionFactory);
}
