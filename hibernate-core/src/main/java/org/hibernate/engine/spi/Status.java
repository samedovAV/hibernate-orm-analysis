/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.spi;

import jakarta.annotation.Nullable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Represents the status of an entity with respect to
 * this session. These statuses are for internal
 * bookkeeping only and are not intended to represent
 * any notion that is visible to the application
 * program.
 */
public enum Status {
	MANAGED,
	READ_ONLY,
	DELETED,
	GONE,
	LOADING,
	SAVING;

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isDeletedOrGone() {
		return this == DELETED || this == GONE;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static @Nullable Status fromOrdinal(int ordinal) {
		final Status[] values = values();
		return ordinal < 0 || ordinal >= values.length ? null : values[ordinal];
	}
}
