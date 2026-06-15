/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.hbm.internal;

import org.hibernate.LockMode;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class LockModeConverter {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static LockMode fromXml(String name) {
		return LockMode.fromExternalForm( name );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static String toXml(LockMode lockMode) {
		return lockMode.toExternalForm();
	}
}
