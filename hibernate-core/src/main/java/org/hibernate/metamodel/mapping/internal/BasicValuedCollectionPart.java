/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.mapping.internal;

import java.util.function.BiConsumer;

import jakarta.annotation.Nullable;
import org.hibernate.engine.FetchStyle;
import org.hibernate.engine.FetchTiming;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.internal.util.IndexedConsumer;
import org.hibernate.metamodel.mapping.BasicValuedModelPart;
import org.hibernate.metamodel.mapping.CollectionPart;
import org.hibernate.metamodel.mapping.EntityMappingType;
import org.hibernate.metamodel.mapping.JdbcMapping;
import org.hibernate.metamodel.mapping.MappingType;
import org.hibernate.metamodel.mapping.PluralAttributeMapping;
import org.hibernate.metamodel.mapping.SelectableConsumer;
import org.hibernate.metamodel.mapping.SelectableMapping;
import org.hibernate.metamodel.mapping.SelectablePath;
import org.hibernate.metamodel.model.domain.NavigableRole;
import org.hibernate.persister.collection.CollectionPersister;
import org.hibernate.spi.EntityIdentifierNavigablePath;
import org.hibernate.spi.NavigablePath;
import org.hibernate.sql.ast.spi.SqlSelection;
import org.hibernate.sql.ast.tree.from.PluralTableGroup;
import org.hibernate.sql.ast.tree.from.TableGroup;
import org.hibernate.sql.results.graph.DomainResult;
import org.hibernate.sql.results.graph.DomainResultCreationState;
import org.hibernate.sql.results.graph.Fetch;
import org.hibernate.sql.results.graph.FetchOptions;
import org.hibernate.sql.results.graph.FetchParent;
import org.hibernate.sql.results.graph.basic.BasicFetch;
import org.hibernate.sql.results.graph.basic.BasicResult;
import org.hibernate.type.descriptor.java.JavaType;

import static org.hibernate.sql.results.ResultsLogger.RESULTS_LOGGER;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Models a basic collection element/value or index/key
 *
 * @author Steve Ebersole
 */
public class BasicValuedCollectionPart
		implements CollectionPart, BasicValuedModelPart, FetchOptions {
	private final NavigableRole navigableRole;
	private final CollectionPersister collectionDescriptor;
	private final Nature nature;

	private final SelectableMapping selectableMapping;

	public BasicValuedCollectionPart(
			CollectionPersister collectionDescriptor,
			Nature nature,
			SelectableMapping selectableMapping) {
		this.navigableRole = collectionDescriptor.getNavigableRole().append( nature.getName() );
		this.collectionDescriptor = collectionDescriptor;
		this.nature = nature;
		this.selectableMapping = selectableMapping;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Nature getNature() {
		return nature;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PluralAttributeMapping getCollectionAttribute() {
		return collectionDescriptor.getAttributeMapping();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public MappingType getPartMappingType() {
		return selectableMapping.getJdbcMapping()::getJavaTypeDescriptor;
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
	public SelectablePath getSelectablePath() {
		return selectableMapping.getSelectablePath();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getSelectableName() {
		return selectableMapping.getSelectableName();
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
	public boolean isPartitioned() {
		return selectableMapping.isPartitioned();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isUpdateable() {
		return selectableMapping.isUpdateable();
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
	public @Nullable Integer getTemporalPrecision() {
		return selectableMapping.getTemporalPrecision();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public @Nullable Integer getScale() {
		return selectableMapping.getScale();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JavaType<?> getJavaType() {
		return selectableMapping.getJdbcMapping().getJavaTypeDescriptor();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public NavigableRole getNavigableRole() {
		return navigableRole;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String toString() {
		return "BasicValuedCollectionPart(" + navigableRole + ")@" + System.identityHashCode( this );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <T> DomainResult<T> createDomainResult(
			NavigablePath navigablePath,
			TableGroup tableGroup,
			String resultVariable,
			DomainResultCreationState creationState) {
		final var sqlSelection = resolveSqlSelection( navigablePath, tableGroup, null, creationState );
		return new BasicResult<>(
				sqlSelection.getValuesArrayPosition(),
				resultVariable,
				selectableMapping.getJdbcMapping(),
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
			DomainResultCreationState creationState) {
		final var exprResolver = creationState.getSqlAstCreationState().getSqlExpressionResolver();
		final TableGroup targetTableGroup;
		// If the index is part of the element table group, we must use that explicitly here because the index is basic
		// and thus there is no index table group registered. The logic in the PluralTableGroup prevents from looking
		// into the element table group though because the element table group navigable path is not the parent of this navigable path
		if ( nature == Nature.INDEX
				&& collectionDescriptor.getAttributeMapping().getIndexMetadata().getIndexPropertyName() != null ) {
			targetTableGroup = ( (PluralTableGroup) tableGroup ).getElementTableGroup();
		}
		else {
			targetTableGroup = tableGroup;
		}
		final var tableReference =
				targetTableGroup.resolveTableReference( navigablePath, getContainingTableExpression() );
		return exprResolver.resolveSqlSelection(
				exprResolver.resolveSqlExpression( tableReference, selectableMapping ),
				getJdbcMapping().getJdbcJavaType(),
				fetchParent,
				creationState.getSqlAstCreationState().getCreationContext().getTypeConfiguration()
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void applySqlSelections(
			NavigablePath navigablePath, TableGroup tableGroup, DomainResultCreationState creationState) {
		resolveSqlSelection( navigablePath, tableGroup, null, creationState );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void applySqlSelections(
			NavigablePath navigablePath,
			TableGroup tableGroup,
			DomainResultCreationState creationState,
			BiConsumer<SqlSelection, JdbcMapping> selectionConsumer) {
		selectionConsumer.accept( resolveSqlSelection( navigablePath, tableGroup, null, creationState ), getJdbcMapping() );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public EntityMappingType findContainingEntityMapping() {
		return collectionDescriptor.getAttributeMapping().findContainingEntityMapping();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public JdbcMapping getJdbcMapping() {
		return selectableMapping.getJdbcMapping();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public MappingType getMappedType() {
		return this::getJavaType;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getFetchableName() {
		return nature.getName();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getFetchableKey() {
		return nature == Nature.INDEX || !collectionDescriptor.hasIndex() ? 0 : 1;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public FetchOptions getMappedFetchOptions() {
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Fetch generateFetch(
			FetchParent fetchParent,
			NavigablePath fetchablePath,
			FetchTiming fetchTiming,
			boolean selected,
			String resultVariable,
			DomainResultCreationState creationState) {
		if ( RESULTS_LOGGER.isTraceEnabled() ) {
			RESULTS_LOGGER.tracef(
					"Generating Fetch for collection-part: `%s` -> `%s`",
					collectionDescriptor.getRole(),
					nature.getName()
			);
		}

		NavigablePath parentNavigablePath = fetchablePath.getParent();
		if ( parentNavigablePath instanceof EntityIdentifierNavigablePath ) {
			parentNavigablePath = parentNavigablePath.getParent();
		}

		final var tableGroup =
				creationState.getSqlAstCreationState().getFromClauseAccess()
						.findTableGroup( parentNavigablePath );
		final var sqlSelection = resolveSqlSelection( fetchablePath, tableGroup, fetchParent, creationState );

		return new BasicFetch<>(
				sqlSelection.getValuesArrayPosition(),
				fetchParent,
				fetchablePath,
				this,
				FetchTiming.IMMEDIATE,
				!sqlSelection.isVirtual()
		);
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public JdbcMapping getJdbcMapping(int index) {
		if ( index != 0 ) {
			throw new IndexOutOfBoundsException( index );
		}
		return getJdbcMapping();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JdbcMapping getSingleJdbcMapping() {
		return getJdbcMapping();
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
	public int forEachJdbcType(int offset, IndexedConsumer<JdbcMapping> action) {
		action.accept( offset, selectableMapping.getJdbcMapping() );
		return getJdbcTypeCount();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int forEachSelectable(int offset, SelectableConsumer consumer) {
		consumer.accept( offset, selectableMapping );
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
		valueConsumer.consume( offset, x, y, disassemble( domainValue, session ), this );
		return getJdbcTypeCount();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <X, Y> int decompose(
			Object domainValue, int offset,
			X x,
			Y y,
			JdbcValueBiConsumer<X, Y> valueConsumer,
			SharedSessionContractImplementor session) {
		valueConsumer.consume( offset, x, y, disassemble( domainValue, session ), this );
		return getJdbcTypeCount();
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
}
