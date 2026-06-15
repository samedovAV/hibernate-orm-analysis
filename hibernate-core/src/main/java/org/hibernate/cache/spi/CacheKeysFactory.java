/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.cache.spi;

import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.persister.collection.CollectionPersister;
import org.hibernate.persister.entity.EntityPersister;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A factory for keys into the second-level cache.
 *
 * @author Radim Vansa &lt;rvansa@redhat.com&gt;
 */
public interface CacheKeysFactory {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object createCollectionKey(Object id, CollectionPersister persister, SessionFactoryImplementor factory, String tenantIdentifier);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object createEntityKey(Object id, EntityPersister persister, SessionFactoryImplementor factory, String tenantIdentifier);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object createNaturalIdKey(Object naturalIdValues, EntityPersister persister, SharedSessionContractImplementor session);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object getEntityId(Object cacheKey);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object getCollectionId(Object cacheKey);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object getNaturalIdValues(Object cacheKey);
}
