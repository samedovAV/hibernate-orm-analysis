/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.loader.ast.spi;

import org.hibernate.engine.spi.LoadQueryInfluencers;
import org.hibernate.metamodel.mapping.ModelPart;
import org.hibernate.sql.ast.tree.from.RootTableGroupProducer;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Common details for things that can be loaded by a {@linkplain Loader loader} - generally
 * {@linkplain org.hibernate.metamodel.mapping.EntityMappingType entities} and
 * {@linkplain org.hibernate.metamodel.mapping.PluralAttributeMapping plural attributes} (collections).
 *
 * @see Loader
 * @see org.hibernate.metamodel.mapping.EntityMappingType
 * @see org.hibernate.metamodel.mapping.PluralAttributeMapping
 *
 * @author Steve Ebersole
 */
public interface Loadable extends ModelPart, RootTableGroupProducer {
	/**
	 * The name for this loadable, for use as the root when generating
	 * {@linkplain org.hibernate.spi.NavigablePath relative paths}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getRootPathName();

	/**
	 * @deprecated Use {@link #isAffectedByInfluencers(LoadQueryInfluencers, boolean)} instead
	 */
	@Deprecated(forRemoval = true)
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default boolean isAffectedByInfluencers(LoadQueryInfluencers influencers) {
		return isAffectedByInfluencers( influencers, false );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean isAffectedByInfluencers(LoadQueryInfluencers influencers, boolean onlyApplyForLoadByKeyFilters) {
		return isAffectedByEntityGraph( influencers )
			|| isAffectedByEnabledFetchProfiles( influencers )
			|| isAffectedByEnabledFilters( influencers, onlyApplyForLoadByKeyFilters )
			|| isAffectedByBatchSize( influencers );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean isNotAffectedByInfluencers(LoadQueryInfluencers influencers) {
		return !isAffectedByEntityGraph( influencers )
			&& !isAffectedByEnabledFetchProfiles( influencers )
			&& !isAffectedByEnabledFilters( influencers )
			&& !isAffectedByBatchSize( influencers )
			&& influencers.getEnabledCascadingFetchProfile() == null;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean isAffectedByBatchSize(LoadQueryInfluencers influencers) {
		return influencers.hasBatchSizeOverride()
			|| influencers.getBatchSize() > 0
			&& influencers.getBatchSize() != getBatchSize();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	int getBatchSize();

	/**
	 * Whether any of the "influencers" affect this loadable.
	 * @deprecated Use {@link #isAffectedByEnabledFilters(LoadQueryInfluencers, boolean)} instead
	 */
	@Deprecated(forRemoval = true)
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default boolean isAffectedByEnabledFilters(LoadQueryInfluencers influencers) {
		return isAffectedByEnabledFilters( influencers, false );
	}

	/**
	 * Whether any of the "influencers" affect this loadable.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isAffectedByEnabledFilters(LoadQueryInfluencers influencers, boolean onlyApplyForLoadByKeyFilters);

	/**
	 * Whether the {@linkplain LoadQueryInfluencers#getEffectiveEntityGraph() effective entity-graph}
	 * applies to this loadable
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isAffectedByEntityGraph(LoadQueryInfluencers influencers);

	/**
	 * Whether any of the {@linkplain LoadQueryInfluencers#getEnabledFetchProfileNames()}
	 * apply to this loadable
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isAffectedByEnabledFetchProfiles(LoadQueryInfluencers influencers);
}
