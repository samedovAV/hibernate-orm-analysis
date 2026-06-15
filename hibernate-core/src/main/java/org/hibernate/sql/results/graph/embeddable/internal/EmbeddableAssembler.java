/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.results.graph.embeddable.internal;

import java.util.function.BiConsumer;

import org.hibernate.metamodel.mapping.PluralAttributeMapping;
import org.hibernate.sql.results.graph.Initializer;
import org.hibernate.sql.results.graph.InitializerData;
import org.hibernate.sql.results.graph.embeddable.EmbeddableInitializer;
import org.hibernate.sql.results.graph.DomainResultAssembler;
import org.hibernate.sql.results.graph.collection.internal.DetachedCollectionHelper;
import org.hibernate.sql.results.jdbc.spi.RowProcessingState;
import org.hibernate.type.descriptor.java.JavaType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class EmbeddableAssembler implements DomainResultAssembler {
	private static final CollectionLoader[] NO_COLLECTION_LOADERS = new CollectionLoader[0];

	protected final EmbeddableInitializer<InitializerData> initializer;
	private final CollectionLoader[] collectionLoaders;

	public EmbeddableAssembler(EmbeddableInitializer<?> initializer) {
		this( initializer, NO_COLLECTION_LOADERS );
	}

	public EmbeddableAssembler(EmbeddableInitializer<?> initializer, CollectionLoader[] collectionLoaders) {
		this.initializer = (EmbeddableInitializer<InitializerData>) initializer;
		this.collectionLoaders = collectionLoaders;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JavaType getAssembledJavaType() {
		return initializer.getInitializedPart().getJavaType();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Object assemble(RowProcessingState rowProcessingState) {
		final var data = initializer.getData( rowProcessingState );
		final var state = data.getState();
		if ( state == Initializer.State.UNINITIALIZED ) {
			initializer.resolveKey( data );
		}
		if ( state == Initializer.State.KEY_RESOLVED ) {
			initializer.resolveInstance( data );
		}
		final Object instance = initializer.getResolvedInstance( data );
		for ( CollectionLoader collectionLoader : collectionLoaders ) {
			collectionLoader.load( instance, rowProcessingState );
		}
		return instance;
	}

	@Override
	@Prove(complexity = Complexity.O_N2, n = "", count = {})
	public void resolveState(RowProcessingState rowProcessingState) {
		// use resolveState instead of initialize instance to avoid
		// unneeded embeddable instantiation and injection
		initializer.resolveState( rowProcessingState );
		for ( var collectionLoader : collectionLoaders ) {
			collectionLoader.resolveState( rowProcessingState );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public EmbeddableInitializer<?> getInitializer() {
		return initializer;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void forEachResultAssembler(BiConsumer consumer, Object arg) {
		if ( initializer.isResultInitializer() ) {
			consumer.accept( initializer, arg );
		}
	}

	public static class CollectionLoader {
		private final PluralAttributeMapping collectionAttribute;
		private final DomainResultAssembler<?> collectionKeyAssembler;

		public CollectionLoader(
				PluralAttributeMapping collectionAttribute,
				DomainResultAssembler<?> collectionKeyAssembler) {
			this.collectionAttribute = collectionAttribute;
			this.collectionKeyAssembler = collectionKeyAssembler;
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public void load(Object embeddable, RowProcessingState rowProcessingState) {
			if ( embeddable != null ) {
				collectionAttribute.setValue(
						embeddable,
						DetachedCollectionHelper.loadAndCopy(
								collectionAttribute,
								collectionKeyAssembler.assemble( rowProcessingState ),
								rowProcessingState.getSession(),
								null
						)
				);
			}
		}

		@Prove(complexity = Complexity.O_N, n = "", count = {})
		public void resolveState(RowProcessingState rowProcessingState) {
			collectionKeyAssembler.resolveState( rowProcessingState );
		}
	}
}
