/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.mapping.internal;

import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Consumer;

import org.hibernate.internal.util.IndexedConsumer;
import org.hibernate.metamodel.mapping.AttributeMapping;
import org.hibernate.metamodel.mapping.AttributeMappingsList;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

public final class ImmutableAttributeMappingList implements AttributeMappingsList {

	//Not using a generic collection storage as that would imply type checks
	//on each access: it has been shown that iteration on AttributeMappingsList
	//collections was severely triggering https://bugs.openjdk.org/browse/JDK-8180450 .
	//Arrays are safe for types reads; not for writes! but we're only writing
	//during initialization.
	private final AttributeMapping[] list;

	private ImmutableAttributeMappingList(final ArrayList<AttributeMapping> objects) {
		this.list = objects.toArray( new AttributeMapping[0] );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int size() {
		return list.length;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public AttributeMapping get(final int i) {
		return list[i]; //intentional unguarded array access: let it explode
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void forEach(Consumer<? super AttributeMapping> attributeMappingConsumer) {
		for ( var attributeMapping : list ) {
			attributeMappingConsumer.accept( attributeMapping );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void indexedForEach(final IndexedConsumer<? super AttributeMapping> consumer) {
		for ( int i = 0; i < list.length; i++ ) {
			consumer.accept( i, list[i] );
		}
	}

	public static final class Builder {

		private final ArrayList<AttributeMapping> builderList;

		public Builder(final int sizeHint) {
			builderList = new ArrayList<>( sizeHint );
		}

		@Prove(complexity = Complexity.O_N, n = "", count = {})
		public void add(final AttributeMapping attributeMapping) {
			Objects.requireNonNull( attributeMapping );
			builderList.add( attributeMapping );
		}

		@Prove(complexity = Complexity.O_N, n = "", count = {})
		int size() {
			return builderList.size();
		}

		@Prove(complexity = Complexity.O_N, n = "", count = {})
		AttributeMapping get(final int i) {
			return builderList.get( i );
		}

		@Prove(complexity = Complexity.O_N, n = "", count = {})
		void set(final int i, final AttributeMapping attributeMapping) {
			Objects.requireNonNull( attributeMapping );
			builderList.set( i, attributeMapping );
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public AttributeMappingsList build() {
			return new ImmutableAttributeMappingList( builderList );
		}

		@Prove(complexity = Complexity.O_N, n = "", count = {})
		public boolean assertFetchableIndexes() {
			for ( int i = 0; i < builderList.size(); i++ ) {
				final var attributeMapping = builderList.get( i );
				assert i == attributeMapping.getFetchableKey();
			}
			return true;
		}
	}

}
