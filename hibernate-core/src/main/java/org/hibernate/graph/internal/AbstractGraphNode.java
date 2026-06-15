/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.graph.internal;

import org.hibernate.graph.spi.GraphNodeImplementor;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public abstract class AbstractGraphNode<J> implements GraphNodeImplementor<J> {

	private final boolean mutable;

	public AbstractGraphNode(boolean mutable) {
		this.mutable = mutable;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isMutable() {
		return mutable;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected void verifyMutability() {
		if ( !isMutable() ) {
			throw new IllegalStateException( "Cannot mutate immutable graph node" );
		}
	}
}
