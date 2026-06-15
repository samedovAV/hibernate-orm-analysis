/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.results.jdbc.spi;

import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.sql.results.graph.DomainResultAssembler;
import org.hibernate.sql.results.graph.Initializer;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * The "resolved" form of {@link JdbcValuesMapping} providing access
 * to resolved ({@link DomainResultAssembler}) descriptors and resolved
 * initializer ({@link Initializer}) descriptors.
 *
 * @see JdbcValuesMapping#resolveAssemblers(SessionFactoryImplementor)
 */
public interface JdbcValuesMappingResolution {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	DomainResultAssembler<?>[] getDomainResultAssemblers();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Initializer<?>[] getResultInitializers();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Initializer<?>[] getInitializers();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Initializer<?>[] getSortedForResolveInstance();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean hasCollectionInitializers();
}
