/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate;

import java.io.Serializable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Implemented by custom listeners that respond to low-level events
 * involving interactions between the {@link Session} and the database
 * or second-level cache.
 * <p>
 * A {@code SessionEventListener} class applying to all newly-created
 * sessions may be registered using the configuration property
 * {@value org.hibernate.cfg.AvailableSettings#AUTO_SESSION_EVENTS_LISTENER}.
 * A new instance of the class will be created for each new session.
 *
 * @apiNote This an incubating API, subject to change.
 *
 * @see org.hibernate.cfg.AvailableSettings#AUTO_SESSION_EVENTS_LISTENER
 * @see SessionBuilder#eventListeners(SessionEventListener...)
 *
 * @author Steve Ebersole
 */
@Incubating
public interface SessionEventListener extends Serializable {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void transactionCompletion(boolean successful) {}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void jdbcConnectionAcquisitionStart() {}
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void jdbcConnectionAcquisitionEnd() {}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void jdbcConnectionReleaseStart() {}
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void jdbcConnectionReleaseEnd() {}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void jdbcPrepareStatementStart() {}
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void jdbcPrepareStatementEnd() {}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void jdbcExecuteStatementStart() {}
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void jdbcExecuteStatementEnd() {}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void jdbcExecuteBatchStart() {}
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void jdbcExecuteBatchEnd() {}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void cachePutStart() {}
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void cachePutEnd() {}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void cacheGetStart() {}
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void cacheGetEnd(boolean hit) {}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void flushStart() {}
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void flushEnd(int numberOfEntities, int numberOfCollections) {}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void prePartialFlushStart(){}
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void prePartialFlushEnd(){}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void partialFlushStart() {}
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void partialFlushEnd(int numberOfEntities, int numberOfCollections) {}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void dirtyCalculationStart() {}
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void dirtyCalculationEnd(boolean dirty) {}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void end() {}
}
