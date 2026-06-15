/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.cache.internal;

import org.hibernate.cache.spi.CacheKeysFactory;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.persister.collection.CollectionPersister;
import org.hibernate.persister.entity.EntityPersister;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Factory that does not fill in the entityName or role
 *
 * @author Radim Vansa &lt;rvansa@redhat.com&gt;
 */
public class SimpleCacheKeysFactory implements CacheKeysFactory {
	public static final String SHORT_NAME = "simple";
	public static CacheKeysFactory INSTANCE = new SimpleCacheKeysFactory();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object createCollectionKey(Object id, CollectionPersister persister, SessionFactoryImplementor factory, String tenantIdentifier) {
		return id;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object createEntityKey(Object id, EntityPersister persister, SessionFactoryImplementor factory, String tenantIdentifier) {
		return id;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object createNaturalIdKey(
			Object naturalIdValues,
			EntityPersister persister,
			SharedSessionContractImplementor session) {
		// natural ids always need to be wrapped
		return NaturalIdCacheKey.from( naturalIdValues, persister, null, session );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object getEntityId(Object cacheKey) {
		return cacheKey;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object getCollectionId(Object cacheKey) {
		return cacheKey;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Object getNaturalIdValues(Object cacheKey) {
		return ((NaturalIdCacheKey) cacheKey).getNaturalIdValues();
	}
}
