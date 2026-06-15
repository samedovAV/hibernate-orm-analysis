/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.results.graph.entity;

import java.util.BitSet;

import org.hibernate.metamodel.mapping.DiscriminatedAssociationModelPart;
import org.hibernate.spi.NavigablePath;
import org.hibernate.sql.results.graph.DomainResultCreationState;
import org.hibernate.sql.results.graph.DomainResultGraphNode;
import org.hibernate.sql.results.graph.Fetch;
import org.hibernate.sql.results.graph.FetchParent;
import org.hibernate.sql.results.graph.Fetchable;
import org.hibernate.sql.results.graph.internal.ImmutableFetchList;
import org.hibernate.type.descriptor.java.JavaType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

public abstract class AbstractDiscriminatedEntityResultGraphNode implements DomainResultGraphNode, FetchParent {
	private final NavigablePath navigablePath;

	private final DiscriminatedAssociationModelPart graphedPart;
	private final JavaType<?> baseAssociationJtd;

	private Fetch discriminatorValueFetch;
	private Fetch keyValueFetch;
	private ImmutableFetchList fetches;

	public AbstractDiscriminatedEntityResultGraphNode(
			NavigablePath navigablePath,
			DiscriminatedAssociationModelPart graphedPart,
			JavaType<?> baseAssociationJtd) {
		this.navigablePath = navigablePath;
		this.graphedPart = graphedPart;
		this.baseAssociationJtd = baseAssociationJtd;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected void afterInitialize(DomainResultCreationState creationState) {
		this.fetches = creationState.visitFetches( this );
		assert fetches.size() == 2;

		discriminatorValueFetch = fetches.get( graphedPart.getDiscriminatorMapping() );
		keyValueFetch = fetches.get( graphedPart.getKeyPart() );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Fetch getDiscriminatorValueFetch() {
		return discriminatorValueFetch;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Fetch getKeyValueFetch() {
		return keyValueFetch;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JavaType<?> getBaseAssociationJtd() {
		return baseAssociationJtd;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public NavigablePath getNavigablePath() {
		return navigablePath;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JavaType<?> getResultJavaType() {
		return baseAssociationJtd;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean containsAnyNonScalarResults() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public DiscriminatedAssociationModelPart getReferencedMappingContainer() {
		return graphedPart;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public DiscriminatedAssociationModelPart getReferencedMappingType() {
		return graphedPart;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ImmutableFetchList getFetches() {
		return fetches;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Fetch findFetch(Fetchable fetchable) {
		if ( graphedPart.getDiscriminatorMapping() == fetchable ) {
			return discriminatorValueFetch;
		}

		if ( graphedPart.getKeyPart() == fetchable ) {
			return keyValueFetch;
		}

		throw new IllegalArgumentException( "Given Fetchable [" + fetchable + "] did not match either discriminator nor key mapping" );
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

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void collectValueIndexesToCache(BitSet valueIndexes) {
		FetchParent.super.collectValueIndexesToCache( valueIndexes );
	}
}
