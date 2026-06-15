/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.results.graph.collection.internal;

import java.util.BitSet;

import org.hibernate.engine.FetchTiming;
import org.hibernate.engine.spi.FetchOptions;
import org.hibernate.metamodel.mapping.PluralAttributeMapping;
import org.hibernate.spi.NavigablePath;
import org.hibernate.sql.results.graph.AssemblerCreationState;
import org.hibernate.sql.results.graph.DomainResult;
import org.hibernate.sql.results.graph.FetchParent;
import org.hibernate.sql.results.graph.InitializerParent;
import org.hibernate.sql.results.graph.collection.CollectionInitializer;
import org.hibernate.type.descriptor.java.JavaType;

import jakarta.annotation.Nullable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Andrea Boriero
 */
public class SelectEagerCollectionFetch extends CollectionFetch {
	private final @Nullable DomainResult<?> collectionKeyDomainResult;

	public SelectEagerCollectionFetch(
			NavigablePath fetchedPath,
			PluralAttributeMapping fetchedAttribute,
			DomainResult<?> collectionKeyDomainResult,
			FetchParent fetchParent,
			FetchOptions fetchOptions) {
		super( fetchedPath, fetchedAttribute, fetchParent, fetchOptions );
		this.collectionKeyDomainResult = collectionKeyDomainResult;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public FetchTiming getTiming() {
		return FetchTiming.DELAYED;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean hasTableGroup() {
		return false;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public CollectionInitializer<?> createInitializer(InitializerParent<?> parent, AssemblerCreationState creationState) {
		return new SelectEagerCollectionInitializer(
				getNavigablePath(),
				getFetchedMapping(),
				parent,
				collectionKeyDomainResult,
				getFetchOptions(),
				creationState
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JavaType<?> getResultJavaType() {
		return getFetchedMapping().getJavaType();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void collectValueIndexesToCache(BitSet valueIndexes) {
		if ( collectionKeyDomainResult != null ) {
			collectionKeyDomainResult.collectValueIndexesToCache( valueIndexes );
		}
	}
}
