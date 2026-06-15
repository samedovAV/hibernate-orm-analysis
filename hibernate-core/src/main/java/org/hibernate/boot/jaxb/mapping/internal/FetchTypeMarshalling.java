/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.mapping.internal;

import jakarta.persistence.FetchType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * JAXB marshalling for {@link FetchType}
 *
 * @author Steve Ebersole
 */
public class FetchTypeMarshalling {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static FetchType fromXml(String name) {
		final FetchType result = name == null ? null : FetchType.valueOf( name );
		assert result != FetchType.DEFAULT;
		return result;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static String toXml(FetchType fetchType) {
		return fetchType == null ? null : fetchType.name();
	}
}
