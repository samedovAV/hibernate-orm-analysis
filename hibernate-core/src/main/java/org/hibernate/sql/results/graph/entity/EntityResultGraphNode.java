/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.results.graph.entity;

import org.hibernate.engine.spi.FetchOptions;
import org.hibernate.graph.spi.GraphHelper;
import org.hibernate.graph.spi.GraphImplementor;
import org.hibernate.metamodel.mapping.EntityMappingType;
import org.hibernate.metamodel.model.domain.JpaMetamodel;
import org.hibernate.sql.results.graph.DomainResultGraphNode;
import org.hibernate.sql.results.graph.FetchParent;
import org.hibernate.metamodel.mapping.EntityValuedModelPart;
import org.hibernate.spi.NavigablePath;
import org.hibernate.type.descriptor.java.JavaType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Represents a reference to an entity either as a return, fetch, or collection element or index.
 *
 * @author Steve Ebersole
 */
public interface EntityResultGraphNode extends DomainResultGraphNode, FetchParent {
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NavigablePath getNavigablePath();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EntityValuedModelPart getEntityValuedModelPart();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default FetchOptions getFetchOptions() {
		return FetchOptions.NONE;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean containsAnyNonScalarResults() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default JavaType<?> getResultJavaType() {
		return getEntityValuedModelPart().getEntityMappingType().getMappedJavaType();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default EntityMappingType getReferencedMappingContainer() {
		return getEntityValuedModelPart().getEntityMappingType();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default boolean appliesTo(GraphImplementor<?> graphImplementor, JpaMetamodel metamodel) {
		final String entityName = getEntityValuedModelPart().getEntityMappingType().getEntityName();
		return GraphHelper.appliesTo( graphImplementor, metamodel.entity( entityName ) );
	}
}
