/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.results.jdbc.internal;

import java.util.LinkedHashSet;

import org.hibernate.sql.results.graph.DomainResultAssembler;
import org.hibernate.sql.results.graph.Initializer;
import org.hibernate.sql.results.internal.InitializersList;
import org.hibernate.sql.results.jdbc.spi.JdbcValuesMappingResolution;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

public class JdbcValuesMappingResolutionImpl implements JdbcValuesMappingResolution {

	private final DomainResultAssembler<?>[] domainResultAssemblers;
	private final Initializer<?>[] resultInitializers;
	private final boolean hasCollectionInitializers;
	private final InitializersList initializersList;

	public JdbcValuesMappingResolutionImpl(
			DomainResultAssembler<?>[] domainResultAssemblers,
			boolean hasCollectionInitializers,
			InitializersList initializersList) {
		this( domainResultAssemblers,
				getResultInitializers( domainResultAssemblers ),
				hasCollectionInitializers,
				initializersList );
	}

	private JdbcValuesMappingResolutionImpl(
			DomainResultAssembler<?>[] domainResultAssemblers,
			Initializer<?>[] resultInitializers,
			boolean hasCollectionInitializers,
			InitializersList initializersList) {
		this.domainResultAssemblers = domainResultAssemblers;
		this.resultInitializers = resultInitializers;
		this.hasCollectionInitializers = hasCollectionInitializers;
		this.initializersList = initializersList;
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	private static Initializer<?>[] getResultInitializers(DomainResultAssembler<?>[] resultAssemblers) {
		final LinkedHashSet<Initializer<?>> initializers = new LinkedHashSet<>( resultAssemblers.length );
		for ( var resultAssembler : resultAssemblers ) {
			resultAssembler.forEachResultAssembler( (initializer, list) -> list.add( initializer ), initializers );
		}
		return initializers.toArray(Initializer.EMPTY_ARRAY);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public DomainResultAssembler<?>[] getDomainResultAssemblers() {
		return domainResultAssemblers;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean hasCollectionInitializers() {
		return hasCollectionInitializers;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Initializer<?>[] getResultInitializers() {
		return resultInitializers;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Initializer<?>[] getInitializers() {
		return initializersList.getInitializers();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Initializer<?>[] getSortedForResolveInstance() {
		return initializersList.getSortedForResolveInstance();
	}

}
