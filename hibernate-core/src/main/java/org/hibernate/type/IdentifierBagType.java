/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.type;

import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.collection.spi.PersistentIdentifierBag;
import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.metamodel.CollectionClassification;
import org.hibernate.persister.collection.CollectionPersister;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

public class IdentifierBagType extends CollectionType {

	public IdentifierBagType(String role, String propertyRef) {
		super(role, propertyRef );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public CollectionClassification getCollectionClassification() {
		return CollectionClassification.ID_BAG;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PersistentCollection<?> instantiate(
			SharedSessionContractImplementor session,
			CollectionPersister persister, Object key)
		throws HibernateException {

		return new PersistentIdentifierBag<>( session );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object instantiate(int anticipatedSize) {
		return anticipatedSize <= 0 ? new ArrayList<>() : new ArrayList<>( anticipatedSize + 1 );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<?> getReturnedClass() {
		return java.util.Collection.class;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PersistentCollection<?> wrap(SharedSessionContractImplementor session, Object collection) {
		return new PersistentIdentifierBag<>( session, (java.util.Collection<?>) collection );
	}

}
