/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.results.graph.collection.internal;

import org.hibernate.engine.spi.FetchOptions;
import org.hibernate.metamodel.mapping.PluralAttributeMapping;
import org.hibernate.spi.NavigablePath;
import org.hibernate.sql.results.graph.AssemblerCreationState;
import org.hibernate.sql.results.graph.DomainResultAssembler;
import org.hibernate.sql.results.graph.Fetch;
import org.hibernate.sql.results.graph.FetchParent;
import org.hibernate.sql.results.graph.Fetchable;
import org.hibernate.sql.results.graph.Initializer;
import org.hibernate.sql.results.graph.InitializerParent;
import org.hibernate.sql.results.graph.InitializerProducer;
import org.hibernate.sql.results.graph.internal.ImmutableFetchList;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public abstract class CollectionFetch implements FetchParent, Fetch, InitializerProducer<CollectionFetch> {
	private final NavigablePath fetchedPath;
	private final PluralAttributeMapping fetchedAttribute;

	private final FetchParent fetchParent;
	private final FetchOptions fetchOptions;

	public CollectionFetch(
			NavigablePath fetchedPath,
			PluralAttributeMapping fetchedAttribute,
			FetchParent fetchParent,
			FetchOptions fetchOptions) {
		this.fetchedPath = fetchedPath;
		this.fetchedAttribute = fetchedAttribute;
		this.fetchParent = fetchParent;
		this.fetchOptions = fetchOptions;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public FetchParent getFetchParent() {
		return fetchParent;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PluralAttributeMapping getFetchedMapping() {
		return fetchedAttribute;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public FetchOptions getFetchOptions() {
		return fetchOptions;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public NavigablePath getNavigablePath() {
		return fetchedPath;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PluralAttributeMapping getReferencedMappingContainer() {
		return getFetchedMapping();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PluralAttributeMapping getReferencedMappingType() {
		return getFetchedMapping();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ImmutableFetchList getFetches() {
		return ImmutableFetchList.EMPTY;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Fetch findFetch(Fetchable fetchable) {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean hasJoinFetches() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean containsCollectionFetches() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public DomainResultAssembler<?> createAssembler(
			InitializerParent<?> parent,
			AssemblerCreationState creationState) {
		return new CollectionAssembler(
				getFetchedMapping(),
				creationState.resolveInitializer( this, parent, this ).asCollectionInitializer()
		);
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Initializer<?> createInitializer(
			CollectionFetch resultGraphNode,
			InitializerParent<?> parent,
			AssemblerCreationState creationState) {
		return resultGraphNode.createInitializer( parent, creationState );
	}
}
