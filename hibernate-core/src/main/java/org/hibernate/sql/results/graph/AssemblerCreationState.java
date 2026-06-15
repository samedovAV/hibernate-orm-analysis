/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.results.graph;

import java.util.function.Supplier;

import org.hibernate.metamodel.mapping.ModelPart;
import org.hibernate.spi.NavigablePath;
import org.hibernate.sql.ast.spi.SqlAstCreationContext;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface AssemblerCreationState {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean isDynamicInstantiation() {
		return false;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean containsMultipleCollectionFetches() {
		return true;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	int acquireInitializerId();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Initializer<?> resolveInitializer(
			NavigablePath navigablePath,
			ModelPart fetchedModelPart,
			Supplier<Initializer<?>> producer);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P extends FetchParent> Initializer<?> resolveInitializer(
			P resultGraphNode,
			InitializerParent<?> parent,
			InitializerProducer<P> producer);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqlAstCreationContext getSqlAstCreationContext();

}
