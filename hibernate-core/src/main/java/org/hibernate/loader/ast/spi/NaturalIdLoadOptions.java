/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.loader.ast.spi;

import org.hibernate.LockOptions;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Options for loading by natural-id
 */
public interface NaturalIdLoadOptions {
	/**
	 * Singleton access
	 */
	NaturalIdLoadOptions NONE = new NaturalIdLoadOptions() {
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public LockOptions getLockOptions() {
			return null;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public boolean isSynchronizationEnabled() {
			return false;
		}
	};

	/**
	 * The locking options for the loaded entity
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	LockOptions getLockOptions();

	/**
	 * Whether Hibernate should perform "synchronization" prior to performing
	 * look-ups?
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isSynchronizationEnabled();
}
