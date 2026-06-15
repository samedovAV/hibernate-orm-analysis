/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.mapping.internal;

import jakarta.persistence.PessimisticLockScope;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/// JAXB marshaling for [PessimisticLockScope]
///
/// @author Steve Ebersole
public class PessimisticLockScopeMarshalling {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static PessimisticLockScope fromXml(String name) {
		return name == null ? null : PessimisticLockScope.valueOf( name );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static String toXml(PessimisticLockScope lockScope) {
		return lockScope == null ? null : lockScope.name();
	}
}
