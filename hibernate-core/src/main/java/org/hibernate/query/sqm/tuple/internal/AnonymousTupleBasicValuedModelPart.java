/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.tuple.internal;

import java.util.function.BiConsumer;

import jakarta.annotation.Nullable;
import org.hibernate.Incubating;
import org.hibernate.cache.MutableCacheKeyBuilder;
import org.hibernate.engine.FetchStyle;
import org.hibernate.engine.FetchTiming;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.internal.util.IndexedConsumer;
import org.hibernate.metamodel.mapping.BasicValuedModelPart;
import org.hibernate.metamodel.mapping.EntityMappingType;
import org.hibernate.metamodel.mapping.JdbcMapping;
import org.hibernate.metamodel.mapping.MappingType;
import org.hibernate.metamodel.mapping.OwnedValuedModelPart;
import org.hibernate.metamodel.mapping.SelectableConsumer;
import org.hibernate.metamodel.mapping.SelectableMapping;
import org.hibernate.metamodel.mapping.SelectablePath;
import org.hibernate.metamodel.mapping.internal.SelectableMappingImpl;
import org.hibernate.metamodel.model.domain.NavigableRole;
import org.hibernate.query.sqm.SqmExpressible;
import org.hibernate.spi.NavigablePath;
import org.hibernate.sql.ast.spi.SqlAstCreationState;
import org.hibernate.sql.ast.spi.SqlExpressionResolver;
import org.hibernate.sql.ast.spi.SqlSelection;
import org.hibernate.sql.ast.tree.expression.Expression;
import org.hibernate.sql.ast.tree.from.TableGroup;
import org.hibernate.sql.ast.tree.from.TableReference;
import org.hibernate.sql.results.graph.DomainResult;
import org.hibernate.sql.results.graph.DomainResultCreationState;
import org.hibernate.sql.results.graph.FetchOptions;
import org.hibernate.sql.results.graph.FetchParent;
import org.hibernate.sql.results.graph.basic.BasicFetch;
import org.hibernate.sql.results.graph.basic.BasicResult;
import org.hibernate.type.descriptor.java.JavaType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Christian Beikov
 */
@Incubating
public class AnonymousTupleBasicValuedModelPart implements OwnedValuedModelPart, MappingType, BasicValuedModelPart {

	private static final FetchOptions FETCH_OPTIONS = FetchOptions.valueOf( FetchTiming.IMMEDIATE, FetchStyle.JOIN );
	private final MappingType declaringType;
	private final String partName;
	private final SelectableMapping selectableMapping;
	private final SqmExpressible<?> expressible;
	private final int fetchableIndex;

	public AnonymousTupleBasicValuedModelPart(
			MappingType declaringType,
			String partName,
			String selectionExpression,
			SqmExpressible<?> expressible,
			JdbcMapping jdbcMapping,
			int fetchableIndex) {
		this(
				declaringType,
				partName,
				new SelectableMappingImpl(
						"",
						selectionExpression,
						null,
						null,
						null,
						null,
						null,
						null,
						null,
						null,
						false,
						true,
						false,
						false,
						false,
						false,
						jdbcMapping
				),
				expressible,
				fetchableIndex
		);
	}

	public AnonymousTupleBasicValuedModelPart(
			MappingType declaringType,
			String partName,
			SelectableMapping selectableMapping,
			SqmExpressible<?> expressible,
			int fetchableIndex) {
		this.declaringType = declaringType;
		this.partName = partName;
		this.selectableMapping = selectableMapping;
		this.expressible = expressible;
		this.fetchableIndex = fetchableIndex;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public MappingType getPartMappingType() {
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JavaType<?> getJavaType() {
		return expressible.getExpressibleJavaType();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JavaType<?> getMappedJavaType() {
		return expressible.getExpressibleJavaType();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public MappingType getDeclaringType() {
		return declaringType;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getPartName() {
		return partName;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public NavigableRole getNavigableRole() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public EntityMappingType findContainingEntityMapping() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getSelectableName() {
		return selectableMapping.getSelectableName();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SelectablePath getSelectablePath() {
		return selectableMapping.getSelectablePath();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getWriteExpression() {
		return selectableMapping.getWriteExpression();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public JdbcMapping getJdbcMapping() {
		return selectableMapping.getJdbcMapping();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getContainingTableExpression() {
		return selectableMapping.getContainingTableExpression();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getSelectionExpression() {
		return selectableMapping.getSelectionExpression();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public @Nullable String getCustomReadExpression() {
		return selectableMapping.getCustomReadExpression();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public @Nullable String getCustomWriteExpression() {
		return selectableMapping.getCustomWriteExpression();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isFormula() {
		return selectableMapping.isFormula();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isNullable() {
		return selectableMapping.isNullable();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isInsertable() {
		return selectableMapping.isInsertable();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isUpdateable() {
		return selectableMapping.isUpdateable();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isPartitioned() {
		return selectableMapping.isPartitioned();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean hasPartitionedSelectionMapping() {
		return selectableMapping.isPartitioned();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public @Nullable Long getLength() {
		return selectableMapping.getLength();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public @Nullable Integer getArrayLength() {
		return selectableMapping.getArrayLength();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public @Nullable Integer getPrecision() {
		return selectableMapping.getPrecision();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public @Nullable Integer getScale() {
		return selectableMapping.getScale();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public @Nullable Integer getTemporalPrecision() {
		return selectableMapping.getTemporalPrecision();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public MappingType getMappedType() {
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getFetchableName() {
		return partName;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getFetchableKey() {
		return fetchableIndex;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public FetchOptions getMappedFetchOptions() {
		return FETCH_OPTIONS;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <T> DomainResult<T> createDomainResult(
			NavigablePath navigablePath,
			TableGroup tableGroup,
			String resultVariable,
			DomainResultCreationState creationState) {
		final SqlSelection sqlSelection = resolveSqlSelection(
				navigablePath,
				tableGroup,
				null,
				creationState.getSqlAstCreationState()
		);

		return new BasicResult<>(
				sqlSelection.getValuesArrayPosition(),
				resultVariable,
				getJdbcMapping(),
				navigablePath,
				false,
				!sqlSelection.isVirtual()
		);
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	private SqlSelection resolveSqlSelection(
			NavigablePath navigablePath,
			TableGroup tableGroup,
			FetchParent fetchParent,
			SqlAstCreationState creationState) {
		final SqlExpressionResolver expressionResolver = creationState.getSqlExpressionResolver();
		final TableReference tableReference = tableGroup.resolveTableReference(
				navigablePath,
				this,
				getContainingTableExpression()
		);
		final Expression expression = expressionResolver.resolveSqlExpression( tableReference, this );
		return expressionResolver.resolveSqlSelection(
				expression,
				getJdbcMapping().getJdbcJavaType(),
				fetchParent,
				creationState.getCreationContext().getTypeConfiguration()
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public BasicFetch<?> generateFetch(
			FetchParent fetchParent,
			NavigablePath fetchablePath,
			FetchTiming fetchTiming,
			boolean selected,
			String resultVariable,
			DomainResultCreationState creationState) {
		final SqlAstCreationState sqlAstCreationState = creationState.getSqlAstCreationState();
		final TableGroup tableGroup = sqlAstCreationState.getFromClauseAccess().getTableGroup(
				fetchParent.getNavigablePath()
		);

		assert tableGroup != null;

		final SqlSelection sqlSelection = resolveSqlSelection(
				fetchablePath,
				tableGroup,
				fetchParent,
				creationState.getSqlAstCreationState()
		);

		return new BasicFetch<>(
				sqlSelection.getValuesArrayPosition(),
				fetchParent,
				fetchablePath,
				this,
				fetchTiming,
				!sqlSelection.isVirtual()
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void applySqlSelections(
			NavigablePath navigablePath,
			TableGroup tableGroup,
			DomainResultCreationState creationState) {
		resolveSqlSelection( navigablePath, tableGroup, null, creationState.getSqlAstCreationState() );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void applySqlSelections(
			NavigablePath navigablePath,
			TableGroup tableGroup,
			DomainResultCreationState creationState,
			BiConsumer<SqlSelection, JdbcMapping> selectionConsumer) {
		selectionConsumer.accept(
				resolveSqlSelection( navigablePath, tableGroup, null, creationState.getSqlAstCreationState() ),
				getJdbcMapping()
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <X, Y> int forEachDisassembledJdbcValue(
			Object value,
			int offset,
			X x,
			Y y,
			JdbcValuesBiConsumer<X, Y> valuesConsumer,
			SharedSessionContractImplementor session) {
		valuesConsumer.consume( offset, x, y, value, getJdbcMapping() );
		return getJdbcTypeCount();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int forEachJdbcType(int offset, IndexedConsumer<JdbcMapping> action) {
		action.accept( offset, getJdbcMapping() );
		return getJdbcTypeCount();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <X, Y> int breakDownJdbcValues(
			Object domainValue,
			int offset,
			X x,
			Y y,
			JdbcValueBiConsumer<X, Y> valueConsumer,
			SharedSessionContractImplementor session) {
		valueConsumer.consume( offset, x, y, domainValue, this );
		return getJdbcTypeCount();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object disassemble(Object value, SharedSessionContractImplementor session) {
		return value;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void addToCacheKey(MutableCacheKeyBuilder cacheKey, Object value, SharedSessionContractImplementor session) {
		if ( value == null ) {
			return;
		}
		cacheKey.addValue( value );
		cacheKey.addHashCode( ( (JavaType) getExpressibleJavaType() ).extractHashCode( value ) );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <X, Y> int forEachJdbcValue(
			Object value,
			int offset,
			X x,
			Y y,
			JdbcValuesBiConsumer<X, Y> valuesConsumer,
			SharedSessionContractImplementor session) {
		valuesConsumer.consume( offset, x, y, value, getJdbcMapping() );
		return getJdbcTypeCount();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int forEachSelectable(int offset, SelectableConsumer consumer) {
		consumer.accept( offset, this );
		return getJdbcTypeCount();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int forEachJdbcType(IndexedConsumer<JdbcMapping> action) {
		action.accept( 0, getJdbcMapping() );
		return getJdbcTypeCount();
	}
}
