/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.persister.entity;

import java.util.Objects;

import org.hibernate.type.Type;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Useful metadata representing a unique key within a Persister
 */
public final class UniqueKeyEntry {

	private final String uniqueKeyName;
	private final int stateArrayPosition;
	private final Type propertyType;

	public UniqueKeyEntry(final String uniqueKeyName, final int stateArrayPosition, final Type propertyType) {
		this.uniqueKeyName = Objects.requireNonNull( uniqueKeyName );
		this.stateArrayPosition = stateArrayPosition;
		this.propertyType = Objects.requireNonNull( propertyType );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getUniqueKeyName() {
		return this.uniqueKeyName;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getStateArrayPosition() {
		return this.stateArrayPosition;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Type getPropertyType() {
		return this.propertyType;
	}

}
