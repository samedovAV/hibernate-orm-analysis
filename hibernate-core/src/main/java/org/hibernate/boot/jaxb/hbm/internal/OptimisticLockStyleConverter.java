/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.hbm.internal;

import java.util.Locale;

import org.hibernate.engine.OptimisticLockStyle;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Handles conversion to/from Hibernate's OptimisticLockStyle enum during
 * JAXB processing.
 *
 * @author Steve Ebersole
 */
public class OptimisticLockStyleConverter {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static OptimisticLockStyle fromXml(String name) {
		return OptimisticLockStyle.valueOf( name == null ? null : name.toUpperCase( Locale.ENGLISH ) );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static String toXml(OptimisticLockStyle lockMode) {
		return lockMode == null ? null : lockMode.name().toLowerCase( Locale.ENGLISH );
	}
}
