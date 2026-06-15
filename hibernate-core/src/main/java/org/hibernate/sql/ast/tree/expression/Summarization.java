/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.expression;

import java.util.List;

import org.hibernate.metamodel.mapping.MappingModelExpressible;
import org.hibernate.sql.ast.SqlAstWalker;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Christian Beikov
 */
public class Summarization implements Expression {

	private final Kind kind;
	private final List<Expression> groupings;

	public Summarization(Kind kind, List<Expression> groupings) {
		this.kind = kind;
		this.groupings = groupings;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Kind getKind() {
		return kind;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<Expression> getGroupings() {
		return groupings;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public MappingModelExpressible getExpressionType() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void accept(SqlAstWalker walker) {
		walker.visitSummarization( this );
	}

	public enum Kind {
		ROLLUP( "rollup" ),
		CUBE( "cube" );

		private final String sqlText;

		Kind(String sqlText) {
			this.sqlText = sqlText;
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public String sqlText() {
			return sqlText;
		}
	}
}
