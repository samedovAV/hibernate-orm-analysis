/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.usertype.internal;

import java.io.Serializable;

import org.hibernate.usertype.CompositeUserType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Christian Beikov
 */
public abstract class AbstractTimeZoneStorageCompositeUserType<T> implements CompositeUserType<T> {

	public static final String INSTANT_NAME = "instant";
	public static final String ZONE_OFFSET_NAME = "zoneOffset";

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean equals(T x, T y) {
		return x.equals( y );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public int hashCode(T x) {
		return x.hashCode();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public T deepCopy(T value) {
		return value;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isMutable() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Serializable disassemble(T value) {
		return (Serializable) value;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public T assemble(Serializable cached, Object owner) {
		//noinspection unchecked
		return (T) cached;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public T replace(T detached, T managed, Object owner) {
		return detached;
	}

}
