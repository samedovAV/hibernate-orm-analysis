/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.graph.spi;

import org.hibernate.Incubating;
import org.hibernate.metamodel.model.domain.EntityDomainType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 *
 * @since 7.2
 */
@Incubating
@FunctionalInterface
public interface GraphParserEntityNameResolver {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EntityDomainType<?> resolveEntityName(String entityName);
}
