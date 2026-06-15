/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.tree.from;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Unified contract for things that can contain a SqmFromClause.
 *
 * @author Steve Ebersole
 */
public interface SqmFromClauseContainer {
	/**
	 * Obtains this container's SqmFromClause.
	 *
	 * @return This container's SqmFromClause.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmFromClause getFromClause();
}
