/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.tree;

import org.hibernate.query.criteria.JpaCriteriaBase;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Commonality between a top-level statement and a sub-query
 *
 * @author Steve Ebersole
 */
public interface SqmQuery<T> extends JpaCriteriaBase, SqmNode {
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmQuery<T> copy(SqmCopyContext context);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String generateAlias();
}
