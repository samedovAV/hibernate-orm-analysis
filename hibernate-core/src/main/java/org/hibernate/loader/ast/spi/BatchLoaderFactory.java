/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.loader.ast.spi;

import org.hibernate.engine.spi.LoadQueryInfluencers;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.metamodel.mapping.EntityMappingType;
import org.hibernate.metamodel.mapping.PluralAttributeMapping;
import org.hibernate.service.Service;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Factory for {@link BatchLoader} instances
 *
 * @author Steve Ebersole
 */
public interface BatchLoaderFactory extends Service {
	/**
	 * Create a BatchLoader for batch-loadable entities.
	 *
	 * @param domainBatchSize The total number of entities (max) that will be need to be initialized
	 * @param entityDescriptor The entity mapping metadata
	 * @deprecated Use {@link #createEntityBatchLoader(int, EntityMappingType, LoadQueryInfluencers)} instead
	 */
	@Deprecated(forRemoval = true)
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default <T> EntityBatchLoader<T> createEntityBatchLoader(
			int domainBatchSize,
			EntityMappingType entityDescriptor,
			SessionFactoryImplementor factory) {
		return createEntityBatchLoader( domainBatchSize, entityDescriptor, new LoadQueryInfluencers( factory ) );
	}

	/**
	 * Create a BatchLoader for batch-loadable entities.
	 *
	 * @param domainBatchSize The total number of entities (max) that will be need to be initialized
	 * @param entityDescriptor The entity mapping metadata
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> EntityBatchLoader<T> createEntityBatchLoader(
			int domainBatchSize,
			EntityMappingType entityDescriptor,
			LoadQueryInfluencers loadQueryInfluencers);

	/**
	 * Create a BatchLoader for batch-loadable collections.
	 *
	 * @param domainBatchSize The total number of collections (max) that will be initialized for any {@link CollectionBatchLoader#load}
	 * @param influencers Any load query influencers (filters, fetch-profiles, ...) to apply to the SQL
	 * @param attributeMapping The collection mapping metadata
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CollectionBatchLoader createCollectionBatchLoader(
			int domainBatchSize,
			LoadQueryInfluencers influencers,
			PluralAttributeMapping attributeMapping,
			SessionFactoryImplementor factory);
}
