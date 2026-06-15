/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.resource.transaction.spi;

import org.hibernate.HibernateException;
import org.hibernate.jdbc.WorkExecutorVisitable;

import java.util.concurrent.Callable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Contract for performing work in a manner that isolates it from any current transaction.
 *
 * @author Steve Ebersole
 */
public interface IsolationDelegate {
	/**
	 * Perform the given work in isolation from current transaction.
	 *
	 * @param work The work to be performed.
	 * @param transacted Should the work itself be done in a (isolated) transaction?
	 *
	 * @return The work result
	 *
	 * @throws HibernateException Indicates a problem performing the work.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> T delegateWork(WorkExecutorVisitable<T> work, boolean transacted) throws HibernateException;

	/**
	 * Invoke the given callable in isolation from current transaction.
	 *
	 * @param callable The callable to be invoked.
	 * @param transacted Should the work itself be done in a (isolated) transaction?
	 *
	 * @return The work result
	 *
	 * @throws HibernateException Indicates a problem performing the work.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> T delegateCallable(Callable<T> callable, boolean transacted) throws HibernateException;
}
