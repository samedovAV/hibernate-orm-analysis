/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.results.graph;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Producer for {@link Initializer} based on a {@link FetchParent}.
 *
 * @see AssemblerCreationState#resolveInitializer(FetchParent, InitializerParent, InitializerProducer)
 * @since 6.5
 */
public interface InitializerProducer<P extends FetchParent> {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Initializer<?> createInitializer(
			P resultGraphNode,
			InitializerParent<?> parent,
			AssemblerCreationState creationState);
}
