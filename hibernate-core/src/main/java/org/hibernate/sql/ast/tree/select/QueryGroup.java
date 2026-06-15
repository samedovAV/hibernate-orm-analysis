/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.select;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import org.hibernate.query.sqm.SetOperator;
import org.hibernate.sql.ast.SqlAstWalker;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Christian Beikov
 */
public class QueryGroup extends QueryPart {
	private final SetOperator setOperator;
	private final List<? extends QueryPart> queryParts;

	public QueryGroup(boolean isRoot, SetOperator setOperator, List<? extends QueryPart> queryParts) {
		super( isRoot );
		this.setOperator = setOperator;
		this.queryParts = queryParts;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public QuerySpec getFirstQuerySpec() {
		return queryParts.get( 0 ).getFirstQuerySpec();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public QuerySpec getLastQuerySpec() {
		return queryParts.get( queryParts.size() - 1 ).getLastQuerySpec();
	}

	@Override
	@Prove(complexity = Complexity.O_N2, n = "", count = {})
	public void visitQuerySpecs(Consumer<QuerySpec> querySpecConsumer) {
		for ( int i = 0; i < queryParts.size(); i++ ) {
			queryParts.get( i ).visitQuerySpecs( querySpecConsumer );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_N2, n = "", count = {})
	public <T> T queryQuerySpecs(Function<QuerySpec, T> querySpecConsumer) {
		for ( int i = 0; i < queryParts.size(); i++ ) {
			T result = queryParts.get( i ).queryQuerySpecs( querySpecConsumer );
			if ( result != null ) {
				return result;
			}
		}
		return null;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SetOperator getSetOperator() {
		return setOperator;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<? extends QueryPart> getQueryParts() {
		return queryParts;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void accept(SqlAstWalker sqlTreeWalker) {
		sqlTreeWalker.visitQueryGroup( this );
	}

}
