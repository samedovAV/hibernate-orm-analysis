/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.results.graph.embeddable.internal;

import org.hibernate.engine.FetchTiming;
import org.hibernate.metamodel.mapping.EmbeddableMappingType;
import org.hibernate.metamodel.mapping.EmbeddableValuedModelPart;
import org.hibernate.spi.NavigablePath;
import org.hibernate.sql.ast.SqlAstJoinType;
import org.hibernate.sql.ast.tree.from.TableGroup;
import org.hibernate.sql.ast.tree.from.TableGroupJoin;
import org.hibernate.sql.ast.tree.from.TableGroupProducer;
import org.hibernate.sql.results.graph.AbstractFetchParent;
import org.hibernate.sql.results.graph.AssemblerCreationState;
import org.hibernate.sql.results.graph.DomainResultAssembler;
import org.hibernate.sql.results.graph.DomainResultCreationState;
import org.hibernate.sql.results.graph.Fetch;
import org.hibernate.sql.results.graph.FetchParent;
import org.hibernate.sql.results.graph.Fetchable;
import org.hibernate.sql.results.graph.InitializerParent;
import org.hibernate.sql.results.graph.InitializerProducer;
import org.hibernate.sql.results.graph.basic.BasicFetch;
import org.hibernate.sql.results.graph.embeddable.AggregateEmbeddableResultGraphNode;
import org.hibernate.sql.results.graph.embeddable.EmbeddableInitializer;
import org.hibernate.sql.results.graph.embeddable.EmbeddableValuedFetchable;

import static org.hibernate.internal.util.NullnessUtil.castNonNull;
import static org.hibernate.sql.results.graph.embeddable.AggregateEmbeddableResultGraphNode.determineAggregateValuesArrayPositions;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A Fetch for an embeddable that is mapped as aggregate e.g. STRUCT, JSON or XML.
 * This is only used when {@link EmbeddableMappingType#shouldSelectAggregateMapping()} returns <code>true</code>.
 * The main difference is that it selects only the aggregate column and
 * uses {@link org.hibernate.sql.results.graph.DomainResultCreationState#visitNestedFetches(FetchParent)}
 * for creating the fetches for the attributes of the embeddable.
 */
public class AggregateEmbeddableFetchImpl extends AbstractFetchParent
		implements AggregateEmbeddableResultGraphNode, Fetch, InitializerProducer<AggregateEmbeddableFetchImpl> {
	private final FetchParent fetchParent;
	private final FetchTiming fetchTiming;
	private final TableGroup tableGroup;
	private final boolean hasTableGroup;
	private final EmbeddableMappingType fetchContainer;
	private final BasicFetch<?> discriminatorFetch;
	private final int[] aggregateValuesArrayPositions;

	public AggregateEmbeddableFetchImpl(
			NavigablePath navigablePath,
			EmbeddableValuedFetchable embeddedPartDescriptor,
			FetchParent fetchParent,
			FetchTiming fetchTiming,
			boolean hasTableGroup,
			DomainResultCreationState creationState) {
		super( navigablePath );
		this.fetchContainer = embeddedPartDescriptor.getEmbeddableTypeDescriptor();

		this.fetchParent = fetchParent;
		this.fetchTiming = fetchTiming;
		this.hasTableGroup = hasTableGroup;

		final var sqlAstCreationState = creationState.getSqlAstCreationState();
		this.tableGroup = sqlAstCreationState.getFromClauseAccess().resolveTableGroup(
				getNavigablePath(),
				np -> {
					final TableGroup lhsTableGroup = sqlAstCreationState.getFromClauseAccess()
							.findTableGroup( fetchParent.getNavigablePath() );
					final TableGroupJoin tableGroupJoin = getReferencedMappingContainer().createTableGroupJoin(
							getNavigablePath(),
							lhsTableGroup,
							null,
							null,
							SqlAstJoinType.INNER,
							true,
							false,
							sqlAstCreationState
					);
					lhsTableGroup.addTableGroupJoin( tableGroupJoin );
					return tableGroupJoin.getJoinedGroup();
				}

		);

		final var sqlExpressionResolver = sqlAstCreationState.getSqlExpressionResolver();
		final var tableReference = tableGroup.getPrimaryTableReference();
		final var selectableMapping = fetchContainer.getAggregateMapping();
		final var expression = sqlExpressionResolver.resolveSqlExpression( tableReference, selectableMapping );
		final var typeConfiguration = sqlAstCreationState.getCreationContext().getTypeConfiguration();
		final var aggregateSelection = sqlExpressionResolver.resolveSqlSelection(
				expression,
				typeConfiguration.getJavaTypeRegistry().resolveDescriptor( Object[].class ),
				fetchParent,
				typeConfiguration
		);
		this.discriminatorFetch = creationState.visitEmbeddableDiscriminatorFetch( this, true );
		this.aggregateValuesArrayPositions = determineAggregateValuesArrayPositions( fetchParent, aggregateSelection );
		resetFetches( creationState.visitNestedFetches( this ) );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int[] getAggregateValuesArrayPositions() {
		return aggregateValuesArrayPositions;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public FetchTiming getTiming() {
		return fetchTiming;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean hasTableGroup() {
		return hasTableGroup;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public FetchParent getFetchParent() {
		return fetchParent;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public EmbeddableMappingType getFetchContainer() {
		return fetchContainer;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public EmbeddableValuedModelPart getReferencedMappingContainer() {
		return getFetchContainer().getEmbeddedValueMapping();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Fetchable getFetchedMapping() {
		return getReferencedMappingContainer();
	}


	@Override
	@Prove(complexity = Complexity.O_N2, n = "", count = {})
	public NavigablePath resolveNavigablePath(Fetchable fetchable) {
		if ( fetchable instanceof TableGroupProducer ) {
			for ( var tableGroupJoin : tableGroup.getTableGroupJoins() ) {
				final var navigablePath = tableGroupJoin.getNavigablePath();
				if ( tableGroupJoin.getJoinedGroup().isFetched()
						&& fetchable.getFetchableName().equals( navigablePath.getLocalName() )
						&& tableGroupJoin.getJoinedGroup().getModelPart() == fetchable
						&& castNonNull( navigablePath.getParent() ).equals( getNavigablePath() ) ) {
					return navigablePath;
				}
			}
		}
		return super.resolveNavigablePath( fetchable );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public EmbeddableMappingType getReferencedMappingType() {
		return getFetchContainer();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public DomainResultAssembler<?> createAssembler(
			InitializerParent<?> parent,
			AssemblerCreationState creationState) {
		return new EmbeddableAssembler( creationState.resolveInitializer( this, parent, this ).asEmbeddableInitializer() );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public EmbeddableInitializer<?> createInitializer(
			AggregateEmbeddableFetchImpl resultGraphNode,
			InitializerParent<?> parent,
			AssemblerCreationState creationState) {
		return resultGraphNode.createInitializer( parent, creationState );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public EmbeddableInitializer<?> createInitializer(
			InitializerParent<?> parent,
			AssemblerCreationState creationState) {
		return new AggregateEmbeddableInitializerImpl(
				this,
				discriminatorFetch,
				parent,
				creationState,
				false
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public FetchParent asFetchParent() {
		return this;
	}
}
