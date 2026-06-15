/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.action.queue.spi.bind;

import org.hibernate.Incubating;
import org.hibernate.engine.spi.SessionImplementor;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/// Support for callbacks after the execution of a FlushOperation.
///
/// @see org.hibernate.action.queue.spi.plan.FlushOperation#getPostExecutionCallback()
///
/// @author Steve Ebersole
/// @since 8.0
@Incubating
public interface PostExecutionCallback {
	/// The callback.
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void handle(SessionImplementor session);
}
