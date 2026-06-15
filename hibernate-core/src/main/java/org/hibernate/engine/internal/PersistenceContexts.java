/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.internal;

import org.hibernate.engine.spi.PersistenceContext;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Operations to instantiate and (de)serialize {@link StatefulPersistenceContext}
 * without exposing the class outside this package.
 *
 * @author Gavin King
 */
public class PersistenceContexts {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static PersistenceContext createPersistenceContext(SharedSessionContractImplementor session) {
		return new StatefulPersistenceContext( session );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public static PersistenceContext deserialize(ObjectInputStream ois, SessionImplementor session)
			throws IOException, ClassNotFoundException {
		return StatefulPersistenceContext.deserialize( ois, session );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public static void serialize(PersistenceContext persistenceContext, ObjectOutputStream oos)
			throws IOException {
		( (StatefulPersistenceContext) persistenceContext ).serialize( oos );
	}
}
