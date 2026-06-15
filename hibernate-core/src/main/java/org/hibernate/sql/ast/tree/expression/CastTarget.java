/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.expression;

import jakarta.annotation.Nullable;
import org.hibernate.metamodel.mapping.JdbcMapping;
import org.hibernate.metamodel.mapping.JdbcMappingContainer;
import org.hibernate.metamodel.mapping.SqlTypedMapping;
import org.hibernate.sql.ast.SqlAstWalker;
import org.hibernate.sql.ast.tree.SqlAstNode;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Gavin King
 */
public class CastTarget implements Expression, SqlAstNode, SqlTypedMapping {
	private final JdbcMapping type;
	private final @Nullable Long length;
	private final @Nullable Integer arrayLength;
	private final @Nullable Integer precision;
	private final @Nullable Integer scale;

	public CastTarget(JdbcMapping type) {
		this( type, null, null, null, null );
	}

	public CastTarget(JdbcMapping type, @Nullable Long length, @Nullable Integer precision, @Nullable Integer scale) {
		this( type, length, null, precision, scale );
	}

	public CastTarget(JdbcMapping type, @Nullable Long length, @Nullable Integer arrayLength, @Nullable Integer precision, @Nullable Integer scale) {
		this.type = type;
		this.length = length;
		this.arrayLength = arrayLength;
		this.precision = precision;
		this.scale = scale;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JdbcMapping getJdbcMapping() {
		return type;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public @Nullable Long getLength() {
		return length;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public @Nullable Integer getArrayLength() {
		return arrayLength;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public @Nullable Integer getPrecision() {
		return precision;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public @Nullable Integer getTemporalPrecision() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public @Nullable Integer getScale() {
		return scale;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JdbcMappingContainer getExpressionType() {
		return type;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void accept(SqlAstWalker sqlTreeWalker) {
		sqlTreeWalker.visitCastTarget( this );
	}

}
