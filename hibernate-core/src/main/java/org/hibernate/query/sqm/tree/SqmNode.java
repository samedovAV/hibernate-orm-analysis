/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.tree;

import org.hibernate.query.criteria.JpaCriteriaNode;
import org.hibernate.query.sqm.NodeBuilder;

import org.jboss.logging.Logger;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Base contract for any SQM AST node.
 *
 * @author Steve Ebersole
 */
public interface SqmNode extends JpaCriteriaNode, SqmCacheable {
	Logger LOG = Logger.getLogger( SqmNode.class );

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default String asLoggableText() {
		LOG.debugf( "#asLoggableText not defined for %s - using #toString", getClass().getName() );
		return toString();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NodeBuilder nodeBuilder();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmNode copy(SqmCopyContext context);
}
