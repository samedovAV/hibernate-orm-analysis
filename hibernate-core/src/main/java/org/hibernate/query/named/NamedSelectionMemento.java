/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.named;

import jakarta.persistence.PessimisticLockScope;
import jakarta.persistence.Timeout;
import org.hibernate.CacheMode;
import org.hibernate.LockMode;
import org.hibernate.Locking;
import org.hibernate.query.spi.JpaTypedQueryReference;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/// Models NamedQueryMemento which is a selection-query
///
/// @author Steve Ebersole
public interface NamedSelectionMemento<T> extends NamedQueryMemento<T>, JpaTypedQueryReference<T> {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getSelectionString();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Boolean getReadOnly();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Integer getFetchSize();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Integer getFirstResult();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Integer getMaxResults();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Boolean getCacheable();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getCacheRegion();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CacheMode getCacheMode();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	LockMode getHibernateLockMode();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	PessimisticLockScope getPessimisticLockScope();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Timeout getLockTimeout();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Locking.FollowOn getFollowOnLockingStrategy();
}
