/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.results.graph.entity.internal;

import java.util.BitSet;

import org.hibernate.engine.spi.FetchOptions;
import org.hibernate.metamodel.mapping.EntityMappingType;
import org.hibernate.metamodel.mapping.internal.ToOneAttributeMapping;
import org.hibernate.spi.NavigablePath;
import org.hibernate.sql.results.graph.AssemblerCreationState;
import org.hibernate.sql.results.graph.DomainResult;
import org.hibernate.sql.results.graph.DomainResultAssembler;
import org.hibernate.sql.results.graph.DomainResultCreationState;
import org.hibernate.sql.results.graph.Fetch;
import org.hibernate.sql.results.graph.FetchParent;
import org.hibernate.sql.results.graph.Fetchable;
import org.hibernate.sql.results.graph.InitializerParent;
import org.hibernate.sql.results.graph.InitializerProducer;
import org.hibernate.sql.results.graph.basic.BasicFetch;
import org.hibernate.sql.results.graph.entity.EntityFetch;
import org.hibernate.sql.results.graph.entity.EntityInitializer;
import org.hibernate.sql.results.graph.internal.ImmutableFetchList;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public abstract class AbstractNonJoinedEntityFetch implements EntityFetch,
		InitializerProducer<AbstractNonJoinedEntityFetch> {
	private final NavigablePath navigablePath;
	private final ToOneAttributeMapping fetchedModelPart;
	private final FetchParent fetchParent;
	private final DomainResult<?> keyResult;
	private final BasicFetch<?> discriminatorFetch;
	private final boolean selectByUniqueKey;
	private final FetchOptions fetchOptions;

	public AbstractNonJoinedEntityFetch(
			NavigablePath navigablePath,
			ToOneAttributeMapping fetchedModelPart,
			FetchParent fetchParent,
			DomainResult<?> keyResult,
			boolean selectDiscriminator,
			boolean selectByUniqueKey,
			DomainResultCreationState creationState) {
		this.navigablePath = navigablePath;
		this.fetchedModelPart = fetchedModelPart;
		this.fetchParent = fetchParent;
		this.keyResult = keyResult;
		this.discriminatorFetch = selectDiscriminator ? creationState.visitDiscriminatorFetch( this ) : null;
		this.selectByUniqueKey = selectByUniqueKey;
		this.fetchOptions = creationState.getFetchOptions( navigablePath );
	}

	protected AbstractNonJoinedEntityFetch(
			NavigablePath navigablePath,
			ToOneAttributeMapping fetchedModelPart,
			FetchParent fetchParent,
			DomainResult<?> keyResult,
			BasicFetch<?> discriminatorFetch,
			boolean selectByUniqueKey,
			FetchOptions fetchOptions) {
		this.navigablePath = navigablePath;
		this.fetchedModelPart = fetchedModelPart;
		this.fetchParent = fetchParent;
		this.keyResult = keyResult;
		this.discriminatorFetch = discriminatorFetch;
		this.selectByUniqueKey = selectByUniqueKey;
		this.fetchOptions = fetchOptions;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public NavigablePath getNavigablePath() {
		return navigablePath;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ToOneAttributeMapping getFetchedMapping() {
		return fetchedModelPart;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ToOneAttributeMapping getEntityValuedModelPart() {
		return fetchedModelPart;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public FetchOptions getFetchOptions() {
		return fetchOptions;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public FetchParent getFetchParent() {
		return fetchParent;
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
	public boolean hasTableGroup() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void collectValueIndexesToCache(BitSet valueIndexes) {
		if ( keyResult != null ) {
			keyResult.collectValueIndexesToCache( valueIndexes );
		}
		if ( discriminatorFetch != null ) {
			discriminatorFetch.collectValueIndexesToCache( valueIndexes );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public EntityMappingType getReferencedMappingType() {
		return fetchedModelPart.getEntityMappingType();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public DomainResult<?> getKeyResult() {
		return keyResult;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public BasicFetch<?> getDiscriminatorFetch() {
		return discriminatorFetch;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isSelectByUniqueKey() {
		return selectByUniqueKey;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public DomainResultAssembler<?> createAssembler(
			InitializerParent<?> parent,
			AssemblerCreationState creationState) {
		final EntityInitializer<?> entityInitializer = creationState.resolveInitializer( this, parent, this )
				.asEntityInitializer();
		assert entityInitializer != null;
		return buildEntityAssembler( entityInitializer );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public EntityInitializer<?> createInitializer(
			AbstractNonJoinedEntityFetch resultGraphNode,
			InitializerParent<?> parent,
			AssemblerCreationState creationState) {
		return resultGraphNode.createInitializer( parent, creationState );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public abstract EntityInitializer<?> createInitializer(InitializerParent<?> parent, AssemblerCreationState creationState);

	/**
	 * Used By Hibernate Reactive
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected EntityAssembler<?> buildEntityAssembler(EntityInitializer<?> entityInitializer) {
		return new EntityAssembler<>( getFetchedMapping().getJavaType(), entityInitializer );
	}
}
