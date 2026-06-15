/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.mapping.internal;

import jakarta.persistence.LockModeType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * JAXB marshalling for {@link LockModeType}
 *
 * @author Steve Ebersole
 */
public class LockModeTypeMarshalling {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static LockModeType fromXml(String name) {
		return name == null ? null : LockModeType.valueOf( name );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static String toXml(LockModeType lockModeType) {
		return lockModeType == null ? null : lockModeType.name();
	}
}
