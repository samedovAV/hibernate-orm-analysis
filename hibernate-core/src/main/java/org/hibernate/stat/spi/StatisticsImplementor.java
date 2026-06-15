/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.stat.spi;

import org.hibernate.metamodel.model.domain.NavigableRole;
import org.hibernate.service.Service;
import org.hibernate.stat.Statistics;

import java.util.Map;

import static java.util.Collections.emptyMap;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A service SPI for collecting statistics about various events occurring at runtime.
 * <p>
 * A custom implementation may be provided via a {@link StatisticsFactory}.
 *
 * @author Emmanuel Bernard
 */
public interface StatisticsImplementor extends Statistics, Service {
	/**
	 * Callback about a session being opened.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void openSession();

	/**
	 * Callback about a session being closed.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void closeSession();

	/**
	 * Callback about a flush occurring
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void flush();

	/**
	 * Callback about a connection being obtained from {@link org.hibernate.engine.jdbc.connections.spi.ConnectionProvider}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void connect();

	/**
	 * Callback about a statement being prepared.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void prepareStatement();

	/**
	 * Callback about a statement being closed.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void closeStatement();

	/**
	 * Callback about a transaction completing.
	 *
	 * @param success Was the transaction successful?
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void endTransaction(boolean success);

	/**
	 * Callback about an entity being loaded.  This might indicate a proxy or a fully initialized entity, but in either
	 * case it means without a separate SQL query being needed.
	 *
	 * @param entityName The name of the entity loaded.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void loadEntity(String entityName);

	/**
	 * Callback about an entity being fetched.  Unlike {@link #loadEntity} this indicates a separate query being
	 * performed.
	 *
	 * @param entityName The name of the entity fetched.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void fetchEntity(String entityName);

	/**
	 * Callback about an entity being updated.
	 *
	 * @param entityName The name of the entity updated.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void updateEntity(String entityName);

	/**
	 * Callback about an entity being upserted.
	 *
	 * @param entityName The name of the entity upserted.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void upsertEntity(String entityName);

	/**
	 * Callback about an entity being inserted
	 *
	 * @param entityName The name of the entity inserted
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void insertEntity(String entityName);

	/**
	 * Callback about an entity being deleted.
	 *
	 * @param entityName The name of the entity deleted.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void deleteEntity(String entityName);

	/**
	 * Callback about an optimistic lock failure on an entity
	 *
	 * @param entityName The name of the entity.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void optimisticFailure(String entityName);

	/**
	 * Callback about a collection loading.  This might indicate a lazy collection or an initialized collection being
	 * created, but in either case it means without a separate SQL query being needed.
	 *
	 * @param role The collection role.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void loadCollection(String role);

	/**
	 * Callback to indicate a collection being fetched.  Unlike {@link #loadCollection}, this indicates a separate
	 * query was needed.
	 *
	 * @param role The collection role.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void fetchCollection(String role);

	/**
	 * Callback indicating a collection was updated.
	 *
	 * @param role The collection role.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void updateCollection(String role);

	/**
	 * Callback indicating a collection recreation (full deletion + full (re-)insertion).
	 *
	 * @param role The collection role.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void recreateCollection(String role);

	/**
	 * Callback indicating a collection removal.
	 *
	 * @param role The collection role.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void removeCollection(String role);

	/**
	 * Callback indicating a put into second level cache.
	 *
	 * @apiNote {@code entityName} should be the root entity name
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void entityCachePut(NavigableRole entityName, String regionName);

	/**
	 * Callback indicating a get from second level cache resulted in a hit.
	 *
	 * @apiNote {@code entityName} should be the root entity name
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void entityCacheHit(NavigableRole entityName, String regionName);

	/**
	 * Callback indicating a get from second level cache resulted in a miss.
	 *
	 * @apiNote {@code entityName} should be the root entity name
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void entityCacheMiss(NavigableRole entityName, String regionName);

	/**
	 * Callback indicating a removal from second level cache.
	 *
	 * @apiNote {@code entityName} should be the root entity name
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void entityCacheRemove(NavigableRole rootEntityRole, String name);

	/**
	 * Callback indicating a put into second level cache.
	 *
	 * @param collectionRole The collection's "path"
	 * @param regionName The name of the cache region
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void collectionCachePut(NavigableRole collectionRole, String regionName);

	/**
	 * Callback indicating a get from second level cache resulted in a hit.
	 *
	 * @param collectionRole The collection's "path"
	 * @param regionName The name of the cache region
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void collectionCacheHit(NavigableRole collectionRole, String regionName);

	/**
	 * Callback indicating a get from second level cache resulted in a miss.
	 *
	 * @param collectionRole The collection's "path"
	 * @param regionName The name of the cache region
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void collectionCacheMiss(NavigableRole collectionRole, String regionName);

	/**
	 * Callback indicating a put into natural id cache.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void naturalIdCachePut(NavigableRole rootEntityName, String regionName);

	/**
	 * Callback indicating a get from natural id cache resulted in a hit.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void naturalIdCacheHit(NavigableRole rootEntityName, String regionName);

	/**
	 * Callback indicating a get from natural id cache resulted in a miss.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void naturalIdCacheMiss(NavigableRole rootEntityName, String regionName);

	/**
	 * Callback indicating execution of a natural id query
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void naturalIdQueryExecuted(String rootEntityName, long executionTime);

	/**
	 * Callback indicating a put into the query cache.
	 *
	 * @param hql The query
	 * @param regionName The cache region
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void queryCachePut(String hql, String regionName);

	/**
	 * Callback indicating a get from the query cache resulted in a hit.
	 *
	 * @param hql The query
	 * @param regionName The name of the cache region
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void queryCacheHit(String hql, String regionName);

	/**
	 * Callback indicating a get from the query cache resulted in a miss.
	 *
	 * @param hql The query
	 * @param regionName The name of the cache region
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void queryCacheMiss(String hql, String regionName);

	/**
	 * Callback indicating execution of a SQL or HQL query
	 *
	 * @param query The query
	 * @param rows Number of rows returned
	 * @param time execution time
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void queryExecuted(String query, int rows, long time);

	/**
	 * Callback indicating a hit to the timestamp cache
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void updateTimestampsCacheHit();

	/**
	 * Callback indicating a miss to the timestamp cache
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void updateTimestampsCacheMiss();

	/**
	 * Callback indicating a put to the timestamp cache
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void updateTimestampsCachePut();

	/**
	 * Callback indicating a get from the query plan cache resulted in a hit.
	 *
	 * @param query The query
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void queryPlanCacheHit(String query) {
		//For backward compatibility
	}

	/**
	 * Callback indicating a get from the query plan cache resulted in a miss.
	 *
	 * @param query The query
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void queryPlanCacheMiss(String query) {
		//For backward compatibility
	}

	/**
	 * Callback indicating compilation of a sql/hql query
	 *
	 * @param hql The query
	 * @param microseconds execution time
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void queryCompiled(String hql, long microseconds) {
		//For backward compatibility
	}

	/**
	 * Register the execution of a slow SQL query.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void slowQuery(String sql, long executionTime) {
		//For backward compatibility
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default Map<String, Long> getSlowQueries() {
		//For backward compatibility
		return emptyMap();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void normalizeNaturalId(String entityName) {
		//For backward compatibility
	}
}
