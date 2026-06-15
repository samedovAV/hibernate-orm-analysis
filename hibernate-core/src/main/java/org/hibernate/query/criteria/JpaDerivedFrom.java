/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.criteria;

import jakarta.annotation.Nonnull;
import org.hibernate.Incubating;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Christian Beikov
 */
@Incubating
public interface JpaDerivedFrom<T> extends JpaFrom<T,T> {

	/**
	 * The subquery part for this derived from node.
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaSubQuery<T> getQueryPart();

}
