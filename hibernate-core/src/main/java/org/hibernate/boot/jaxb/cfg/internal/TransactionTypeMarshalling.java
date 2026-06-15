/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.cfg.internal;

import org.hibernate.internal.util.StringHelper;

import jakarta.persistence.PersistenceUnitTransactionType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * JAXB marshal/unmarshal support for {@linkplain PersistenceUnitTransactionType}.
 *
 * @author Steve Ebersole
 */
public class TransactionTypeMarshalling {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static PersistenceUnitTransactionType fromXml(String name) {
		if ( StringHelper.isEmpty( name ) ) {
			return PersistenceUnitTransactionType.RESOURCE_LOCAL;
		}
		return PersistenceUnitTransactionType.valueOf( name );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static String toXml(PersistenceUnitTransactionType transactionType) {
		return transactionType == null ? null : transactionType.name();
	}
}
