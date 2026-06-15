/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.spi;

import org.hibernate.StatelessSession;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * SPI extension of StatelessSession
 *
 * @author Steve Ebersole
 *
 * @since 7.2
 */
public interface StatelessSessionImplementor extends StatelessSession, SharedSessionContractImplementor {
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean isStateless() {
		return true;
	}
}
