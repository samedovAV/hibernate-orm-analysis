/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.mapping.internal;

import java.util.Locale;

import jakarta.annotation.Nullable;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.Selectable;
import org.hibernate.metamodel.mapping.JdbcMapping;
import org.hibernate.metamodel.mapping.SelectableMapping;
import org.hibernate.metamodel.mapping.SelectablePath;
import org.hibernate.metamodel.spi.RuntimeModelCreationContext;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Christian Beikov
 */
public class SelectableMappingImpl extends SqlTypedMappingImpl implements SelectableMapping {

	private final String containingTableExpression;
	private final String selectionExpression;
	private final SelectablePath selectablePath;
	private final @Nullable String customReadExpression;
	private final @Nullable String customWriteExpression;
	private final boolean isLob;
	private final boolean nullable;
	private final boolean insertable;
	private final boolean updateable;
	private final boolean partitioned;
	private final boolean isFormula;

	public SelectableMappingImpl(
			String containingTableExpression,
			String selectionExpression,
			@Nullable SelectablePath selectablePath,
			@Nullable String customReadExpression,
			@Nullable String customWriteExpression,
			@Nullable Long length,
			@Nullable Integer precision,
			@Nullable Integer scale,
			@Nullable Integer temporalPrecision,
			boolean isLob,
			boolean nullable,
			boolean insertable,
			boolean updateable,
			boolean partitioned,
			boolean isFormula,
			JdbcMapping jdbcMapping) {
		this(
				containingTableExpression,
				selectionExpression,
				selectablePath,
				customReadExpression,
				customWriteExpression,
				length,
				null,
				precision,
				scale,
				temporalPrecision,
				isLob,
				nullable,
				insertable,
				updateable,
				partitioned,
				isFormula,
				jdbcMapping
		);
	}

	public SelectableMappingImpl(
			String containingTableExpression,
			String selectionExpression,
			@Nullable SelectablePath selectablePath,
			@Nullable String customReadExpression,
			@Nullable String customWriteExpression,
			@Nullable Long length,
			@Nullable Integer arrayLength,
			@Nullable Integer precision,
			@Nullable Integer scale,
			@Nullable Integer temporalPrecision,
			boolean isLob,
			boolean nullable,
			boolean insertable,
			boolean updateable,
			boolean partitioned,
			boolean isFormula,
			JdbcMapping jdbcMapping) {
		super( length, arrayLength, precision, scale, temporalPrecision, jdbcMapping );
		assert selectionExpression != null;
		// Save memory by using interned strings. Probability is high that we have multiple duplicate strings
		this.containingTableExpression = containingTableExpression.intern();
		this.selectionExpression = selectionExpression.intern();
		this.selectablePath = selectablePath == null ? new SelectablePath( selectionExpression ) : selectablePath;
		this.customReadExpression = customReadExpression == null ? null : customReadExpression.intern();
		this.customWriteExpression = customWriteExpression == null || isFormula ? null : customWriteExpression.intern();
		this.isLob = isLob;
		this.nullable = nullable;
		this.insertable = insertable;
		this.updateable = updateable;
		this.partitioned = partitioned;
		this.isFormula = isFormula;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static SelectableMapping from(
			final String containingTableExpression,
			final Selectable selectable,
			// The path to the property for this selectable mapping, which is used as selectable name
			// if the selectable is a formula. If it's a formula, the value should be non-null
			@Nullable final String propertyPath,
			@Nullable final SelectablePath parentPath,
			final JdbcMapping jdbcMapping,
			boolean insertable,
			boolean updateable,
			boolean partitioned,
			boolean forceNotNullable,
			RuntimeModelCreationContext creationContext) {
		final var dialect = creationContext.getDialect();
		final String columnExpression;
		final Long length;
		final Integer arrayLength;
		final Integer precision;
		final Integer scale;
		final Integer temporalPrecision;
		final SelectablePath selectablePath;
		final boolean isLob;
		final boolean isNullable;
		if ( selectable.isFormula() ) {
			columnExpression =
					selectable.getTemplate( dialect,
							creationContext.getTypeConfiguration() );
			length = null;
			arrayLength = null;
			precision = null;
			scale = null;
			temporalPrecision = null;
			isNullable = true;
			isLob = false;
			assert propertyPath != null : "Property path must be non-null for formulas";
			selectablePath = new SelectablePath( propertyPath );
		}
		else {
			var column = (Column) selectable;
			columnExpression = selectable.getText( dialect );
			length = column.getLength();
			arrayLength = column.getArrayLength();
			precision = column.getPrecision();
			scale = column.getScale();
			temporalPrecision = column.getTemporalPrecision();

			isNullable = !forceNotNullable && column.isNullable();
			isLob = column.isSqlTypeLob( creationContext.getMetadata() );
			selectablePath = parentPath == null
					? null
					: parentPath.append( column.getQuotedName( dialect ) );
		}
		return new SelectableMappingImpl(
				containingTableExpression,
				columnExpression,
				selectablePath,
				selectable.getCustomReadExpression(),
				selectable.getWriteExpr( jdbcMapping, dialect,
						creationContext.getBootModel() ),
				length,
				arrayLength,
				precision,
				scale,
				temporalPrecision,
				isLob,
				isNullable,
				insertable,
				updateable,
				partitioned,
				selectable.isFormula(),
				jdbcMapping
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String toString() {
		return String.format(
				Locale.ROOT,
				"SelectableMapping(`%s`.`%s`)",
				containingTableExpression,
				selectionExpression
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getContainingTableExpression() {
		return containingTableExpression;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getSelectionExpression() {
		return selectionExpression;
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
	public boolean isLob() {
		return isLob;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isFormula() {
		return isFormula;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isNullable() {
		return nullable;
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
}
