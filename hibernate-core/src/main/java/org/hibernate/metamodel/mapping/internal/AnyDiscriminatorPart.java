/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.mapping.internal;

import jakarta.annotation.Nullable;
import org.hibernate.cache.MutableCacheKeyBuilder;
import org.hibernate.engine.FetchStyle;
import org.hibernate.engine.FetchTiming;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.internal.util.IndexedConsumer;
import org.hibernate.metamodel.mapping.DiscriminatedAssociationModelPart;
import org.hibernate.metamodel.mapping.DiscriminatorConverter;
import org.hibernate.metamodel.mapping.DiscriminatorMapping;
import org.hibernate.metamodel.mapping.DiscriminatorValue;
import org.hibernate.metamodel.mapping.EntityDiscriminatorMapping;
import org.hibernate.metamodel.mapping.EntityMappingType;
import org.hibernate.metamodel.mapping.JdbcMapping;
import org.hibernate.metamodel.mapping.MappingType;
import org.hibernate.metamodel.mapping.SelectableConsumer;
import org.hibernate.metamodel.mapping.SelectablePath;
import org.hibernate.metamodel.model.domain.NavigableRole;
import org.hibernate.metamodel.spi.ImplicitDiscriminatorStrategy;
import org.hibernate.metamodel.spi.MappingMetamodelImplementor;
import org.hibernate.spi.NavigablePath;
import org.hibernate.sql.ast.spi.SqlAstCreationState;
import org.hibernate.sql.ast.spi.SqlSelection;
import org.hibernate.sql.ast.tree.expression.Expression;
import org.hibernate.sql.ast.tree.from.TableGroup;
import org.hibernate.sql.results.graph.DomainResult;
import org.hibernate.sql.results.graph.DomainResultCreationState;
import org.hibernate.sql.results.graph.FetchOptions;
import org.hibernate.sql.results.graph.FetchParent;
import org.hibernate.sql.results.graph.basic.BasicFetch;
import org.hibernate.sql.results.graph.basic.BasicResult;
import org.hibernate.type.BasicType;
import org.hibernate.type.descriptor.java.ClassJavaType;
import org.hibernate.type.descriptor.java.JavaType;

import java.util.Map;
import java.util.function.BiConsumer;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Acts as a ModelPart for the discriminator portion of an any-valued mapping
 *
 * @author Steve Ebersole
 */
public class AnyDiscriminatorPart implements DiscriminatorMapping, FetchOptions {
	public static final String ROLE_NAME = EntityDiscriminatorMapping.DISCRIMINATOR_ROLE_NAME;

	private final NavigableRole navigableRole;
	private final DiscriminatedAssociationModelPart declaringType;

	private final String table;
	private final String column;
	private final SelectablePath selectablePath;
	private final @Nullable String customReadExpression;
	private final @Nullable String customWriteExpression;
	private final @Nullable Long length;
	private final @Nullable Integer arrayLength;
	private final @Nullable Integer precision;
	private final @Nullable Integer scale;

	private final boolean insertable;
	private final boolean updateable;
	private final boolean partitioned;

	private final BasicType<?> underlyingJdbcMapping;
	private final DiscriminatorConverter<?,?> valueConverter;

	public AnyDiscriminatorPart(
			NavigableRole partRole,
			DiscriminatedAssociationModelPart declaringType,
			String table,
			String column,
			SelectablePath selectablePath,
			@Nullable String customReadExpression,
			@Nullable String customWriteExpression,
			@Nullable Long length,
			@Nullable Integer arrayLength,
			@Nullable Integer precision,
			@Nullable Integer scale,
			boolean insertable,
			boolean updateable,
			boolean partitioned,
			BasicType<?> underlyingJdbcMapping,
			Map<DiscriminatorValue,String> valueToEntityNameMap,
			ImplicitDiscriminatorStrategy implicitValueStrategy,
			MappingMetamodelImplementor mappingMetamodel) {
		this.navigableRole = partRole;
		this.declaringType = declaringType;
		this.table = table;
		this.column = column;
		this.selectablePath = selectablePath;
		this.customReadExpression = customReadExpression;
		this.customWriteExpression = customWriteExpression;
		this.length = length;
		this.arrayLength = arrayLength;
		this.precision = precision;
		this.scale = scale;
		this.insertable = insertable;
		this.updateable = updateable;
		this.partitioned = partitioned;

		this.underlyingJdbcMapping = underlyingJdbcMapping;
		this.valueConverter = determineDiscriminatorConverter(
				partRole,
				underlyingJdbcMapping,
				valueToEntityNameMap,
				implicitValueStrategy,
				mappingMetamodel
		);
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static DiscriminatorConverter<?, ?> determineDiscriminatorConverter(
			NavigableRole partRole,
			BasicType<?> underlyingJdbcMapping,
			Map<DiscriminatorValue, String> valueToEntityNameMap,
			ImplicitDiscriminatorStrategy implicitValueStrategy,
			MappingMetamodelImplementor mappingMetamodel) {
		return new UnifiedAnyDiscriminatorConverter<>(
				partRole,
				ClassJavaType.INSTANCE,
				underlyingJdbcMapping.getJavaTypeDescriptor(),
				valueToEntityNameMap,
				implicitValueStrategy,
				mappingMetamodel
		);
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public DiscriminatorConverter<?,?> getValueConverter() {
		return valueConverter;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JdbcMapping jdbcMapping() {
		return underlyingJdbcMapping;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getContainingTableExpression() {
		return table;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getSelectionExpression() {
		return column;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getSelectableName() {
		return selectablePath.getSelectableName();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SelectablePath getSelectablePath() {
		return selectablePath;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isFormula() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isNullable() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isInsertable() {
		return insertable;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isUpdateable() {
		return updateable;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isPartitioned() {
		return partitioned;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public @Nullable String getCustomReadExpression() {
		return customReadExpression;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public @Nullable String getCustomWriteExpression() {
		return customWriteExpression;
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
	public JdbcMapping getJdbcMapping() {
		return jdbcMapping();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JavaType<?> getJavaType() {
		return jdbcMapping().getMappedJavaType();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getPartName() {
		return ROLE_NAME;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public NavigableRole getNavigableRole() {
		return navigableRole;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JdbcMapping getUnderlyingJdbcMapping() {
		return underlyingJdbcMapping;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Object disassemble(Object value, SharedSessionContractImplementor session) {
		return underlyingJdbcMapping.disassemble( value, session, value );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void addToCacheKey(MutableCacheKeyBuilder cacheKey, Object value, SharedSessionContractImplementor session) {
		cacheKey.addValue( underlyingJdbcMapping.disassemble( value, session, value ) );
		cacheKey.addHashCode( underlyingJdbcMapping.getHashCode( value ) );
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
		throw new UnsupportedOperationException();
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
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public EntityMappingType findContainingEntityMapping() {
		return declaringType.findContainingEntityMapping();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public MappingType getMappedType() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getFetchableName() {
		return getPartName();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getFetchableKey() {
		return 0;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public FetchOptions getMappedFetchOptions() {
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int forEachJdbcType(int offset, IndexedConsumer<JdbcMapping> action) {
		action.accept( offset, jdbcMapping() );
		return getJdbcTypeCount();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public int forEachSelectable(SelectableConsumer consumer) {
		return forEachSelectable( 0, consumer );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int forEachSelectable(int offset, SelectableConsumer consumer) {
		consumer.accept( offset, this );
		return 1;
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
		final var sqlAstCreationState = creationState.getSqlAstCreationState();
		final var fromClauseAccess = sqlAstCreationState.getFromClauseAccess();
		final var sqlExpressionResolver = sqlAstCreationState.getSqlExpressionResolver();

		final var tableGroup = fromClauseAccess.getTableGroup( fetchablePath.getParent().getParent() );
		final var tableReference = tableGroup.resolveTableReference( fetchablePath, table );
		final var columnReference = sqlExpressionResolver.resolveSqlExpression(
				tableReference,
				this
		);
		final var sqlSelection = sqlExpressionResolver.resolveSqlSelection(
				columnReference,
				jdbcMapping().getJdbcJavaType(),
				fetchParent,
				sqlAstCreationState.getCreationContext().getTypeConfiguration()
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
	public FetchStyle getStyle() {
		return FetchStyle.JOIN;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public FetchTiming getTiming() {
		return FetchTiming.IMMEDIATE;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <T> DomainResult<T> createDomainResult(
			NavigablePath navigablePath,
			TableGroup tableGroup,
			String resultVariable,
			DomainResultCreationState creationState) {
		final var sqlSelection = resolveSqlSelection( navigablePath, tableGroup, creationState );
		return new BasicResult<>(
				sqlSelection.getValuesArrayPosition(),
				resultVariable,
				jdbcMapping(),
				navigablePath,
				false,
				!sqlSelection.isVirtual()
		);
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Expression resolveSqlExpression(
			NavigablePath navigablePath,
			JdbcMapping jdbcMappingToUse,
			TableGroup tableGroup,
			SqlAstCreationState creationState) {
		final var tableReference =
				tableGroup.resolveTableReference( navigablePath, this,
						getContainingTableExpression() );
		return creationState.getSqlExpressionResolver()
				.resolveSqlExpression( tableReference, this );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void applySqlSelections(
			NavigablePath navigablePath,
			TableGroup tableGroup,
			DomainResultCreationState creationState) {
		resolveSqlSelection( navigablePath, tableGroup, creationState );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void applySqlSelections(
			NavigablePath navigablePath,
			TableGroup tableGroup,
			DomainResultCreationState creationState,
			BiConsumer<SqlSelection, JdbcMapping> selectionConsumer) {
		selectionConsumer.accept( resolveSqlSelection( navigablePath, tableGroup, creationState ), getJdbcMapping() );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	private SqlSelection resolveSqlSelection(
			NavigablePath navigablePath,
			TableGroup tableGroup,
			DomainResultCreationState creationState) {
		final var sqlAstCreationState = creationState.getSqlAstCreationState();
		return sqlAstCreationState.getSqlExpressionResolver().resolveSqlSelection(
				resolveSqlExpression( navigablePath, null, tableGroup, sqlAstCreationState ),
				jdbcMapping().getJdbcJavaType(),
				null,
				sqlAstCreationState.getCreationContext().getTypeConfiguration()
		);
	}
}
