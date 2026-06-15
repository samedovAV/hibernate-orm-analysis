/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.type;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.collection.spi.PersistentList;
import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.metamodel.CollectionClassification;
import org.hibernate.persister.collection.CollectionPersister;

import static org.hibernate.metamodel.CollectionClassification.LIST;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

public class ListType extends CollectionType {

	public ListType(String role, String propertyRef) {
		super(role, propertyRef );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public CollectionClassification getCollectionClassification() {
		return LIST;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PersistentCollection<?> instantiate(SharedSessionContractImplementor session, CollectionPersister persister, Object key) {
		return new PersistentList<>( session );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<?> getReturnedClass() {
		return List.class;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PersistentCollection<?> wrap(SharedSessionContractImplementor session, Object collection) {
		return new PersistentList<>( session, (List<?>) collection );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object instantiate(int anticipatedSize) {
		return anticipatedSize <= 0 ? new ArrayList<>() : new ArrayList<>( anticipatedSize + 1 );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Object indexOf(Object collection, Object element) {
		final var list = (List<?>) collection;
		for ( int i=0; i<list.size(); i++ ) {
			//TODO: proxies!
			if ( list.get(i) == element ) {
				return i;
			}
		}
		return null;
	}
}
