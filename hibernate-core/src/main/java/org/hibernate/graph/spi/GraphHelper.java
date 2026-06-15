/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.graph.spi;

import org.hibernate.graph.Graph;
import org.hibernate.metamodel.model.domain.ManagedDomainType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Helper containing utilities useful for graph handling
 *
 * @author Steve Ebersole
 */
public class GraphHelper {

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public static boolean appliesTo(Graph<?> graph, ManagedDomainType<?> managedType) {
		final ManagedDomainType<?> graphedType = graph.getGraphedType();
		ManagedDomainType<?> superType = managedType;
		while ( superType != null ) {
			if ( graphedType.equals( superType ) ) {
				return true;
			}
			superType = superType.getSuperType();
		}
		return false;
	}

}
