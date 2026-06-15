/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.type;

import java.util.Iterator;
import java.util.Map;

import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.metamodel.CollectionClassification;
import org.hibernate.persister.collection.CollectionPersister;
import org.hibernate.resource.beans.spi.ManagedBean;
import org.hibernate.usertype.LoggableUserType;
import org.hibernate.usertype.UserCollectionType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A custom type for mapping user-written classes that implement {@code PersistentCollection}
 *
 * @see PersistentCollection
 * @see UserCollectionType
 * @author Gavin King
 */
public class CustomCollectionType extends CollectionType {

	private final UserCollectionType userType;
	private final boolean customLogging;

	public CustomCollectionType(
			ManagedBean<? extends UserCollectionType> userTypeBean,
			String role,
			String foreignKeyPropertyName) {
		super(role, foreignKeyPropertyName );
		userType = userTypeBean.getBeanInstance();
		customLogging = userType instanceof LoggableUserType;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<?> getReturnedClass() {
		return userType.instantiate( -1 ).getClass();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public CollectionClassification getCollectionClassification() {
		return userType.getClassification();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public PersistentCollection<?> instantiate(SharedSessionContractImplementor session, CollectionPersister persister, Object key) {
		return userType.instantiate( session, persister );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public PersistentCollection<?> wrap(SharedSessionContractImplementor session, Object collection) {
		return userType.wrap( session, collection );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Object instantiate(int anticipatedType) {
		return userType.instantiate( anticipatedType );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Iterator<?> getElementsIterator(Object collection) {
		return userType.getElementsIterator(collection);
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean contains(Object collection, Object entity, SharedSessionContractImplementor session) {
		return userType.contains( collection, entity );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Object indexOf(Object collection, Object entity) {
		return userType.indexOf(collection, entity);
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Object replaceElements(
			Object original,
			Object target,
			Object owner,
			Map<Object,Object> copyCache,
			SharedSessionContractImplementor session) {
		final var collectionDescriptor =
				session.getFactory().getMappingMetamodel()
						.getCollectionDescriptor( getRole() );
		return userType.replaceElements( original, target, collectionDescriptor, owner, copyCache, session );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	protected String renderLoggableString(Object value, SessionFactoryImplementor factory) {
		if ( customLogging ) {
			return ( (LoggableUserType) userType ).toLoggableString( value, factory );
		}
		else {
			return super.renderLoggableString( value, factory );
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public UserCollectionType getUserType() {
		return userType;
	}
}
