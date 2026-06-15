/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.mapping.internal;

import java.util.function.BiConsumer;

import org.hibernate.engine.FetchTiming;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.internal.util.IndexedConsumer;
import org.hibernate.metamodel.mapping.DiscriminatorConverter;
import org.hibernate.metamodel.mapping.DiscriminatorType;
import org.hibernate.metamodel.mapping.EntityDiscriminatorMapping;
import org.hibernate.metamodel.mapping.EntityMappingType;
import org.hibernate.metamodel.mapping.JdbcMapping;
import org.hibernate.metamodel.mapping.ManagedMappingType;
import org.hibernate.metamodel.mapping.MappingType;
import org.hibernate.metamodel.model.domain.NavigableRole;
import org.hibernate.spi.NavigablePath;
import org.hibernate.sql.ast.spi.SqlAstCreationState;
import org.hibernate.sql.ast.spi.SqlSelection;
import org.hibernate.sql.ast.tree.from.TableGroup;
import org.hibernate.sql.results.graph.DomainResult;
import org.hibernate.sql.results.graph.DomainResultCreationState;
import org.hibernate.sql.results.graph.FetchParent;
import org.hibernate.sql.results.graph.basic.BasicFetch;
import org.hibernate.sql.results.graph.basic.BasicResult;
import org.hibernate.type.BasicType;
import org.hibernate.type.descriptor.java.JavaType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @implNote `discriminatorType` represents the mapping to Class, whereas `discriminatorType.getUnderlyingType()`
 * represents the "raw" JDBC mapping (String, Integer, etc.)
 *
 * @author Steve Ebersole
 */
public abstract class AbstractDiscriminatorMapping implements EntityDiscriminatorMapping {
	private final NavigableRole role;

	private final BasicType<Object> underlyingJdbcMapping;
	private final DiscriminatorType<Object> discriminatorType;
	private final ManagedMappingType mappingType;

	public AbstractDiscriminatorMapping(
			ManagedMappingType mappingType,
			DiscriminatorType<Object> discriminatorType,
			BasicType<Object> underlyingJdbcMapping) {
		this.underlyingJdbcMapping = underlyingJdbcMapping;
		this.mappingType = mappingType;
		this.discriminatorType = discriminatorType;
		this.role = mappingType.getNavigableRole().append( DISCRIMINATOR_ROLE_NAME );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public EntityMappingType getEntityDescriptor() {
		return mappingType.asEntityMappingType();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public BasicType<?> getUnderlyingJdbcMapping() {
		return discriminatorType.getUnderlyingJdbcMapping();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public DiscriminatorConverter<?, ?> getValueConverter() {
		return discriminatorType.getValueConverter();
	}


	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// EntityDiscriminatorMapping

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public NavigableRole getNavigableRole() {
		return role;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JdbcMapping getJdbcMapping() {
		return discriminatorType;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public EntityMappingType findContainingEntityMapping() {
		return mappingType.findContainingEntityMapping();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public MappingType getMappedType() {
		return getJdbcMapping();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JavaType<?> getJavaType() {
		return getJdbcMapping().getJavaTypeDescriptor();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public DomainResult createDomainResult(
			NavigablePath navigablePath,
			TableGroup tableGroup,
			String resultVariable,
			DomainResultCreationState creationState) {
		// create a SqlSelection based on the underlying JdbcMapping
		final var sqlSelection = resolveSqlSelection(
				navigablePath,
				underlyingJdbcMapping,
				tableGroup,
				null,
				creationState.getSqlAstCreationState()
		);

		// return a BasicResult with conversion the entity class or entity-name
		return new BasicResult(
				sqlSelection.getValuesArrayPosition(),
				resultVariable,
				discriminatorType.getJavaTypeDescriptor(),
				discriminatorType.getValueConverter(),
				navigablePath,
				false,
				!sqlSelection.isVirtual()
		);
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	private SqlSelection resolveSqlSelection(
			NavigablePath navigablePath,
			JdbcMapping jdbcMappingToUse,
			TableGroup tableGroup,
			FetchParent fetchParent,
			SqlAstCreationState creationState) {
		return creationState.getSqlExpressionResolver().resolveSqlSelection(
				resolveSqlExpression( navigablePath, jdbcMappingToUse, tableGroup, creationState ),
				jdbcMappingToUse.getJdbcJavaType(),
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
		final var tableGroup =
				creationState.getSqlAstCreationState().getFromClauseAccess()
						.getTableGroup( fetchParent.getNavigablePath() );
		assert tableGroup != null;

		// create a SqlSelection based on the underlying JdbcMapping
		final var sqlSelection = resolveSqlSelection(
				fetchablePath,
				underlyingJdbcMapping,
				tableGroup,
				fetchParent,
				creationState.getSqlAstCreationState()
		);

		// return a BasicFetch with conversion the entity class or entity-name
		return new BasicFetch<>(
				sqlSelection.getValuesArrayPosition(),
				fetchParent,
				fetchablePath,
				this,
				discriminatorType.getValueConverter(),
				fetchTiming,
				true,
				false,
				!sqlSelection.isVirtual()
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void applySqlSelections(
			NavigablePath navigablePath,
			TableGroup tableGroup,
			DomainResultCreationState creationState) {
		resolveSqlSelection(
				navigablePath,
				underlyingJdbcMapping,
				tableGroup,
				null,
				creationState.getSqlAstCreationState()
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void applySqlSelections(
			NavigablePath navigablePath,
			TableGroup tableGroup,
			DomainResultCreationState creationState,
			BiConsumer<SqlSelection, JdbcMapping> selectionConsumer) {
		selectionConsumer.accept(
				resolveSqlSelection( navigablePath, underlyingJdbcMapping, tableGroup, null, creationState.getSqlAstCreationState() ),
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
		valuesConsumer.consume( offset, x, y, value, underlyingJdbcMapping );
		return getJdbcTypeCount();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int forEachJdbcType(int offset, IndexedConsumer<JdbcMapping> action) {
		action.accept( offset, underlyingJdbcMapping );
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
	public Object disassemble(Object value, SharedSessionContractImplementor session) {
		return value;
	}

}
