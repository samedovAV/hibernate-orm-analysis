/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.tree.domain;

import org.hibernate.query.sqm.tree.from.SqmJoin;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface SqmSingularValuedJoin<L,R> extends SqmJoin<L, R> {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmCorrelatedSingularValuedJoin<L,R> createCorrelation();
}
