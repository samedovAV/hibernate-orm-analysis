/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.results.graph.internal;

import java.util.Collections;
import java.util.Iterator;
import java.util.function.Consumer;

import org.hibernate.internal.util.IndexedConsumer;
import org.hibernate.sql.results.graph.Fetch;
import org.hibernate.sql.results.graph.FetchList;
import org.hibernate.sql.results.graph.Fetchable;
import org.hibernate.sql.results.graph.FetchableContainer;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

public final class ImmutableFetchList implements FetchList {

	public static ImmutableFetchList EMPTY = new ImmutableFetchList();
	private final Fetch[] fetches;

	private ImmutableFetchList() {
		this.fetches = null;
	}

	private ImmutableFetchList(Fetch[] fetches) {
		assert fetches != null;
		this.fetches = fetches;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public int size() {
		if ( fetches == null ) {
			return 0;
		}
		int size = 0;
		for ( Fetch fetch : fetches ) {
			if ( fetch != null ) {
				size++;
			}
		}
		return size;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isEmpty() {
		return fetches == null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Fetch get(Fetchable fetchable) {
		return fetches == null ? null : fetches[fetchable.getFetchableKey()];
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void forEach(Consumer<? super Fetch> consumer) {
		if ( fetches != null ) {
			for ( Fetch fetch : fetches ) {
				if ( fetch != null ) {
					consumer.accept( fetch );
				}
			}
		}
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void indexedForEach(IndexedConsumer<? super Fetch> consumer) {
		if ( fetches != null ) {
			int index = 0;
			for ( Fetch fetch : fetches ) {
				if ( fetch != null ) {
					consumer.accept( index++, fetch );
				}
			}
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Iterator<Fetch> iterator() {
		if ( fetches == null ) {
			return Collections.emptyIterator();
		}
		return new FetchIterator();
	}

	private final class FetchIterator implements Iterator<Fetch> {

		private int idx;

		public FetchIterator() {
			assert ImmutableFetchList.this.fetches != null;
			this.idx = 0;
			while (ImmutableFetchList.this.fetches[idx] == null) {
				idx++;
			}
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public boolean hasNext() {
			return idx < ImmutableFetchList.this.fetches.length;
		}

		@Override
		@Prove(complexity = Complexity.O_N, n = "", count = {})
		public Fetch next() {
			final Fetch fetch = ImmutableFetchList.this.fetches[idx++];
			while ( idx < ImmutableFetchList.this.fetches.length ) {
				if ( ImmutableFetchList.this.fetches[idx] != null ) {
					break;
				}
				idx++;
			}
			return fetch;
		}

	}

	public static class Builder {
		private final Fetch[] fetches;

		public Builder(FetchableContainer container) {
			this.fetches = new Fetch[container.getNumberOfFetchableKeys()];
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public void add(Fetch fetch) {
			fetches[fetch.getFetchedMapping().getFetchableKey()] = fetch;
		}

		@Prove(complexity = Complexity.O_N, n = "", count = {})
		public ImmutableFetchList build() {
			for ( Fetch fetch : fetches ) {
				if ( fetch != null ) {
					return new ImmutableFetchList( fetches );
				}
			}
			return EMPTY;
		}
	}
}
