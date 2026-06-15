/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.type;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.mapping.Component;
import org.hibernate.usertype.CompositeUserType;

import java.io.Serializable;
import java.util.Map;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Handles {@link CompositeUserType}s.
 *
 * @author Gavin King
 *
 * @since 6.3
 */
public class UserComponentType<T> extends ComponentType {

	private final CompositeUserType<T> compositeUserType;

	public UserComponentType(
			Component component,
			int[] originalPropertyOrder,
			CompositeUserType<T> compositeUserType) {
		super( component, originalPropertyOrder, compositeUserType.isMutable() );
		this.compositeUserType = compositeUserType;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isEqual(Object x, Object y) throws HibernateException {
		return x==y || compositeUserType.equals( (T) x, (T) y );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isEqual(Object x, Object y, SessionFactoryImplementor factory)
			throws HibernateException {
		return isEqual( x, y );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getHashCode(Object x) {
		return compositeUserType.hashCode( (T) x );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public int getHashCode(Object x, SessionFactoryImplementor factory) {
		return getHashCode( x );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Object deepCopy(Object component, SessionFactoryImplementor factory) {
		return component == null ? null : compositeUserType.deepCopy( (T) component );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Object replace(Object original, Object target, SharedSessionContractImplementor session, Object owner, Map<Object, Object> copyCache) {
		return original == null || !isMutable() ? original : compositeUserType.replace( (T) original, (T) target, owner );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Object replace(Object original, Object target, SharedSessionContractImplementor session, Object owner, Map<Object, Object> copyCache, ForeignKeyDirection foreignKeyDirection) {
		return replace( original, target, session, owner, copyCache );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Serializable disassemble(Object value, SessionFactoryImplementor sessionFactory)
			throws HibernateException {
		return value == null ? null : compositeUserType.disassemble( (T) value );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Serializable disassemble(Object value, SharedSessionContractImplementor session, Object owner)
			throws HibernateException {
		return disassemble( value, session.getFactory() );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Object assemble(Serializable object, SharedSessionContractImplementor session, Object owner)
			throws HibernateException {
		return object == null ? null : compositeUserType.assemble( object, owner );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object replacePropertyValues(Object component, Object[] values, SharedSessionContractImplementor session)
			throws HibernateException {
		return instantiator( component ).instantiate( () -> values );
	}
}
