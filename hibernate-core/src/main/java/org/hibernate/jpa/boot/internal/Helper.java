/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.jpa.boot.internal;

import jakarta.persistence.PersistenceException;

import org.hibernate.jpa.boot.spi.PersistenceUnitDescriptor;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class Helper {

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public static PersistenceException persistenceException(
			PersistenceUnitDescriptor persistenceUnit,
			String message) {
		return persistenceException( persistenceUnit, message, null );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static PersistenceException persistenceException(
			PersistenceUnitDescriptor persistenceUnit,
			String message,
			Exception cause) {
		return new PersistenceException(
				getExceptionHeader( persistenceUnit ) + message,
				cause
		);
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private static String getExceptionHeader(PersistenceUnitDescriptor persistenceUnit) {
		return "[PersistenceUnit: " + persistenceUnit.getName() + "] ";
	}
}
