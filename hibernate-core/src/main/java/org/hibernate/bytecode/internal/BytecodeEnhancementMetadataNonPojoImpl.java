/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.bytecode.internal;

import org.hibernate.bytecode.enhance.spi.interceptor.BytecodeLazyAttributeInterceptor;
import org.hibernate.bytecode.enhance.spi.interceptor.LazyAttributeLoadingInterceptor;
import org.hibernate.bytecode.enhance.spi.interceptor.LazyAttributesMetadata;
import org.hibernate.bytecode.spi.BytecodeEnhancementMetadata;
import org.hibernate.bytecode.spi.NotInstrumentedException;
import org.hibernate.engine.spi.EntityKey;
import org.hibernate.engine.spi.PersistentAttributeInterceptable;
import org.hibernate.engine.spi.PersistentAttributeInterceptor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;

import jakarta.annotation.Nullable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * BytecodeEnhancementMetadata implementation for non-POJO models, mainly
 * {@link org.hibernate.metamodel.RepresentationMode#MAP}
 *
 * @author Steve Ebersole
 */
public class BytecodeEnhancementMetadataNonPojoImpl implements BytecodeEnhancementMetadata {
	private final String entityName;
	private final LazyAttributesMetadata lazyAttributesMetadata;
	private final String errorMsg;

	public BytecodeEnhancementMetadataNonPojoImpl(String entityName) {
		this.entityName = entityName;
		this.lazyAttributesMetadata = LazyAttributesMetadata.nonEnhanced( entityName );
		this.errorMsg = "Entity [" + entityName + "] is non-pojo, and therefore not instrumented";
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getEntityName() {
		return entityName;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isEnhancedForLazyLoading() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public LazyAttributesMetadata getLazyAttributesMetadata() {
		return lazyAttributesMetadata;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public LazyAttributeLoadingInterceptor injectInterceptor(
			Object entity,
			Object identifier,
			SharedSessionContractImplementor session) throws NotInstrumentedException {
		throw new NotInstrumentedException( errorMsg );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void injectInterceptor(
			Object entity,
			PersistentAttributeInterceptor interceptor,
			SharedSessionContractImplementor session) {
		throw new NotInstrumentedException( errorMsg );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void injectEnhancedEntityAsProxyInterceptor(
			Object entity,
			EntityKey entityKey,
			SharedSessionContractImplementor session) {
		throw new NotInstrumentedException( errorMsg );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PersistentAttributeInterceptable createEnhancedProxy(EntityKey keyToLoad, boolean addEmptyEntry, SharedSessionContractImplementor session) {
		throw new NotInstrumentedException( errorMsg );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public @Nullable LazyAttributeLoadingInterceptor extractInterceptor(Object entity) throws NotInstrumentedException {
		throw new NotInstrumentedException( errorMsg );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public @Nullable BytecodeLazyAttributeInterceptor extractLazyInterceptor(Object entity) throws NotInstrumentedException {
		throw new NotInstrumentedException( errorMsg );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean hasUnFetchedAttributes(Object entity) {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isAttributeLoaded(Object entity, String attributeName) {
		return true;
	}

}
