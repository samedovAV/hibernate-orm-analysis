/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.results.graph.entity.internal;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import org.hibernate.engine.spi.LoadQueryInfluencers;
import org.hibernate.metamodel.mapping.DiscriminatedAssociationModelPart;
import org.hibernate.metamodel.mapping.DiscriminatorValueDetails;
import org.hibernate.metamodel.mapping.EntityMappingType;
import org.hibernate.metamodel.mapping.internal.DiscriminatedAssociationMapping;
import org.hibernate.spi.NavigablePath;
import org.hibernate.sql.ast.tree.from.TableGroup;
import org.hibernate.sql.results.graph.AssemblerCreationState;
import org.hibernate.sql.results.graph.DomainResult;
import org.hibernate.sql.results.graph.DomainResultAssembler;
import org.hibernate.sql.results.graph.DomainResultCreationState;
import org.hibernate.sql.results.graph.Initializer;
import org.hibernate.sql.results.graph.InitializerParent;
import org.hibernate.sql.results.graph.InitializerProducer;
import org.hibernate.sql.results.graph.entity.AbstractDiscriminatedEntityResultGraphNode;
import org.hibernate.type.descriptor.java.JavaType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

public class JoinedDiscriminatedEntityResult<T> extends AbstractDiscriminatedEntityResultGraphNode
		implements DomainResult<T>, InitializerProducer<JoinedDiscriminatedEntityResult<T>> {
	private final String resultVariable;
	private final List<EntityResultImpl<?>> concreteEntityResults;
	private final BitSet affectedByFilters;

	public JoinedDiscriminatedEntityResult(
			NavigablePath navigablePath,
			JavaType<?> baseAssociationJtd,
			DiscriminatedAssociationModelPart fetchedPart,
			String resultVariable,
			List<DiscriminatorValueDetails> valueDetails,
			DomainResultCreationState creationState) {
		super( navigablePath, fetchedPart, baseAssociationJtd );
		this.resultVariable = resultVariable;

		afterInitialize( creationState );

		final var fromClauseAccess = creationState.getSqlAstCreationState().getFromClauseAccess();
		this.concreteEntityResults = new ArrayList<>( valueDetails.size() );
		final var affectedByFilters = new BitSet( valueDetails.size() );
		for ( int i = 0; i < valueDetails.size(); i++ ) {
			final var entityMapping = valueDetails.get( i ).getIndicatedEntity();
			final NavigablePath concretePath = DiscriminatedAssociationMapping.concreteEntityPath( navigablePath, entityMapping );
			final TableGroup tableGroup = fromClauseAccess.getTableGroup( concretePath );
			final var entityResult = new EntityResultImpl<>( concretePath, entityMapping, tableGroup, null );
			entityResult.afterInitialize( entityResult, creationState );
			concreteEntityResults.add( entityResult );
			affectedByFilters.set( i, isAffectedByEnabledFilters( entityMapping, creationState ) );
		}
		this.affectedByFilters = affectedByFilters;
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	private static boolean isAffectedByEnabledFilters(EntityMappingType entityMappingType, DomainResultCreationState creationState) {
		final LoadQueryInfluencers loadQueryInfluencers = creationState.getSqlAstCreationState()
				.getLoadQueryInfluencers();
		return entityMappingType.isAffectedByEnabledFilters( loadQueryInfluencers, true );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected List<EntityResultImpl<?>> getConcreteEntityResults() {
		return concreteEntityResults;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected BitSet getAffectedByFilters() {
		return affectedByFilters;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getResultVariable() {
		return resultVariable;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public DomainResultAssembler<T> createResultAssembler(
			InitializerParent<?> parent,
			AssemblerCreationState creationState) {
		//noinspection unchecked
		return new EntityAssembler(
				getReferencedMappingContainer().getJavaType(),
				creationState.resolveInitializer( this, parent, this ).asEntityInitializer()
		);
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Initializer<?> createInitializer(
			JoinedDiscriminatedEntityResult<T> resultGraphNode,
			InitializerParent<?> parent,
			AssemblerCreationState creationState) {
		return resultGraphNode.createInitializer( parent, creationState );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Initializer<?> createInitializer(InitializerParent<?> parent, AssemblerCreationState creationState) {
		return new JoinedDiscriminatedEntityInitializer(
				parent,
				getReferencedMappingType(),
				getNavigablePath(),
				getDiscriminatorValueFetch(),
				getKeyValueFetch(),
				true,
				affectedByFilters,
				true,
				concreteEntityResults,
				creationState
		);
	}

	@Override
	@Prove(complexity = Complexity.O_N2, n = "", count = {})
	public boolean containsCollectionFetches() {
		for ( var entityResult : concreteEntityResults ) {
			if ( entityResult.containsCollectionFetches() ) {
				return true;
			}
		}
		return false;
	}

	@Prove(complexity = Complexity.O_N2, n = "", count = {})
	public void collectValueIndexesToCache(BitSet valueIndexes) {
		super.collectValueIndexesToCache( valueIndexes );
		for ( var entityResult : concreteEntityResults ) {
			entityResult.collectValueIndexesToCache( valueIndexes );
		}
	}
}
