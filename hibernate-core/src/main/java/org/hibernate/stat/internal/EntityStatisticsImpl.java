/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.stat.internal;

import java.io.Serializable;
import java.util.concurrent.atomic.LongAdder;

import org.hibernate.persister.entity.EntityPersister;
import org.hibernate.stat.EntityStatistics;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Entity related statistics
 *
 * @author Alex Snaps
 */
public class EntityStatisticsImpl extends AbstractCacheableDataStatistics implements EntityStatistics, Serializable {

	private final String rootEntityName;
	private final LongAdder loadCount = new LongAdder();
	private final LongAdder updateCount = new LongAdder();
	private final LongAdder upsertCount = new LongAdder();
	private final LongAdder insertCount = new LongAdder();
	private final LongAdder deleteCount = new LongAdder();
	private final LongAdder fetchCount = new LongAdder();
	private final LongAdder optimisticFailureCount = new LongAdder();

	EntityStatisticsImpl(EntityPersister rootEntityDescriptor) {
		super( () -> {
			final var cache = rootEntityDescriptor.getCacheAccessStrategy();
			return cache != null ? cache.getRegion() : null;
		} );
		rootEntityName = rootEntityDescriptor.getRootEntityName();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public long getDeleteCount() {
		return deleteCount.sum();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public long getInsertCount() {
		return insertCount.sum();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public long getLoadCount() {
		return loadCount.sum();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public long getUpdateCount() {
		return updateCount.sum();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public long getUpsertCount() {
		return upsertCount.sum();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public long getFetchCount() {
		return fetchCount.sum();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public long getOptimisticFailureCount() {
		return optimisticFailureCount.sum();
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
	void incrementUpsertCount() {
		upsertCount.increment();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void incrementInsertCount() {
		insertCount.increment();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void incrementDeleteCount() {
		deleteCount.increment();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void incrementOptimisticFailureCount() {
		optimisticFailureCount.increment();
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String toString() {
		final var text = new StringBuilder()
				.append( "EntityStatistics" )
				.append( "[rootEntityName=" ).append( rootEntityName )
				.append( ",loadCount=" ).append( this.loadCount )
				.append( ",updateCount=" ).append( this.updateCount )
				.append( ",upsertCount=" ).append( this.upsertCount )
				.append( ",insertCount=" ).append( this.insertCount )
				.append( ",deleteCount=" ).append( this.deleteCount )
				.append( ",fetchCount=" ).append( this.fetchCount )
				.append( ",optimisticLockFailureCount=" ).append( this.optimisticFailureCount );
		appendCacheStats( text );
		return text.append( ']' ).toString();
	}
}
