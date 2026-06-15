/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.mapping.internal;

import org.hibernate.Internal;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.cache.MutableCacheKeyBuilder;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.mapping.Collection;
import org.hibernate.metamodel.mapping.CollectionPart;
import org.hibernate.metamodel.mapping.EntityMappingType;
import org.hibernate.metamodel.mapping.NonTransientException;
import org.hibernate.persister.collection.CollectionPersister;
import org.hibernate.sql.results.graph.entity.EntityValuedFetchable;
import org.hibernate.type.descriptor.java.JavaType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * An entity-valued collection-part.
 *
 * @apiNote This mapping does not include {@linkplain DiscriminatedCollectionPart "ANY"} mappings
 *
 * @implSpec Allows for 2-phase initialization via {@link #finishInitialization}
 *
 * @author Steve Ebersole
 */
public interface EntityCollectionPart extends CollectionPart, EntityValuedFetchable {

	enum Cardinality { ONE_TO_MANY, MANY_TO_MANY }

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Cardinality getCardinality();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NotFoundAction getNotFoundAction();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EntityMappingType getAssociatedEntityMappingType();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default String getFetchableName() {
		return getPartName();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default EntityMappingType getPartMappingType() {
		return getAssociatedEntityMappingType();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default EntityMappingType getEntityMappingType() {
		return getAssociatedEntityMappingType();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default JavaType<?> getJavaType() {
		return getAssociatedEntityMappingType().getJavaType();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default JavaType<?> getExpressibleJavaType() {
		return getJavaType();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default int getJdbcTypeCount() {
		return CollectionPart.super.getJdbcTypeCount();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default void addToCacheKey(MutableCacheKeyBuilder cacheKey, Object value, SharedSessionContractImplementor session) {
		EntityValuedFetchable.super.addToCacheKey( cacheKey, value, session );
	}

	/**
	 * Perform any delayed initialization.
	 * <p>
	 * The initialization is considered successful if the result is {@code true}.  It is
	 * considered unsuccessful if the result is {@code false} or an exception is thrown.
	 * Unsuccessful initializations are generally retried "later", to allow waiting for
	 * model-parts being available e.g.
	 * <p>
	 * If the exception is something that will just never succeed, consider throwing
	 * an exception with the {@link NonTransientException} marker to allow the creation
	 * process to stop immediately
	 */
	@Internal
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean finishInitialization(
			CollectionPersister collectionDescriptor,
			Collection bootValueMapping,
			String fkTargetModelPartName,
			MappingModelCreationProcess creationProcess);
}
