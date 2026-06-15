/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.spi;

import java.util.function.Function;
import java.util.function.Supplier;

import org.hibernate.Incubating;
import org.hibernate.query.hql.HqlTranslator;
import org.hibernate.query.sql.spi.ParameterInterpretation;
import org.hibernate.query.sqm.internal.SqmInterpretationsKey;
import org.hibernate.query.sqm.spi.InterpretationsKeySource;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Cache for various parts of translating or interpreting queries.
 *
 * @see org.hibernate.cfg.AvailableSettings#QUERY_PLAN_CACHE_ENABLED
 * @see org.hibernate.cfg.AvailableSettings#QUERY_PLAN_CACHE_MAX_SIZE
 *
 * @author Steve Ebersole
 */
@Incubating
public interface QueryInterpretationCache {
	interface Key {
		/**
		 * The possibility for a cache key to do defensive copying in case it has mutable state.
		 */
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		default Key prepareForStore() {
			return this;
		}
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		String getQueryString();
	}

	// Used by Hibernate Reactive
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	static Key createInterpretationsKey(InterpretationsKeySource keySource) {
		return SqmInterpretationsKey.createInterpretationsKey(keySource);
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	int getNumberOfCachedHqlInterpretations();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	int getNumberOfCachedQueryPlans();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<R> HqlInterpretation<R> resolveHqlInterpretation(String queryString, Class<R> expectedResultType, HqlTranslator translator);
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<R> void cacheHqlInterpretation(Object cacheKey, HqlInterpretation<R> hqlInterpretation);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<R> SelectQueryPlan<R> resolveSelectQueryPlan(Key key, Supplier<SelectQueryPlan<R>> creator);
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default <K extends Key, R> SelectQueryPlan<R> resolveSelectQueryPlan(K key, Function<K, SelectQueryPlan<R>> creator) {
		return resolveSelectQueryPlan( key, () -> creator.apply( key ) );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NonSelectQueryPlan getNonSelectQueryPlan(Key key);
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void cacheNonSelectQueryPlan(Key key, NonSelectQueryPlan plan);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ParameterInterpretation resolveNativeQueryParameters(String queryString, Function<String, ParameterInterpretation> creator);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isEnabled();

	/**
	 * Close the cache when the SessionFactory is closed.
	 * <p>
	 * Note that depending on the cache strategy implementation chosen, clearing the cache might not reclaim all the
	 * memory.
	 * <p>
	 * Typically, when using LIRS, clearing the cache only invalidates the entries but the outdated entries are kept in
	 * memory until they are replaced by others. It is not considered a memory leak as the cache is bounded.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void close();

}
