/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.action.queue.spi.bind;

import org.hibernate.Incubating;
import org.hibernate.engine.spi.SessionImplementor;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/// @author Steve Ebersole
/// @since 8.0
@Incubating
public class ChainedPostExecutionCallback implements PostExecutionCallback {
	private final PostExecutionCallback first;
	private final PostExecutionCallback second;

	public ChainedPostExecutionCallback(PostExecutionCallback first, PostExecutionCallback second) {
		this.first = first;
		this.second = second;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void handle(SessionImplementor session) {
		first.handle(session);
		second.handle(session);
	}
}
