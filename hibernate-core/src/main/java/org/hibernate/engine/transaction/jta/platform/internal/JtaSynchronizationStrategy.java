/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.transaction.jta.platform.internal;

import java.io.Serializable;
import jakarta.transaction.Synchronization;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Contract used to centralize {@link Synchronization} handling logic.
 *
 * @author Steve Ebersole
 */
public interface JtaSynchronizationStrategy extends Serializable {
	/**
	 * Register a synchronization
	 *
	 * @param synchronization The synchronization to register.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void registerSynchronization(Synchronization synchronization);

	/**
	 * Can a synchronization be registered?
	 *
	 * @return {@literal true}/{@literal false}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean canRegisterSynchronization();
}
