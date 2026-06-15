/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.mapping;

import jakarta.annotation.Nullable;
import org.hibernate.engine.jdbc.Size;

import java.util.function.BiConsumer;
import java.util.function.IntFunction;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Consumer used to visit selectable (column/formula) mappings
 *
 * @author Steve Ebersole
 */
@FunctionalInterface
public interface SelectableConsumer {
	/**
	 * Accept the selectable mapping.  `selectIndex` is its position,
	 * the meaning of which depends on the impl and whether
	 * {@link SelectableMappings#forEachSelectable(SelectableConsumer)} or
	 * {@link SelectableMappings#forEachSelectable(int, SelectableConsumer)}
	 * was used
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void accept(int selectionIndex, SelectableMapping selectableMapping);

	/**
	 * Simple form of visitation over a number of columns by name, using
	 * a separate {@link JdbcMappingContainer} as a base for additional details.
	 * <p>
	 * Intended for use in visiting table keys, where we know JdbcMappings, etc.
	 * from the identifier.
	 * <p>
	 * The expectation here is for the following details to be available:<ul>
	 *     <li>{@link SelectableMapping#getContainingTableExpression()}</li>
	 *     <li>{@link SelectableMapping#getSelectionExpression()} (the column name)</li>
	 *     <li>{@link SelectableMapping#getWriteExpression()}</li>
	 *     <li>{@link SelectableMapping#getJdbcMapping()}</li>
	 * </ul>
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void accept(String tableName, JdbcMappingContainer base, String[] columnNames) {
		assert base.getJdbcTypeCount() == columnNames.length;

		final MutableSelectableMapping mutableSelectableMapping = new MutableSelectableMapping( tableName, base, columnNames );
		mutableSelectableMapping.forEach( this::accept );
	}

	/**
	 * Simple form of visitation over a number of columns by name, using
	 * a separate {@link SelectableMappings} as a base for additional details.
	 * <p>
	 * Intended for use in visiting table keys, where we know JdbcMappings, etc.
	 * from the identifier.
	 * <p>
	 * The expectation here is for the following details to be available:<ul>
	 *     <li>{@link SelectableMapping#getContainingTableExpression()}</li>
	 *     <li>{@link SelectableMapping#getSelectionExpression()} (the column name)</li>
	 *     <li>{@link SelectableMapping#getWriteExpression()}</li>
	 *     <li>{@link SelectableMapping#getJdbcMapping()}</li>
	 * </ul>
	 */
	@Prove(complexity = Complexity.O_N2, n = "", count = {})
	default void accept(SelectableMappings base, String tableName, String[] columnNames) {
		class SelectableMappingIterator implements SelectableMapping {
			private final String tableName;
			private final SelectableMappings delegate;
			private final String[] columnNames;

			private int index;

			public SelectableMappingIterator(String tableName, SelectableMappings delegate, String[] columnNames) {
				this.tableName = tableName;
				this.delegate = delegate;
				this.columnNames = columnNames;
				assert delegate.getJdbcTypeCount() == columnNames.length;
			}

			@Prove(complexity = Complexity.O_N, n = "", count = {})
			private void forEach(BiConsumer<Integer,SelectableMapping> consumer) {
				for ( index = 0; index < columnNames.length; index++ ) {
					consumer.accept( index, this );
				}
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public String getContainingTableExpression() {
				return tableName;
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public String getSelectionExpression() {
				return columnNames[index];
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public @Nullable String getCustomReadExpression() {
				return null;
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public @Nullable String getCustomWriteExpression() {
				return null;
			}

			@Prove(complexity = Complexity.O_1, n = "", count = {})
			private SelectableMapping getDelegate() {
				return delegate.getSelectable( index );
			}

			@Override
			@Prove(complexity = Complexity.O_N, n = "", count = {})
			public String getSelectableName() {
				return getDelegate().getSelectableName();
			}

			@Override
			@Prove(complexity = Complexity.O_N, n = "", count = {})
			public SelectablePath getSelectablePath() {
				return getDelegate().getSelectablePath();
			}

			@Override
			@Prove(complexity = Complexity.O_N, n = "", count = {})
			public boolean isFormula() {
				return getDelegate().isFormula();
			}

			@Override
			@Prove(complexity = Complexity.O_N, n = "", count = {})
			public boolean isNullable() {
				return getDelegate().isNullable();
			}

			@Override
			@Prove(complexity = Complexity.O_N, n = "", count = {})
			public boolean isInsertable() {
				return getDelegate().isInsertable();
			}

			@Override
			@Prove(complexity = Complexity.O_N, n = "", count = {})
			public boolean isUpdateable() {
				return getDelegate().isUpdateable();
			}

			@Override
			@Prove(complexity = Complexity.O_N, n = "", count = {})
			public boolean isPartitioned() {
				return getDelegate().isPartitioned();
			}

			@Override
			@Prove(complexity = Complexity.O_N, n = "", count = {})
			public @Nullable Long getLength() {
				return getDelegate().getLength();
			}

			@Override
			@Prove(complexity = Complexity.O_N, n = "", count = {})
			public @Nullable Integer getArrayLength() {
				return getDelegate().getArrayLength();
			}

			@Override
			@Prove(complexity = Complexity.O_N, n = "", count = {})
			public @Nullable Integer getPrecision() {
				return getDelegate().getPrecision();
			}

			@Override
			@Prove(complexity = Complexity.O_N, n = "", count = {})
			public @Nullable Integer getScale() {
				return getDelegate().getScale();
			}

			@Override
			@Prove(complexity = Complexity.O_N, n = "", count = {})
			public @Nullable Integer getTemporalPrecision() {
				return getDelegate().getTemporalPrecision();
			}

			@Override
			@Prove(complexity = Complexity.O_N, n = "", count = {})
			public boolean isLob() {
				return getDelegate().isLob();
			}

			@Override
			@Prove(complexity = Complexity.O_N, n = "", count = {})
			public JdbcMapping getJdbcMapping() {
				return getDelegate().getJdbcMapping();
			}

			@Override
			@Prove(complexity = Complexity.O_N, n = "", count = {})
			public Size toSize() {
				return getDelegate().toSize();
			}
		}

		final SelectableMappingIterator mutableSelectableMapping = new SelectableMappingIterator( tableName, base, columnNames );
		mutableSelectableMapping.forEach( this::accept );
	}

	class MutableSelectableMapping implements SelectableMapping {
		private final String tableName;
		private final JdbcMappingContainer base;
		private final String[] columnNames;

		private int index;

		public MutableSelectableMapping(String tableName, JdbcMappingContainer base, String[] columnNames) {
			this.tableName = tableName;
			this.base = base;
			this.columnNames = columnNames;

			assert base.getJdbcTypeCount() == columnNames.length;
		}

		@Prove(complexity = Complexity.O_N, n = "", count = {})
		private void forEach(BiConsumer<Integer,SelectableMapping> consumer) {
			for ( index = 0; index < columnNames.length; index++ ) {
				consumer.accept( index, this );
			}
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public String getContainingTableExpression() {
			return tableName;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public String getSelectionExpression() {
			return columnNames[index];
		}

		@Override
		@Prove(complexity = Complexity.O_N, n = "", count = {})
		public JdbcMapping getJdbcMapping() {
			return base.getJdbcMapping( index );
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
			// we insert keys
			return true;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public boolean isUpdateable() {
			// we never update keys
			return false;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public boolean isPartitioned() {
			return false;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public @Nullable Long getLength() {
			// we could probably use the details from `base`, but
			// this method should really never be called on this object
			throw new UnsupportedOperationException();
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public @Nullable Integer getArrayLength() {
			// we could probably use the details from `base`, but
			// this method should really never be called on this object
			throw new UnsupportedOperationException();
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public @Nullable Integer getPrecision() {
			// we could probably use the details from `base`, but
			// this method should really never be called on this object
			return null;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public @Nullable Integer getScale() {
			// we could probably use the details from `base`, but
			// this method should really never be called on this object
			return null;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public @Nullable Integer getTemporalPrecision() {
			// we could probably use the details from `base`, but
			// this method should really never be called on this object
			return null;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public @Nullable String getCustomReadExpression() {
			return null;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public @Nullable String getCustomWriteExpression() {
			return null;
		}
	}



	/**
	 * Simple form allowing visitation over a number of column names within a
	 * table.
	 *
	 * Very limited functionality in terms of the visited SelectableMappings
	 * will not have any defined JdbcMapping, etc
	 */
	@Prove(complexity = Complexity.O_N2, n = "", count = {})
	default void accept(String tableName, String[] columnNames, IntFunction<JdbcMapping> jdbcMappings) {
		class SelectableMappingIterator implements SelectableMapping {

			private int index;

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public String getContainingTableExpression() {
				return tableName;
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public String getSelectionExpression() {
				return columnNames[index];
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public @Nullable String getCustomReadExpression() {
				return null;
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public @Nullable String getCustomWriteExpression() {
				return null;
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public @Nullable Long getLength() {
				return null;
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public @Nullable Integer getArrayLength() {
				return null;
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public @Nullable Integer getPrecision() {
				return null;
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public @Nullable Integer getScale() {
				return null;
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public @Nullable Integer getTemporalPrecision() {
				return null;
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public boolean isFormula() {
				return false;
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public boolean isNullable() {
				return true;
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public boolean isInsertable() {
				return true;
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public boolean isUpdateable() {
				return true;
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public boolean isPartitioned() {
				return false;
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public JdbcMapping getJdbcMapping() {
				return jdbcMappings.apply( index );
			}
		}
		for (
				SelectableMappingIterator iterator = new SelectableMappingIterator();
				iterator.index < columnNames.length;
				iterator.index++
		) {
			accept( iterator.index, iterator );
		}
	}
}
