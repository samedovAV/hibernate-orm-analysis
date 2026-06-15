/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.cache.spi.support;

import java.util.Comparator;

import jakarta.annotation.Nonnull;
import org.hibernate.cache.cfg.spi.CollectionDataCachingConfig;
import org.hibernate.cache.spi.CacheKeysFactory;
import org.hibernate.cache.spi.DomainDataRegion;
import org.hibernate.cache.spi.access.AccessType;
import org.hibernate.cache.spi.access.CollectionDataAccess;
import org.hibernate.cache.spi.access.SoftLock;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.persister.collection.CollectionPersister;
import com.samedov.annotation.Prove;/**
 * Standard support for {@link CollectionDataAccess}
 * using the {@link AccessType#READ_WRITE} access type.
 *
 * @author Chris Cranford
 * @author Steve Ebersole
 */
import com.samedov.annotation.Complexity;

public class CollectionReadWriteAccess extends AbstractReadWriteAccess implements CollectionDataAccess {
	private final Comparator<Object> versionComparator;
	private final CacheKeysFactory keysFactory;

	public CollectionReadWriteAccess(
			DomainDataRegion region,
			CacheKeysFactory keysFactory,
			DomainDataStorageAccess storageAccess,
			CollectionDataCachingConfig config) {
		super( region, storageAccess );
		this.keysFactory = keysFactory;
		this.versionComparator = config.getOwnerVersionComparator();
	}

	@Deprecated
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected AccessedDataClassification getAccessedDataClassification() {
		return AccessedDataClassification.COLLECTION;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public AccessType getAccessType() {
		return AccessType.READ_WRITE;
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object generateCacheKey(
			Object id,
			CollectionPersister collectionDescriptor,
			SessionFactoryImplementor factory,
			String tenantIdentifier) {
		return keysFactory.createCollectionKey( id, collectionDescriptor, factory, tenantIdentifier );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object getCacheKeyId(Object cacheKey) {
		return keysFactory.getCollectionId( cacheKey );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected Comparator<Object> getVersionComparator() {
		return versionComparator;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Object get(SharedSessionContractImplementor session, Object key) {
		return super.get( session, key );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean putFromLoad(SharedSessionContractImplementor session, Object key, Object value, Object version) {
		return super.putFromLoad( session, key, value, version );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SoftLock lockItem(
			SharedSessionContractImplementor session, Object key, Object version) {
		return super.lockItem( session, key, version );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void unlockItem(
			SharedSessionContractImplementor session, Object key, SoftLock lock) {
		super.unlockItem( session, key, lock );
	}
}
