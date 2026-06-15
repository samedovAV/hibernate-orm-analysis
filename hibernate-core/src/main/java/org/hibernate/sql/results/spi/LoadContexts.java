/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.results.spi;

import org.hibernate.engine.spi.CollectionKey;
import org.hibernate.engine.spi.PersistenceContext;
import org.hibernate.internal.util.collections.StandardStack;
import org.hibernate.sql.results.graph.collection.LoadingCollectionEntry;
import org.hibernate.sql.results.jdbc.spi.JdbcValuesSourceProcessingState;

import static org.hibernate.internal.CoreMessageLogger.CORE_LOGGER;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Maintains a Stack of processing state related to performing load operations.
 * The state is defined by {@link JdbcValuesSourceProcessingState} which
 * encapsulates the data to be processed by the load whether the data comes from
 * a ResultSet or second-level cache hit.
 *
 * @author Steve Ebersole
 */
public class LoadContexts {

	private final PersistenceContext persistenceContext;
	private final StandardStack<JdbcValuesSourceProcessingState> jdbcValuesSourceProcessingStateStack = new StandardStack<>();

	public LoadContexts(PersistenceContext persistenceContext) {
		this.persistenceContext = persistenceContext;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void register(JdbcValuesSourceProcessingState state) {
		jdbcValuesSourceProcessingStateStack.push( state );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void deregister(JdbcValuesSourceProcessingState state) {
		final var previous = jdbcValuesSourceProcessingStateStack.pop();
		if ( previous != state ) {
			throw new IllegalStateException( "Illegal pop() with non-matching JdbcValuesSourceProcessingState" );
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isLoadingFinished() {
		return jdbcValuesSourceProcessingStateStack.getRoot() == null;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public LoadingCollectionEntry findLoadingCollectionEntry(final CollectionKey collectionKey) {
		return jdbcValuesSourceProcessingStateStack.findCurrentFirstWithParameter( collectionKey, JdbcValuesSourceProcessingState::findLoadingCollectionLocally );
	}

	/**
	 * Retrieves the persistence context to which this is bound.
	 *
	 * @return The persistence context to which this is bound.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PersistenceContext getPersistenceContext() {
		return persistenceContext;
	}

	/**
	 * Release internal state associated with *all* result sets.
	 * <p>
	 * This is intended as a "failsafe" process to make sure we get everything
	 * cleaned up and released.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void cleanup() {
		if ( ! jdbcValuesSourceProcessingStateStack.isEmpty() ) {
			CORE_LOGGER.debug( "LoadContexts still contained JdbcValuesSourceProcessingState registrations on cleanup" );
		}
		jdbcValuesSourceProcessingStateStack.clear();
	}

}
