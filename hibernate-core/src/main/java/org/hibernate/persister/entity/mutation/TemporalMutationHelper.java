/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.persister.entity.mutation;

import org.hibernate.engine.spi.SharedSessionContractImplementor;

import static org.hibernate.temporal.TemporalTableStrategy.NATIVE;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

public class TemporalMutationHelper {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static boolean isUsingParameters(SharedSessionContractImplementor session) {
		final var factory = session.getFactory();
		return factory.getSessionFactoryOptions().getTemporalTableStrategy() != NATIVE
			&& !factory.getChangesetCoordinator().useServerTimestamp( session.getDialect() );
	}
}
