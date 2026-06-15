/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.graph.spi;

import jakarta.annotation.Nonnull;
import org.hibernate.graph.SubGraph;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Integration version of the {@link SubGraph} contract.
 *
 * @author Steve Ebersole
 *
 * @see RootGraphImplementor
 */
public interface SubGraphImplementor<J> extends SubGraph<J>, GraphImplementor<J> {
	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SubGraphImplementor<J> makeCopy(boolean mutable);
}
