/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.tree.cte;

import java.util.Collection;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.hibernate.query.criteria.JpaCteContainer;
import org.hibernate.query.sqm.tree.SqmNode;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Christian Beikov
 */
public interface SqmCteContainer extends SqmNode, JpaCteContainer {

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Collection<SqmCteStatement<?>> getCteStatements();

	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmCteStatement<?> getCteStatement(String cteLabel);

}
