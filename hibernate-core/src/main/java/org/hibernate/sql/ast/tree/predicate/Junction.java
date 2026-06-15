/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.predicate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.metamodel.mapping.JdbcMappingContainer;
import org.hibernate.sql.ast.SqlAstWalker;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class Junction implements Predicate {
	public enum Nature {
		/**
		 * An AND
		 */
		CONJUNCTION,
		/**
		 * An OR
		 */
		DISJUNCTION
	}

	private final Nature nature;
	private final JdbcMappingContainer expressionType;
	private final List<Predicate> predicates;

	public Junction() {
		this( Nature.CONJUNCTION );
	}

	public Junction(Nature nature) {
		this( nature, null );
	}

	public Junction(Nature nature, JdbcMappingContainer expressionType) {
		this.nature = nature;
		this.expressionType = expressionType;
		this.predicates = new ArrayList<>();
	}

	public Junction(
			Nature nature,
			List<Predicate> predicates,
			JdbcMappingContainer expressionType) {
		this.nature = nature;
		this.expressionType = expressionType;
		this.predicates = predicates;
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void add(Predicate predicate) {
		predicates.add( predicate );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Nature getNature() {
		return nature;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<Predicate> getPredicates() {
		return predicates;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isEmpty() {
		return predicates.isEmpty();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void accept(SqlAstWalker sqlTreeWalker) {
		sqlTreeWalker.visitJunction( this );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JdbcMappingContainer getExpressionType() {
		return expressionType;
	}
}
