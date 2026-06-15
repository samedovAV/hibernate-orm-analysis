/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.stat.internal;

import java.util.Set;

import org.hibernate.engine.spi.PersistenceContext;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.stat.SessionStatistics;

import static java.util.Collections.unmodifiableSet;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Gavin King
 */
public class SessionStatisticsImpl implements SessionStatistics {

	private final PersistenceContext persistenceContext;

	public SessionStatisticsImpl(SessionImplementor session) {
		persistenceContext = session.getPersistenceContextInternal();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getEntityCount() {
		return persistenceContext.getNumberOfManagedEntities();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getCollectionCount() {
		return persistenceContext.getCollectionEntriesSize();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Set<?> getEntityKeys() {
		return unmodifiableSet( persistenceContext.getEntitiesByKey().keySet() );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Set<?> getCollectionKeys() {
		return unmodifiableSet( persistenceContext.getCollectionsByKey().keySet() );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String toString() {
		return "SessionStatistics["
			+ "entity count=" + getEntityCount()
			+ ",collection count=" + getCollectionCount()
			+ ']';
	}

}
