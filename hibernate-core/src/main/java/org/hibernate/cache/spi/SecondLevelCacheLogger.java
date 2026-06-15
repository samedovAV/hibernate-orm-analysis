/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.cache.spi;

import org.hibernate.Internal;
import org.hibernate.cache.spi.access.AccessType;
import org.hibernate.internal.log.SubSystemLogging;

import org.jboss.logging.BasicLogger;
import org.jboss.logging.Logger;
import org.jboss.logging.annotations.LogMessage;
import org.jboss.logging.annotations.Message;
import org.jboss.logging.annotations.MessageLogger;
import org.jboss.logging.annotations.ValidIdRange;

import java.lang.invoke.MethodHandles;
import java.util.UUID;
import java.util.Locale;

import static org.hibernate.cfg.CacheSettings.CACHE_REGION_FACTORY;
import static org.jboss.logging.Logger.Level.DEBUG;
import static org.jboss.logging.Logger.Level.INFO;
import static org.jboss.logging.Logger.Level.TRACE;
import static org.jboss.logging.Logger.Level.WARN;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
@MessageLogger( projectCode = "HHH" )
@ValidIdRange( min = 90001001, max = 90002000 )
@SubSystemLogging(
		name = SecondLevelCacheLogger.LOGGER_NAME,
		description = "Logging related to the second-level cache"
)
@Internal
public interface SecondLevelCacheLogger extends BasicLogger {
	String LOGGER_NAME = SubSystemLogging.BASE + ".cache";

	SecondLevelCacheLogger L2CACHE_LOGGER = Logger.getMessageLogger( MethodHandles.lookup(), SecondLevelCacheLogger.class, LOGGER_NAME, Locale.ROOT );

	int NAMESPACE = 90001000;

	@LogMessage(level = WARN)
	@Message(
			value = "Attempt to start an already-started RegionFactory, probably because a SessionFactory was not closed." +
					" Using previously created RegionFactory.",
			id = NAMESPACE + 1
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void attemptToStartAlreadyStartedCacheProvider();

	@LogMessage(level = WARN)
	@Message(
			value = "Attempt to stop an already-stopped JCacheRegionFactory.",
			id = NAMESPACE + 2
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void attemptToStopAlreadyStoppedCacheProvider();

	@LogMessage( level = WARN )
	@Message(
			value = "Read-only caching was requested for mutable entity [%s]",
			id = NAMESPACE + 3
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void readOnlyCachingMutableEntity(String entity);

	@LogMessage( level = WARN )
	@Message(
			value = "Read-only caching was requested for entity [%s] with mutable natural id",
			id = NAMESPACE + 4
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void readOnlyCachingMutableNaturalId(String entity);

	@LogMessage(level = INFO)
	@Message(
			value = "A soft-locked cache entry in region [%s] with key [%s] was expired by the underlying cache." +
					" If this happens regularly, consider increasing the cache timeouts and/or capacity limits.",
			id = NAMESPACE + 5
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void softLockedCacheExpired(String regionName, Object key);

	@LogMessage(level = WARN)
	@Message(
			value = "Missing cache region [%1$s] was created with provider-specific default policies." +
					" Explicitly configure the region and its policies, or disable this warning by setting '%2$s' to '%3$s'.",
			id = NAMESPACE + 6
	)
	@SuppressWarnings( "unused" ) // used by hibernate-jcache
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void missingCacheCreated(String regionName, String configurationPropertyToDisableKey, String configurationPropertyToDisableValue);

	@LogMessage(level = WARN)
	@Message(
			value = "Using legacy cache name [%2$s] because configuration could not be found for cache [%1$s]." +
					" Update configuration to rename cache [%2$s] to [%1$s].",
			id = NAMESPACE + 7
	)
	@SuppressWarnings( "unused" ) // used by hibernate-jcache
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void usingLegacyCacheName(String currentName, String legacyName);

	@LogMessage(level = WARN)
	@Message(
			value = "Cache region [%1$s] has the access type '%2$s' which is not supported by [%3$s]." +
					" Ensure cache implementation supports JTA transactions.",
			id = NAMESPACE + 8
	)
	@SuppressWarnings( "unused" ) // used by hibernate-jcache
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void nonStandardSupportForAccessType(String regionName, String accessType, String regionFactoryClass);

	@LogMessage(level = TRACE)
	@Message(
			value = "Caching query results in region '%s' with timestamp %s",
			id = NAMESPACE + 9
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void cachingQueryResults(String regionName, long timestamp);

	@LogMessage(level = TRACE)
	@Message(
			value = "Checking cached query results in region '%s'",
			id = NAMESPACE + 10
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void checkingCachedQueryResults(String regionName);

	@LogMessage(level = TRACE)
	@Message(
			value = "Query results were not found in cache",
			id = NAMESPACE + 11
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void queryResultsNotFound();

	@LogMessage(level = TRACE)
	@Message(
			value = "Cached query results were stale",
			id = NAMESPACE + 12
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void cachedQueryResultsStale();

	@LogMessage(level = TRACE)
	@Message(
		value = "Returning cached query results",
		id = NAMESPACE + 14
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void returningCachedQueryResults();

	@LogMessage(level = TRACE)
	@Message(
		value = "Evicting entity second-level cache: %s",
		id = NAMESPACE + 15
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void evictingEntityCache(String entityInfo);

	@LogMessage(level = TRACE)
	@Message(
		value = "Evicting entity second-level cache: %s",
		id = NAMESPACE + 16
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void evictingEntityCacheByRole(String role);

	@LogMessage(level = TRACE)
	@Message(
		value = "Evicting natural id cache: %s",
		id = NAMESPACE + 17
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void evictingNaturalIdCache(String role);

	@LogMessage(level = TRACE)
	@Message(
		value = "Evicting collection second-level cache: %s",
		id = NAMESPACE + 18
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void evictingCollectionCache(String collectionInfo);

	@LogMessage(level = TRACE)
	@Message(
		value = "Evicting collection second-level cache: %s",
		id = NAMESPACE + 19
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void evictingCollectionCacheByRole(String role);

	@LogMessage(level = TRACE)
	@Message(
			value = "Auto-evicting collection from second-level cache: %s"
					+ " (since 'hibernate.cache.auto_evict_collection_cache' is enabled)",
			id = NAMESPACE + 25
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void autoEvictingCollectionCache(String collectionInfo);

	@LogMessage(level = TRACE)
	@Message(
			value = "Auto-evicting collection from second-level cache: %s"
					+ " (since 'hibernate.cache.auto_evict_collection_cache' is enabled)",
			id = NAMESPACE + 26
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void autoEvictingCollectionCacheByRole(String collectionRole);

	@LogMessage(level = TRACE)
	@Message(
		value = "Evicting query cache region: %s",
		id = NAMESPACE + 20
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void evictingQueryCacheRegion(String regionName);

	@LogMessage(level = TRACE)
	@Message(
		value = "Evicting cache of all query regions",
		id = NAMESPACE + 21
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void evictingAllQueryRegions();

	@LogMessage(level = TRACE)
	@Message(
		value = "Pre-invalidating space [%s], timestamp: %s",
		id = NAMESPACE + 22
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void preInvalidatingSpace(String space, Long timestamp);

	@LogMessage(level = TRACE)
	@Message(
		value = "Invalidating space [%s], timestamp: %s",
		id = NAMESPACE + 23
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void invalidatingSpace(String space, Long timestamp);

	@LogMessage(level = TRACE)
	@Message(
		value = "[%s] last update timestamp: %s, result set timestamp: %s",
		id = NAMESPACE + 24
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void lastUpdateTimestampForSpace(String space, Long lastUpdate, Long timestamp);

	@LogMessage(level = INFO)
	@Message(
			value = "Second-level cache region factory [%s]",
			id = NAMESPACE + 28
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void regionFactory(String name);

	@LogMessage(level = DEBUG)
	@Message(
			value = "Second-level cache disabled",
			id = NAMESPACE + 29
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void noRegionFactory();

	@LogMessage(level = DEBUG)
	@Message(
			value = "Cannot default RegionFactory based on registered strategies as %s RegionFactory strategies were registered"
					+ " (explicitly set '" + CACHE_REGION_FACTORY + "')",
			id = NAMESPACE + 30
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void cannotDefaultRegionFactory(int size);

	@LogMessage(level = DEBUG)
	@Message(
			value = "Cannot default RegionFactory since no RegionFactory strategies were registered",
			id = NAMESPACE + 31
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void noDefaultRegionFactory();

	@LogMessage(level = TRACE)
	@Message(
			value = "Generating entity cache access: %s [%s]",
			id = NAMESPACE + 32
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void generatingEntityAccess(String entityName, String accessType);

	@LogMessage(level = TRACE)
	@Message(
			value = "Generating collection cache access: %s [%s]",
			id = NAMESPACE + 33
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void generatingCollectionAccess(String role, String accessType);

	@LogMessage(level = TRACE)
	@Message(
			value = "Generating natural id access: %s [%s]",
			id = NAMESPACE + 34
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void generatingNaturalIdAccess(String role, String accessType);

	@LogMessage(level = TRACE)
	@Message(
			value = "DomainDataRegion created [%s]; key-factory = %s",
			id = NAMESPACE + 35
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void domainDataRegionCreated(String regionName, CacheKeysFactory keyFactory);

	@LogMessage(level = TRACE)
	@Message(
			value = "Clearing cache data map [region='%s']",
			id = NAMESPACE + 36
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void clearingCacheDataMap(String regionName);

	@LogMessage(level = TRACE)
	@Message(
			value = "Getting cached data from region ['%s' (%s)] by key [%s]",
			id = NAMESPACE + 37
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void gettingCachedData(String regionName, AccessType accessType, Object key);

	@LogMessage(level = TRACE)
	@Message(
			value = "Cache miss: region = '%s', key = '%s'",
			id = NAMESPACE + 38
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void cacheMiss(String regionName, Object key);

	@LogMessage(level = TRACE)
	@Message(
			value = "Cache hit: region = '%s', key = '%s'",
			id = NAMESPACE + 39
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void cacheHit(String regionName, Object key);

	@LogMessage(level = TRACE)
	@Message(
			value = "Cache hit, but item is unreadable/invalid: region = '%s', key = '%s'",
			id = NAMESPACE + 40
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void cacheHitUnreadable(String regionName, Object key);

	@LogMessage(level = TRACE)
	@Message(
			value = "Caching data from load [region='%s' (%s)] : key[%s] -> value[%s]",
			id = NAMESPACE + 41
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void cachingDataFromLoad(String regionName, AccessType accessType, Object key, Object value);

	@LogMessage(level = TRACE)
	@Message(
			value = "Cache put-from-load skipped due to minimal-put [region='%s' (%s), key='%s']",
			id = NAMESPACE + 42
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void cachePutFromLoadSkippedDueToMinimalPut(String regionName, AccessType accessType, Object key);

	@LogMessage(level = TRACE)
	@Message(
			value = "Cache put-from-load [region='%s' (%s), key='%s', value='%s'] failed due to being non-writable",
			id = NAMESPACE + 43
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void cachePutFromLoadFailedNonWritable(String regionName, AccessType accessType, Object key, Object value);

	@LogMessage(level = TRACE)
	@Message(
			value = "Locking cache item [region='%s' (%s)] : '%s' (timeout=%s, version=%s)",
			id = NAMESPACE + 44
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void lockingCacheItem(String regionName, AccessType accessType, Object key, long timeout, Object version);

	@LogMessage(level = TRACE)
	@Message(
			value = "Unlocking cache item [region='%s' (%s)] : %s",
			id = NAMESPACE + 45
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void unlockingCacheItem(String regionName, AccessType accessType, Object key);

	@LogMessage(level = TRACE)
	@Message(
			value = "Cached entry expired: %s",
			id = NAMESPACE + 46
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void cachedEntryExpired(Object key);

	@LogMessage(level = TRACE)
	@Message(
			value = "Checking readability of read-write cache item [timestamp='%s', version='%s'] : txTimestamp='%s'",
			id = NAMESPACE + 47
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void checkingReadWriteItemReadability(long timestamp, Object version, long txTimestamp);

	@LogMessage(level = TRACE)
	@Message(
			value = "Checking writeability of read-write cache item [timestamp='%s', version='%s'] : txTimestamp='%s', newVersion='%s'",
			id = NAMESPACE + 48
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void checkingReadWriteItemWriteability(long timestamp, Object version, long txTimestamp, Object newVersion);

	@LogMessage(level = TRACE)
	@Message(
			value = "Checking writeability of read-write cache lock [timeout='%s', lockId='%s', version='%s', sourceUuid=%s, multiplicity='%s', unlockTimestamp='%s'] : txTimestamp='%s', newVersion='%s'",
			id = NAMESPACE + 49
	)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void checkingReadWriteLockWriteability(
			long timeout,
			long lockId,
			Object version,
			UUID sourceUuid,
			int multiplicity,
			long unlockTimestamp,
			long txTimestamp,
			Object newVersion);
}
