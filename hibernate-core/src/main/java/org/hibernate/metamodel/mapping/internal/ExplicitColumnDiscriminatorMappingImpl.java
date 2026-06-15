/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.mapping.internal;

import jakarta.annotation.Nullable;
import org.hibernate.metamodel.mapping.DiscriminatorConverter;
import org.hibernate.metamodel.mapping.DiscriminatorType;
import org.hibernate.metamodel.mapping.EmbeddableDiscriminatorMapping;
import org.hibernate.metamodel.mapping.JdbcMapping;
import org.hibernate.metamodel.mapping.ManagedMappingType;
import org.hibernate.spi.NavigablePath;
import org.hibernate.sql.ast.spi.SqlAstCreationState;
import org.hibernate.sql.ast.tree.expression.ColumnReference;
import org.hibernate.sql.ast.tree.expression.Expression;
import org.hibernate.sql.ast.tree.from.TableGroup;
import org.hibernate.type.BasicType;

import static org.hibernate.sql.ast.spi.SqlExpressionResolver.createColumnReferenceKey;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class ExplicitColumnDiscriminatorMappingImpl extends AbstractDiscriminatorMapping
		implements EmbeddableDiscriminatorMapping {
	private final String name;
	private final String tableExpression;
	private final String columnName;
	private final String columnFormula;
	private final boolean isPhysical;
	private final boolean isUpdateable;
	private final @Nullable String customReadExpression;
	private final @Nullable Long length;
	private final @Nullable Integer arrayLength;
	private final @Nullable Integer precision;
	private final @Nullable Integer scale;

	public ExplicitColumnDiscriminatorMappingImpl(
			ManagedMappingType mappingType,
			String name,
			String tableExpression,
			String columnExpression,
			boolean isFormula,
			boolean isPhysical,
			boolean isUpdateable,
			String customReadExpression,
			Long length,
			Integer precision,
			Integer scale,
			DiscriminatorType<?> discriminatorType) {
		this(
				mappingType,
				name,
				tableExpression,
					columnExpression,
					isFormula,
					isPhysical,
					isUpdateable,
					customReadExpression,
					length,
					null,
				precision,
				scale,
				discriminatorType );
	}

	public ExplicitColumnDiscriminatorMappingImpl(
			ManagedMappingType mappingType,
			String name,
			String tableExpression,
			String columnExpression,
			boolean isFormula,
			boolean isPhysical,
			boolean isUpdateable,
			@Nullable String customReadExpression,
			@Nullable Long length,
			@Nullable Integer arrayLength,
			@Nullable Integer precision,
			@Nullable Integer scale,
			DiscriminatorType<?> discriminatorType) {
		//noinspection unchecked
		super( mappingType,
				(DiscriminatorType<Object>) discriminatorType,
				(BasicType<Object>) discriminatorType.getUnderlyingJdbcMapping() );
		this.name = name;
		this.tableExpression = tableExpression;
		this.isPhysical = isPhysical;
		this.customReadExpression = customReadExpression;
		this.length = length;
		this.arrayLength = arrayLength;
		this.precision = precision;
		this.scale = scale;
		if ( isFormula ) {
			columnName = null;
			columnFormula = columnExpression;
			this.isUpdateable = false;
		}
		else {
			columnName = columnExpression;
			columnFormula = null;
			this.isUpdateable = isUpdateable;
		}
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public DiscriminatorType<?> getMappedType() {
		return (DiscriminatorType<?>) super.getMappedType();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public DiscriminatorConverter<?, ?> getValueConverter() {
		return getMappedType().getValueConverter();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Expression resolveSqlExpression(
			NavigablePath navigablePath,
			JdbcMapping jdbcMappingToUse,
			TableGroup tableGroup,
			SqlAstCreationState creationState) {
		final var tableReference = tableGroup.resolveTableReference( navigablePath, tableExpression );
		return creationState.getSqlExpressionResolver()
				.resolveSqlExpression(
						createColumnReferenceKey( tableGroup.getPrimaryTableReference(),
								getSelectionExpression(), jdbcMappingToUse ),
						processingState -> new ColumnReference( tableReference, this )
				);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getContainingTableExpression() {
		return tableExpression;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getSelectableName() {
		return name;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getSelectionExpression() {
		return columnName == null ? columnFormula : columnName;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public @Nullable String getCustomReadExpression() {
		return customReadExpression;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public @Nullable String getCustomWriteExpression() {
		return null;
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
	public @Nullable Integer getScale() {
		return scale;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public @Nullable Integer getTemporalPrecision() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isFormula() {
		return columnFormula != null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isNullable() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isInsertable() {
		return isPhysical;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isUpdateable() {
		return isUpdateable;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isPartitioned() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean hasPartitionedSelectionMapping() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean hasPhysicalColumn() {
		return isPhysical;
	}
}
