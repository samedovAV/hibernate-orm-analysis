/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.graph.internal.parse.strategy;

import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.graph.spi.GraphImplementor;
import org.hibernate.graph.spi.RootGraphImplementor;
import org.hibernate.metamodel.model.domain.EntityDomainType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

public interface GraphParsingStrategy {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> RootGraphImplementor<T> parse(
			EntityDomainType<T> entityDomainType,
			String graphText,
			SessionFactoryImplementor sessionFactory);

	@Deprecated(forRemoval = true)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> RootGraphImplementor<T> parse(String graphText, SessionFactoryImplementor sessionFactory);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void parseInto(GraphImplementor<?> graph, String graphText, SessionFactoryImplementor sessionFactory);

}
