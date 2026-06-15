/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.query;

import jakarta.persistence.PessimisticLockScope;
import jakarta.persistence.Timeout;
import jakarta.annotation.Nullable;
import org.hibernate.CacheMode;
import org.hibernate.LockMode;
import org.hibernate.Locking;
import org.hibernate.query.spi.JpaTypedQueryReference;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Boot-model mapping of named queries which define a selection query.
 *
 * @author Steve Ebersole
 */
public interface NamedSelectionDefinition<R>
		extends NamedQueryDefinition<R>, JpaTypedQueryReference<R> {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getQueryString();

	@Override
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default String getName() {
		return getRegistrationName();
	}

	/**
	 * The expected result type of the query, or {@code null}.
	 */
	@Nullable //FIXME: declared @Nonnull by JPA
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Class<R> getResultType();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Boolean getReadOnly();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Boolean getCacheable();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getCacheRegion();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CacheMode getCacheMode();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	LockMode getHibernateLockMode();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	PessimisticLockScope getLockScope();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Timeout getLockTimeout();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Locking.FollowOn getFollowOnLockingStrategy();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Integer getFetchSize();

	@Override
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getEntityGraphName();
}
