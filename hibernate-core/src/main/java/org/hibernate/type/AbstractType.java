/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.type;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Abstract superclass of the built-in {@link Type} hierarchy.
 *
 * @author Gavin King
 */
public abstract class AbstractType implements Type {

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isAssociationType() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isCollectionType() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isComponentType() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isEntityType() {
		return false;
	}

	@Override @SuppressWarnings({"rawtypes", "unchecked"})
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int compare(Object x, Object y) {
		return ( (Comparable) x ).compareTo(y);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Serializable disassemble(Object value, SharedSessionContractImplementor session, Object owner)
			throws HibernateException {
		return value == null ? null : (Serializable) deepCopy( value, session.getFactory() );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Serializable disassemble(Object value, SessionFactoryImplementor sessionFactory)
			throws HibernateException {
		return value == null ? null : (Serializable) deepCopy( value, sessionFactory );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object assemble(Serializable cached, SharedSessionContractImplementor session, Object owner)
	throws HibernateException {
		return cached == null ? null : deepCopy( cached, session.getFactory() );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isDirty(Object old, Object current, SharedSessionContractImplementor session)
			throws HibernateException {
		return !isSame( old, current );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isAnyType() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isModified(Object old, Object current, boolean[] checkable, SharedSessionContractImplementor session)
			throws HibernateException {
		return isDirty( old, current, session );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isSame(Object x, Object y) throws HibernateException {
		return isEqual(x, y );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isEqual(Object x, Object y) {
		return Objects.equals( x, y );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getHashCode(Object x) {
		return x.hashCode();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isEqual(Object x, Object y, SessionFactoryImplementor factory) {
		return isEqual( x, y );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public int getHashCode(Object x, SessionFactoryImplementor factory) {
		return getHashCode( x );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Object replace(
			Object original,
			Object target,
			SharedSessionContractImplementor session,
			Object owner,
			Map<Object, Object> copyCache,
			ForeignKeyDirection foreignKeyDirection)
	throws HibernateException {
		return needsReplacement( foreignKeyDirection ) ? replace( original, target, session, owner, copyCache ) : target;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private boolean needsReplacement(ForeignKeyDirection foreignKeyDirection) {
		// Collection and OneToOne are the only associations that could be TO_PARENT
		if ( this instanceof CollectionType || this instanceof OneToOneType ) {
			final var associationType = (AssociationType) this;
			return associationType.getForeignKeyDirection() == foreignKeyDirection;
		}
		else {
			return ForeignKeyDirection.FROM_PARENT == foreignKeyDirection;
		}
	}

}
