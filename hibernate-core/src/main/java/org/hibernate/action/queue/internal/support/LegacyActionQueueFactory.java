/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.action.queue.internal.support;

import org.hibernate.action.queue.spi.ActionQueue;
import org.hibernate.action.queue.spi.ActionQueueFactory;
import org.hibernate.action.queue.spi.QueueType;
import org.hibernate.engine.spi.ActionQueueLegacy;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.event.spi.EventSource;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/// ActionQueueFactory for building ActionQueueLegacy instances.
///
/// @author Steve Ebersole
public class LegacyActionQueueFactory implements ActionQueueFactory, Serializable {
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public QueueType getConfiguredQueueType() {
		return QueueType.LEGACY;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ActionQueue buildActionQueue(SessionImplementor session) {
		return new ActionQueueLegacy( session );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public ActionQueue deserialize(
			ObjectInputStream ois,
			SessionImplementor session) throws IOException, ClassNotFoundException {
		return ActionQueueLegacy.deserialize( ois, (EventSource) session );
	}
}
