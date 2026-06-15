/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.tree;

import java.io.Serializable;

import org.hibernate.query.sqm.NodeBuilder;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Base implementation of a criteria node.
 *
 * @author Steve Ebersole
 */
public abstract class AbstractSqmNode implements SqmNode, Serializable {
	private final NodeBuilder builder;

	protected AbstractSqmNode(NodeBuilder builder) {
		this.builder = builder;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public NodeBuilder nodeBuilder() {
		return builder;
	}
}
