/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.results.graph.entity.internal;

import org.hibernate.engine.FetchTiming;
import org.hibernate.metamodel.mapping.DiscriminatedAssociationModelPart;
import org.hibernate.spi.NavigablePath;
import org.hibernate.sql.results.graph.AssemblerCreationState;
import org.hibernate.sql.results.graph.DomainResultAssembler;
import org.hibernate.sql.results.graph.DomainResultCreationState;
import org.hibernate.sql.results.graph.Fetch;
import org.hibernate.sql.results.graph.FetchParent;
import org.hibernate.sql.results.graph.Initializer;
import org.hibernate.sql.results.graph.InitializerParent;
import org.hibernate.sql.results.graph.InitializerProducer;
import org.hibernate.sql.results.graph.entity.AbstractDiscriminatedEntityResultGraphNode;
import org.hibernate.type.descriptor.java.JavaType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

public class DiscriminatedEntityFetch extends AbstractDiscriminatedEntityResultGraphNode implements Fetch,
		InitializerProducer<DiscriminatedEntityFetch> {
	private final FetchTiming fetchTiming;
	private final FetchParent fetchParent;

	public DiscriminatedEntityFetch(
			NavigablePath navigablePath,
			JavaType<?> baseAssociationJtd,
			DiscriminatedAssociationModelPart fetchedPart,
			FetchTiming fetchTiming,
			FetchParent fetchParent,
			DomainResultCreationState creationState) {
		super( navigablePath, fetchedPart, baseAssociationJtd );
		this.fetchTiming = fetchTiming;
		this.fetchParent = fetchParent;

		afterInitialize( creationState );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public FetchParent getFetchParent() {
		return fetchParent;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public DiscriminatedAssociationModelPart getFetchedMapping() {
		return getReferencedMappingContainer();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public FetchTiming getTiming() {
		return fetchTiming;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean hasTableGroup() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public DomainResultAssembler<?> createAssembler(
			InitializerParent<?> parent,
			AssemblerCreationState creationState) {
		return new EntityAssembler<>(
				getReferencedMappingContainer().getJavaType(),
				creationState.resolveInitializer( this, parent, this ).asEntityInitializer()
		);
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Initializer<?> createInitializer(
			DiscriminatedEntityFetch resultGraphNode,
			InitializerParent<?> parent,
			AssemblerCreationState creationState) {
		return resultGraphNode.createInitializer( parent, creationState );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Initializer<?> createInitializer(InitializerParent<?> parent, AssemblerCreationState creationState) {
		return new DiscriminatedEntityInitializer(
				parent,
				getReferencedMappingType(),
				getNavigablePath(),
				getDiscriminatorValueFetch(),
				getKeyValueFetch(),
				fetchTiming == FetchTiming.IMMEDIATE,
				false,
				creationState
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public FetchParent asFetchParent() {
		return this;
	}
}
