/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.mapping;


import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.spi.NavigablePath;
import org.hibernate.sql.ast.tree.from.TableReference;
import org.hibernate.sql.results.graph.DomainResult;
import org.hibernate.sql.results.graph.DomainResultCreationState;

import java.util.List;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Details about a table
 *
 * @author Steve Ebersole
 */
public interface TableDetails {
	/**
	 * The name of the table
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getTableName();

	/**
	 * Details about the primary-key of this table
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	KeyDetails getKeyDetails();

	/**
	 * Whether this table is the root for a given {@link ModelPartContainer}.
	 * <p>
	 * Only relevant for entity-mappings where this indicates whether this
	 * table holds the entity's identifier.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isIdentifierTable();

	/**
	 * Details about the primary key of a table
	 */
	interface KeyDetails extends SelectableMappings {
		/**
		 * Number of columns
		 */
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		int getColumnCount();

		/**
		 * Group of columns defined on the primary key
		 */
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		List<? extends KeyColumn> getKeyColumns();

		/**
		 * Get a key column by relative position
		 */
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		KeyColumn getKeyColumn(int position);


		@FunctionalInterface
		interface KeyValueConsumer {
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			void consume(Object jdbcValue, KeyColumn columnMapping);
		}

		/**
		 * Visit each key column
		 */
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		void forEachKeyColumn(KeyColumnConsumer consumer);

		/**
		 * Break a key value down into its constituent parts, calling the consumer for each.
		 */
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		void breakDownKeyJdbcValues(
				Object domainValue,
				KeyValueConsumer valueConsumer,
				SharedSessionContractImplementor session);

		/**
		 * Create a DomainResult for selecting and retrieving the key.
		 */
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		<K> DomainResult<K> createDomainResult(
				NavigablePath navigablePath,
				TableReference tableReference,
				String resultVariable,
				DomainResultCreationState creationState);
	}

	/**
	 * Details about a column within the key group
	 */
	interface KeyColumn extends SelectableMapping {
		/**
		 * The name of the column
		 */
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		String getColumnName();

		/**
		 * Describes the mapping between object and relational for this column
		 */
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		JdbcMapping getJdbcMapping();
	}

	@FunctionalInterface
	interface KeyColumnConsumer {
		/**
		 * Callback a particular key column
		 *
		 * @param position The position of the column within the key group
		 * @param column The column details
		 */
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		void consume(int position, KeyColumn column);
	}
}
