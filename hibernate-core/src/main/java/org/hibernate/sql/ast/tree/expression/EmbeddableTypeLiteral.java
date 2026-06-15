/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.expression;

import org.hibernate.cache.MutableCacheKeyBuilder;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.internal.util.IndexedConsumer;
import org.hibernate.metamodel.mapping.BasicValuedMapping;
import org.hibernate.metamodel.mapping.JdbcMapping;
import org.hibernate.metamodel.mapping.MappingModelExpressible;
import org.hibernate.metamodel.mapping.MappingType;
import org.hibernate.metamodel.model.domain.EmbeddableDomainType;
import org.hibernate.query.sqm.sql.internal.DomainResultProducer;
import org.hibernate.sql.ast.SqlAstWalker;
import org.hibernate.sql.ast.spi.SqlSelection;
import org.hibernate.sql.results.graph.DomainResult;
import org.hibernate.sql.results.graph.DomainResultCreationState;
import org.hibernate.sql.results.graph.basic.BasicResult;
import org.hibernate.type.BasicType;
import org.hibernate.type.descriptor.java.JavaType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class EmbeddableTypeLiteral
		implements Expression, DomainResultProducer<Object>, BasicValuedMapping {
	private final Class<?> embeddableClass;
	private final BasicType<?> basicType;

	public EmbeddableTypeLiteral(
			EmbeddableDomainType<?> embeddableDomainType,
			BasicType<?> basicType) {
		this.embeddableClass = embeddableDomainType.getJavaType();
		this.basicType = basicType;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object getEmbeddableClass() {
		return embeddableClass;
	}

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// BasicValuedMapping

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public MappingModelExpressible<?> getExpressionType() {
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JdbcMapping getJdbcMapping() {
		return basicType;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public MappingType getMappedType() {
		return basicType;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public int getJdbcTypeCount() {
		return basicType.getJdbcTypeCount();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public JdbcMapping getJdbcMapping(int index) {
		return basicType.getJdbcMapping( index );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public JdbcMapping getSingleJdbcMapping() {
		return basicType.getSingleJdbcMapping();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public int forEachJdbcType(int offset, IndexedConsumer<JdbcMapping> action) {
		return basicType.forEachJdbcType( offset, action );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Object disassemble(Object value, SharedSessionContractImplementor session) {
		return basicType.disassemble( value, session );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void addToCacheKey(MutableCacheKeyBuilder cacheKey, Object value, SharedSessionContractImplementor session) {
		basicType.addToCacheKey( cacheKey, value, session );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public <X, Y> int forEachDisassembledJdbcValue(
			Object value,
			int offset,
			X x,
			Y y,
			JdbcValuesBiConsumer<X, Y> valuesConsumer,
			SharedSessionContractImplementor session) {
		return basicType.forEachDisassembledJdbcValue( value, offset, x, y, valuesConsumer, session );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public <X, Y> int forEachJdbcValue(
			Object value,
			int offset,
			X x,
			Y y,
			JdbcValuesBiConsumer<X, Y> valuesConsumer,
			SharedSessionContractImplementor session) {
		return basicType.forEachJdbcValue( value, offset, x, y, valuesConsumer, session );
	}


	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// DomainResultProducer

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void applySqlSelections(DomainResultCreationState creationState) {
		createSqlSelection( creationState );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public DomainResult<Object> createDomainResult(String resultVariable, DomainResultCreationState creationState) {
		return new BasicResult<>(
				createSqlSelection( creationState ).getValuesArrayPosition(),
				resultVariable,
				basicType
		);
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private SqlSelection createSqlSelection(DomainResultCreationState creationState) {
		return creationState.getSqlAstCreationState().getSqlExpressionResolver().resolveSqlSelection(
				this,
				basicType.getJdbcJavaType(),
				null,
				creationState.getSqlAstCreationState().getCreationContext().getMappingMetamodel().getTypeConfiguration()
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void accept(SqlAstWalker sqlTreeWalker) {
		sqlTreeWalker.visitEmbeddableTypeLiteral( this );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public JavaType getExpressibleJavaType() {
		return basicType.getExpressibleJavaType();
	}
}
