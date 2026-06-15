/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.stat.internal;

import java.io.Serializable;
import java.util.concurrent.atomic.LongAdder;

import org.hibernate.persister.collection.CollectionPersister;
import org.hibernate.stat.CollectionStatistics;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Collection related statistics
 *
 * @author Alex Snaps
 */
public class CollectionStatisticsImpl extends AbstractCacheableDataStatistics implements CollectionStatistics, Serializable {

	private final String collectionRole;
	private final LongAdder loadCount = new LongAdder();
	private final LongAdder fetchCount = new LongAdder();
	private final LongAdder updateCount = new LongAdder();
	private final LongAdder removeCount = new LongAdder();
	private final LongAdder recreateCount = new LongAdder();

	CollectionStatisticsImpl(CollectionPersister persister) {
		super( () -> {
			final var cache = persister.getCacheAccessStrategy();
			return cache == null ? null : cache.getRegion();
		} );
		collectionRole = persister.getRole();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public long getLoadCount() {
		return loadCount.sum();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public long getFetchCount() {
		return fetchCount.sum();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public long getRecreateCount() {
		return recreateCount.sum();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public long getRemoveCount() {
		return removeCount.sum();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public long getUpdateCount() {
		return updateCount.sum();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void incrementLoadCount() {
		loadCount.increment();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void incrementFetchCount() {
		fetchCount.increment();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void incrementUpdateCount() {
		updateCount.increment();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void incrementRecreateCount() {
		recreateCount.increment();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void incrementRemoveCount() {
		removeCount.increment();
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String toString() {
		final var text = new StringBuilder()
				.append( "CollectionStatistics" )
				.append( "[collectionRole=" ).append( collectionRole )
				.append( ",loadCount=" ).append( this.loadCount )
				.append( ",fetchCount=" ).append( this.fetchCount )
				.append( ",recreateCount=" ).append( this.recreateCount )
				.append( ",removeCount=" ).append( this.removeCount )
				.append( ",updateCount=" ).append( this.updateCount );
		appendCacheStats( text );
		return text.append(']').toString();
	}
}
