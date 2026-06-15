/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.type;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.collection.spi.PersistentArrayHolder;
import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.internal.build.AllowReflection;
import org.hibernate.metamodel.CollectionClassification;
import org.hibernate.persister.collection.CollectionPersister;

import static org.hibernate.Hibernate.isInitialized;
import static org.hibernate.bytecode.enhance.spi.LazyPropertyInitializer.UNFETCHED_PROPERTY;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A type for persistent arrays.
 * @author Gavin King
 */
@AllowReflection
public class ArrayType extends CollectionType {

	private final Class<?> elementClass;
	private final Class<?> arrayClass;

	public ArrayType(String role, String propertyRef, Class<?> elementClass) {
		super(role, propertyRef );
		this.elementClass = elementClass;
		arrayClass = elementClass.arrayType();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<?> getReturnedClass() {
		return arrayClass;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public CollectionClassification getCollectionClassification() {
		return CollectionClassification.ARRAY;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PersistentCollection<?> instantiate(SharedSessionContractImplementor session, CollectionPersister persister, Object key)
			throws HibernateException {
		return new PersistentArrayHolder<>(session, persister);
	}

	/**
	 * Not defined for collections of primitive type
	 */
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Iterator<?> getElementsIterator(Object collection) {
		return Arrays.asList( (Object[]) collection ).iterator();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PersistentCollection<?> wrap(SharedSessionContractImplementor session, Object array) {
		return new PersistentArrayHolder<>(session, array);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isArrayType() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String toLoggableString(Object value, SessionFactoryImplementor factory) throws HibernateException {
		if ( value == null ) {
			return "null";
		}
		else {
			final int length = Array.getLength( value );
			final Type elemType = getElementType( factory );
			final List<String> list = new ArrayList<>( length );
			for ( int i = 0; i < length; i++ ) {
				list.add( loggableString( factory, Array.get( value, i ), elemType ) );
			}
			return list.toString();
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private static String loggableString(SessionFactoryImplementor factory, Object element, Type elemType) {
		return element == UNFETCHED_PROPERTY || !isInitialized( element )
				? "<uninitialized>"
				: elemType.toLoggableString( element, factory );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object instantiateResult(Object original) {
		return Array.newInstance( elementClass, Array.getLength(original) );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Object replaceElements(
		Object original,
		Object target,
		Object owner,
		Map<Object, Object> copyCache,
		SharedSessionContractImplementor session) throws HibernateException {

		final int length = Array.getLength(original);
		if ( length!=Array.getLength(target) ) {
			//note: this affects the return value!
			target=instantiateResult(original);
		}

		final Type elemType = getElementType( session.getFactory() );
		for ( int i=0; i<length; i++ ) {
			Array.set( target, i, elemType.replace( Array.get(original, i), null, session, owner, copyCache ) );
		}

		return target;

	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object instantiate(int anticipatedSize) {
		throw new UnsupportedOperationException();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Object indexOf(Object array, Object element) {
		final int length = Array.getLength(array);
		for ( int i=0; i<length; i++ ) {
			//TODO: proxies!
			if ( Array.get(array, i) == element ) {
				return i;
			}
		}
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected boolean initializeImmediately() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean hasHolder() {
		return true;
	}

}
