/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.internal.find;

import org.hibernate.engine.spi.StatelessSessionImplementor;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/// Context for performing load operations from [stateless sessions][StatelessSessionImplementor].
///
/// @author Steve Ebersole
public interface StatelessLoadAccessContext extends LoadAccessContext {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	StatelessSessionImplementor getStatelessSession();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default StatelessSessionImplementor getEntityHandler() {
		return getStatelessSession();
	}
}
