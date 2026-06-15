/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.action.queue.spi.bind;

import org.hibernate.Incubating;
import org.hibernate.engine.spi.SessionImplementor;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/// Callback invoked immediately before executing a planned operation.
///
/// @author Steve Ebersole
/// @since 8.0
@Incubating
public interface PreExecutionCallback {
	/// @return `true` to execute the operation; `false` to skip it.
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean beforeExecution(SessionImplementor session);
}
