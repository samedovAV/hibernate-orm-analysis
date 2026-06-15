/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.dialect.sql.ast;

import org.hibernate.dialect.DatabaseVersion;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.query.sqm.ComparisonOperator;
import org.hibernate.sql.ast.tree.Statement;
import org.hibernate.sql.ast.tree.expression.Expression;
import org.hibernate.sql.ast.tree.from.QueryPartTableReference;
import org.hibernate.sql.ast.tree.select.QueryPart;
import org.hibernate.sql.exec.spi.JdbcOperation;

import static org.hibernate.dialect.DB2zDialect.DB2_LUW_VERSION;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A SQL AST translator for DB2z.
 *
 * @author Christian Beikov
 */
public class DB2zSqlAstTranslator<T extends JdbcOperation> extends DB2SqlAstTranslator<T> {

	private final DatabaseVersion version;

	public DB2zSqlAstTranslator(SessionFactoryImplementor sessionFactory, Statement statement, DatabaseVersion version) {
		super( sessionFactory, statement );
		this.version = version;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected boolean shouldEmulateFetchClause(QueryPart queryPart) {
		// Percent fetches or ties fetches aren't supported in DB2 z/OS
		// Also, variable limit isn't supported before 12.0
		return getQueryPartForRowNumbering() != queryPart && ( useOffsetFetchClause( queryPart ) && !isRowsOnlyFetchClauseType( queryPart ) );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected void renderComparison(Expression lhs, ComparisonOperator operator, Expression rhs) {
		// Supported at least since DB2 z/OS 9.0
		renderComparisonStandard( lhs, operator, rhs );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void visitQueryPartTableReference(QueryPartTableReference tableReference) {
		// DB2 z/OS we need the "table" qualifier for table valued functions or lateral sub-queries
		append( "table " );
		super.visitQueryPartTableReference( tableReference );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected String getNewTableChangeModifier() {
		// On DB2 zOS, `final` also sees the trigger data
		return "final";
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected boolean preferUnionQueryForTupleInListPredicate() {
		// DB2 z/OS can't use an index when rendering a union query
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public DatabaseVersion getDB2Version() {
		return DB2_LUW_VERSION;
	}
}
