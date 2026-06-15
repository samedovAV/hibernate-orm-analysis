/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.results.graph.embeddable;

import org.hibernate.metamodel.mapping.EmbeddableValuedModelPart;
import org.hibernate.metamodel.mapping.EmbeddableMappingType;
import org.hibernate.spi.NavigablePath;
import org.hibernate.sql.results.graph.DomainResultGraphNode;
import org.hibernate.sql.results.graph.FetchParent;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface EmbeddableResultGraphNode extends DomainResultGraphNode, FetchParent {
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default NavigablePath getNavigablePath() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EmbeddableValuedModelPart getReferencedMappingContainer();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EmbeddableMappingType getReferencedMappingType();
}
