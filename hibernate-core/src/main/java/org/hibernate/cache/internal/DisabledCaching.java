/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.cache.internal;

import jakarta.annotation.Nonnull;
import java.util.Set;

import jakarta.persistence.PersistenceException;
import org.hibernate.cache.cfg.spi.DomainDataRegionConfig;
import org.hibernate.cache.spi.CacheImplementor;
import org.hibernate.cache.spi.QueryResultsCache;
import org.hibernate.cache.spi.Region;
import org.hibernate.cache.spi.RegionFactory;
import org.hibernate.cache.spi.TimestampsCache;
import org.hibernate.cache.spi.access.CollectionDataAccess;
import org.hibernate.cache.spi.access.EntityDataAccess;
import org.hibernate.cache.spi.access.NaturalIdDataAccess;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.metamodel.model.domain.NavigableRole;

import static java.util.Collections.emptySet;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A {@link CacheImplementor} service used when the second-level cache is disabled.
 *
 * @author Steve Ebersole
 */
public class DisabledCaching implements CacheImplementor {
	private final SessionFactoryImplementor sessionFactory;
	private final RegionFactory regionFactory;

	public DisabledCaching(SessionFactoryImplementor sessionFactory) {
		this.sessionFactory = sessionFactory;
		this.regionFactory = sessionFactory.getServiceRegistry().getService( RegionFactory.class );
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SessionFactoryImplementor getSessionFactory() {
		return sessionFactory;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public RegionFactory getRegionFactory() {
		return regionFactory;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void prime(Set<DomainDataRegionConfig> cacheRegionConfigs) {
		// nothing to do
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean containsEntity(@Nonnull Class<?> entityClass, @Nonnull Object identifier) {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean containsEntity(@Nonnull String entityName, @Nonnull Object identifier) {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void evictEntityData(@Nonnull Class<?> entityClass, @Nonnull Object identifier) {
		// nothing to do
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void evictEntityData(@Nonnull String entityName, @Nonnull Object identifier) {
		// nothing to do
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void evictEntityData(@Nonnull Class<?> entityClass) {
		// nothing to do
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void evictEntityData(@Nonnull String entityName) {
		// nothing to do
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void evictEntityData() {
		// nothing to do
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void evictNaturalIdData(@Nonnull Class<?> entityClass) {
		// nothing to do
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void evictNaturalIdData(@Nonnull String entityName) {
		// nothing to do
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void evictNaturalIdData() {
		// nothing to do
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean containsCollection(@Nonnull String role, @Nonnull Object ownerIdentifier) {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void evictCollectionData(@Nonnull String role, @Nonnull Object ownerIdentifier) {
		// nothing to do
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void evictCollectionData(@Nonnull String role) {
		// nothing to do
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void evictCollectionData() {
		// nothing to do
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean containsQuery(@Nonnull String regionName) {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void evictDefaultQueryRegion() {
		// nothing to do
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void evictQueryRegion(@Nonnull String regionName) {
		// nothing to do
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void evictQueryRegions() {
		// nothing to do
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void evictRegion(@Nonnull String regionName) {
		// nothing to do
	}


	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void evictAll() {
		// nothing to do
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void evictAllRegions() {
		// nothing to do
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Region getRegion(String fullRegionName) {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public TimestampsCache getTimestampsCache() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public QueryResultsCache getDefaultQueryResultsCache() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public QueryResultsCache getQueryResultsCache(String regionName) {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public QueryResultsCache getQueryResultsCacheStrictly(String regionName) {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void close() {
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Set<String> getCacheRegionNames() {
		return emptySet();
	}

	@Override @Deprecated
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public EntityDataAccess getEntityRegionAccess(NavigableRole rootEntityName) {
		return null;
	}

	@Override @Deprecated
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public NaturalIdDataAccess getNaturalIdCacheRegionAccessStrategy(NavigableRole rootEntityName) {
		return null;
	}

	@Override @Deprecated
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public CollectionDataAccess getCollectionRegionAccess(NavigableRole collectionRole) {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean contains(@Nonnull Class<?> cls, @Nonnull Object primaryKey) {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void evict(@Nonnull Class<?> cls, @Nonnull Object primaryKey) {
		// nothing to do
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void evict(@Nonnull Class<?> cls) {
		// nothing to do
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <T> T unwrap(@Nonnull Class<T> type) {
		if ( type.isAssignableFrom( DisabledCaching.class ) ) {
			return type.cast( this );
		}
		else {
			throw new PersistenceException( "Hibernate cannot unwrap Cache as '" + type.getName() + "'" );
		}
	}
}
