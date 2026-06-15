/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.results.graph.collection.internal;

import java.util.BitSet;
import java.util.Set;

import org.hibernate.metamodel.mapping.CollectionPart;
import org.hibernate.metamodel.mapping.PluralAttributeMapping;
import org.hibernate.spi.NavigablePath;
import org.hibernate.sql.ast.tree.from.TableGroup;
import org.hibernate.sql.results.graph.AssemblerCreationState;
import org.hibernate.sql.results.graph.DomainResult;
import org.hibernate.sql.results.graph.DomainResultAssembler;
import org.hibernate.sql.results.graph.DomainResultCreationState;
import org.hibernate.sql.results.graph.Fetch;
import org.hibernate.sql.results.graph.FetchParent;
import org.hibernate.sql.results.graph.Fetchable;
import org.hibernate.sql.results.graph.FetchableContainer;
import org.hibernate.sql.results.graph.Initializer;
import org.hibernate.sql.results.graph.InitializerParent;
import org.hibernate.sql.results.graph.internal.ImmutableFetchList;
import org.hibernate.type.descriptor.java.JavaType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A collection-valued domain result that preserves the cardinality of the owning row.
 *
 * @author Gavin King
 */
public class DetachedCollectionDomainResult<T> implements DomainResult<T>, FetchParent {
	private final NavigablePath loadingPath;
	private final PluralAttributeMapping loadingAttribute;
	private final String resultVariable;
	private final CollectionPart.Nature selectedPartNature;
	private final JavaType<?> resultJavaType;
	private final DomainResult<?> collectionKeyResult;

	public DetachedCollectionDomainResult(
			NavigablePath loadingPath,
			PluralAttributeMapping loadingAttribute,
			String resultVariable,
			TableGroup ownerTableGroup,
			CollectionPart.Nature selectedPartNature,
			DomainResultCreationState creationState) {
		this.loadingPath = loadingPath;
		this.loadingAttribute = loadingAttribute;
		this.resultVariable = resultVariable;
		this.selectedPartNature = selectedPartNature;
		this.resultJavaType = selectedPartNature == null
				? loadingAttribute.getJavaType()
				: creationState.getSqlAstCreationState()
						.getCreationContext()
						.getTypeConfiguration()
						.getJavaTypeRegistry()
						.resolveDescriptor( Set.class );
		this.collectionKeyResult = loadingAttribute.getKeyDescriptor().createTargetDomainResult(
				ownerTableGroup.getNavigablePath().append( loadingAttribute.getPartName() ),
				ownerTableGroup,
				this,
				creationState
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getResultVariable() {
		return resultVariable;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean containsAnyNonScalarResults() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JavaType<?> getResultJavaType() {
		return resultJavaType;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public NavigablePath getNavigablePath() {
		return loadingPath;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public DomainResultAssembler<T> createResultAssembler(
			InitializerParent<?> parent,
			AssemblerCreationState creationState) {
		return new DetachedCollectionAssembler<>(
				loadingAttribute,
				selectedPartNature,
				collectionKeyResult.createResultAssembler( parent, creationState ),
				resultJavaType
		);
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void collectValueIndexesToCache(BitSet valueIndexes) {
		collectionKeyResult.collectValueIndexesToCache( valueIndexes );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public FetchableContainer getReferencedMappingContainer() {
		return loadingAttribute;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public FetchableContainer getReferencedMappingType() {
		return loadingAttribute;
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
	public Initializer<?> createInitializer(InitializerParent<?> parent, AssemblerCreationState creationState) {
		throw new UnsupportedOperationException();
	}
}
