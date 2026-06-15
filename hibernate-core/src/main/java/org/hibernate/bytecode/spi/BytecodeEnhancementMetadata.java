/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.bytecode.spi;

import org.hibernate.bytecode.enhance.spi.interceptor.BytecodeLazyAttributeInterceptor;
import org.hibernate.bytecode.enhance.spi.interceptor.LazyAttributeLoadingInterceptor;
import org.hibernate.bytecode.enhance.spi.interceptor.LazyAttributesMetadata;
import org.hibernate.engine.spi.EntityKey;
import org.hibernate.engine.spi.PersistentAttributeInterceptable;
import org.hibernate.engine.spi.PersistentAttributeInterceptor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;

import jakarta.annotation.Nullable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Encapsulates bytecode enhancement information about a particular entity.
 *
 * @author Steve Ebersole
 */
public interface BytecodeEnhancementMetadata {
	/**
	 * The name of the entity to which this metadata applies.
	 *
	 * @return The entity name
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getEntityName();

	/**
	 * Has the entity class been bytecode enhanced for lazy loading?
	 *
	 * @return {@code true} indicates the entity class is enhanced for Hibernate use
	 * in lazy loading; {@code false} indicates it is not
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isEnhancedForLazyLoading();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	LazyAttributesMetadata getLazyAttributesMetadata();

	/**
	 * Create an "enhancement as proxy" instance for the given entity
	 *
	 * @apiNote The `addEmptyEntry` parameter is used to avoid creation of `EntityEntry` instances when we
	 * do not need them. - mainly from StatelessSession
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	PersistentAttributeInterceptable createEnhancedProxy(
			EntityKey keyToLoad, boolean addEmptyEntry,
			SharedSessionContractImplementor session);

	/**
	 * Build and inject an interceptor instance into the enhanced entity.
	 *
	 * @param entity The entity into which built interceptor should be injected
	 * @param identifier
	 * @param session The session to which the entity instance belongs.
	 *
	 * @return The built and injected interceptor
	 *
	 * @throws NotInstrumentedException Thrown if {@link #isEnhancedForLazyLoading()} returns {@code false}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	LazyAttributeLoadingInterceptor injectInterceptor(
			Object entity,
			Object identifier,
			SharedSessionContractImplementor session)
				throws NotInstrumentedException;

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void injectInterceptor(
			Object entity,
			PersistentAttributeInterceptor interceptor,
			SharedSessionContractImplementor session);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void injectEnhancedEntityAsProxyInterceptor(
			Object entity,
			EntityKey entityKey,
			SharedSessionContractImplementor session);

	/**
	 * Extract the field interceptor instance from the enhanced entity.
	 *
	 * @param entity The entity from which to extract the interceptor
	 *
	 * @return The extracted interceptor
	 *
	 * @throws NotInstrumentedException Thrown if {@link #isEnhancedForLazyLoading()} returns {@code false}
	 */
	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	LazyAttributeLoadingInterceptor extractInterceptor(Object entity)
			throws NotInstrumentedException;

	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	BytecodeLazyAttributeInterceptor extractLazyInterceptor(Object entity)
			throws NotInstrumentedException;

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean hasUnFetchedAttributes(Object entity);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isAttributeLoaded(Object entity, String attributeName);
}
