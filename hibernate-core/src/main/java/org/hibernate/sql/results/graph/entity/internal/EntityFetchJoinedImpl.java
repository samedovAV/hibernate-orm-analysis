/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.results.graph.entity.internal;

import java.util.BitSet;

import org.hibernate.annotations.NotFoundAction;
import org.hibernate.engine.FetchTiming;
import org.hibernate.engine.spi.FetchOptions;
import org.hibernate.metamodel.mapping.internal.EntityCollectionPart;
import org.hibernate.metamodel.mapping.internal.ToOneAttributeMapping;
import org.hibernate.spi.NavigablePath;
import org.hibernate.sql.ast.tree.from.TableGroup;
import org.hibernate.sql.results.graph.AssemblerCreationState;
import org.hibernate.sql.results.graph.DomainResult;
import org.hibernate.sql.results.graph.DomainResultAssembler;
import org.hibernate.sql.results.graph.DomainResultCreationState;
import org.hibernate.sql.results.graph.Fetch;
import org.hibernate.sql.results.graph.FetchParent;
import org.hibernate.sql.results.graph.Fetchable;
import org.hibernate.sql.results.graph.Initializer;
import org.hibernate.sql.results.graph.InitializerParent;
import org.hibernate.sql.results.graph.InitializerProducer;
import org.hibernate.sql.results.graph.entity.EntityFetch;
import org.hibernate.sql.results.graph.entity.EntityInitializer;
import org.hibernate.sql.results.graph.entity.EntityValuedFetchable;
import org.hibernate.sql.results.graph.internal.ImmutableFetchList;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Andrea Boriero
 * @author Steve Ebersole
 */
public class EntityFetchJoinedImpl implements EntityFetch, FetchParent, InitializerProducer<EntityFetchJoinedImpl> {
	private final FetchParent fetchParent;
	private final EntityValuedFetchable fetchContainer;
	private final EntityResultImpl<?> entityResult;
	private final DomainResult<?> keyResult;
	private final NotFoundAction notFoundAction;
	private final boolean isAffectedByFilter;
	private final FetchOptions fetchOptions;

	private final String sourceAlias;

	public EntityFetchJoinedImpl(
			FetchParent fetchParent,
			ToOneAttributeMapping toOneMapping,
			TableGroup tableGroup,
			DomainResult<?> keyResult,
			boolean isAffectedByFilter,
			NavigablePath navigablePath,
			DomainResultCreationState creationState) {
		this.fetchContainer = toOneMapping;
		this.fetchParent = fetchParent;
		this.keyResult = keyResult;
		this.notFoundAction = toOneMapping.getNotFoundAction();
		this.sourceAlias = tableGroup.getSourceAlias();
		this.isAffectedByFilter = isAffectedByFilter;
		this.fetchOptions = creationState.getFetchOptions( navigablePath );
		this.entityResult = new EntityResultImpl<>(
				navigablePath,
				toOneMapping,
				tableGroup,
				null
		);
		this.entityResult.afterInitialize( this, creationState );
	}

	public EntityFetchJoinedImpl(
			FetchParent fetchParent,
			EntityCollectionPart collectionPart,
			TableGroup tableGroup,
			NavigablePath navigablePath,
			DomainResultCreationState creationState) {
		this.fetchContainer = collectionPart;
		this.fetchParent = fetchParent;
		this.notFoundAction = collectionPart.getNotFoundAction();
		this.keyResult = null;
		this.sourceAlias = tableGroup.getSourceAlias();
		this.isAffectedByFilter = false;
		this.fetchOptions = creationState.getFetchOptions( navigablePath );
		this.entityResult = new EntityResultImpl<>(
				navigablePath,
				collectionPart,
				tableGroup,
				null
		);
		this.entityResult.afterInitialize( this, creationState );
	}

	/**
	 * For Hibernate Reactive
	 */
	protected EntityFetchJoinedImpl(EntityFetchJoinedImpl original) {
		this.fetchContainer = original.fetchContainer;
		this.fetchParent = original.fetchParent;
		this.entityResult = original.entityResult;
		this.keyResult = original.keyResult;
		this.notFoundAction = original.notFoundAction;
		this.isAffectedByFilter = original.isAffectedByFilter;
		this.sourceAlias = original.sourceAlias;
		this.fetchOptions = original.fetchOptions;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public EntityValuedFetchable getEntityValuedModelPart() {
		return fetchContainer;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public EntityValuedFetchable getReferencedModePart() {
		return getEntityValuedModelPart();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public EntityValuedFetchable getReferencedMappingType() {
		return getEntityValuedModelPart();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public EntityValuedFetchable getFetchedMapping() {
		return getEntityValuedModelPart();
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
	public DomainResultAssembler<?> createAssembler(
			InitializerParent<?> parent,
			AssemblerCreationState creationState) {
		return buildEntityAssembler( creationState.resolveInitializer( this, parent, this ).asEntityInitializer() );
	}

	/**
	 * Used by Hibernate Reactive
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected EntityAssembler<?> buildEntityAssembler(EntityInitializer<?> entityInitializer) {
		return new EntityAssembler<>( getFetchedMapping().getJavaType(), entityInitializer );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Initializer<?> createInitializer(
			EntityFetchJoinedImpl resultGraphNode,
			InitializerParent<?> parent,
			AssemblerCreationState creationState) {
		return resultGraphNode.createInitializer( parent, creationState );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public EntityInitializer<?> createInitializer(InitializerParent<?> parent, AssemblerCreationState creationState) {
		return new EntityInitializerImpl(
				this,
				sourceAlias,
				entityResult.getIdentifierFetch(),
				entityResult.getDiscriminatorFetch(),
				keyResult,
				entityResult.getRowIdResult(),
				entityResult.getAuditChangesetIdResult(),
				notFoundAction,
				isAffectedByFilter,
				parent,
				false,
				creationState
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public FetchTiming getTiming() {
		return FetchTiming.IMMEDIATE;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean hasTableGroup() {
		return true;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public EntityResultImpl<?> getEntityResult() {
		return entityResult;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public NavigablePath getNavigablePath() {
		return entityResult.getNavigablePath();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public ImmutableFetchList getFetches() {
		return entityResult.getFetches();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Fetch findFetch(Fetchable fetchable) {
		return entityResult.findFetch( fetchable );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean hasJoinFetches() {
		return entityResult.hasJoinFetches();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean containsCollectionFetches() {
		return entityResult.containsCollectionFetches();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void collectValueIndexesToCache(BitSet valueIndexes) {
		entityResult.collectValueIndexesToCache( valueIndexes );
	}

	/*
	 * BEGIN: For Hibernate Reactive
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected DomainResult<?> getKeyResult() {
		return keyResult;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected NotFoundAction getNotFoundAction() {
		return notFoundAction;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected boolean isAffectedByFilter() {
		return isAffectedByFilter;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected String getSourceAlias() {
		return sourceAlias;
	}
	/*
	 * END: Hibernate Reactive: make sure values are accessible from subclass
	 */
}
