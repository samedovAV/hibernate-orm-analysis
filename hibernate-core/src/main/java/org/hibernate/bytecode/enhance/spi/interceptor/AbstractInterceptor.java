/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.bytecode.enhance.spi.interceptor;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public abstract class AbstractInterceptor implements SessionAssociableInterceptor {

	private SessionAssociationMarkers sessionAssociation;

	protected AbstractInterceptor() {
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SharedSessionContractImplementor getLinkedSession() {
		return sessionAssociation != null ? sessionAssociation.session : null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setSession(SharedSessionContractImplementor session) {
		if ( session != null ) {
			sessionAssociation = session.getSessionAssociationMarkers();
		}
		else {
			unsetSession();
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void unsetSession() {
		if ( sessionAssociation != null ) {
			//We shouldn't mutate the original instance as it's shared across multiple entities,
			//but we can get a version of it which represents the same state except it doesn't have the session set:
			sessionAssociation = sessionAssociation.deAssociatedCopy();
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean allowLoadOutsideTransaction() {
		return sessionAssociation != null
			&& sessionAssociation.allowLoadOutsideTransaction;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getSessionFactoryUuid() {
		return sessionAssociation != null
				? sessionAssociation.sessionFactoryUuid
				: null;
	}

	/**
	 * Handle the case of reading an attribute.  The result is what is returned to the caller
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected abstract Object handleRead(Object target, String attributeName, Object value);

	/**
	 * Handle the case of writing an attribute.  The result is what is set as the entity state
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected abstract Object handleWrite(Object target, String attributeName, Object oldValue, Object newValue);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean readBoolean(Object obj, String name, boolean oldValue) {
		return (Boolean) handleRead( obj, name, oldValue );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean writeBoolean(Object obj, String name, boolean oldValue, boolean newValue) {
		return (Boolean) handleWrite( obj, name, oldValue, newValue );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public byte readByte(Object obj, String name, byte oldValue) {
		return (Byte) handleRead( obj, name, oldValue );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public byte writeByte(Object obj, String name, byte oldValue, byte newValue) {
		return (Byte) handleWrite( obj, name, oldValue, newValue );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public char readChar(Object obj, String name, char oldValue) {
		return (Character) handleRead( obj, name, oldValue );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public char writeChar(Object obj, String name, char oldValue, char newValue) {
		return (char) handleWrite( obj, name, oldValue, newValue );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public short readShort(Object obj, String name, short oldValue) {
		return (Short) handleRead( obj, name, oldValue );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public short writeShort(Object obj, String name, short oldValue, short newValue) {
		return (Short) handleWrite( obj, name, oldValue, newValue );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int readInt(Object obj, String name, int oldValue) {
		return (Integer) handleRead( obj, name, oldValue );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int writeInt(Object obj, String name, int oldValue, int newValue) {
		return (Integer) handleWrite( obj, name, oldValue, newValue );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public float readFloat(Object obj, String name, float oldValue) {
		return (Float) handleRead( obj, name, oldValue );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public float writeFloat(Object obj, String name, float oldValue, float newValue) {
		return (Float) handleWrite( obj, name, oldValue, newValue );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public double readDouble(Object obj, String name, double oldValue) {
		return (Double) handleRead( obj, name, oldValue );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public double writeDouble(Object obj, String name, double oldValue, double newValue) {
		return (Double) handleWrite( obj, name, oldValue, newValue );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public long readLong(Object obj, String name, long oldValue) {
		return (Long) handleRead( obj, name, oldValue );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public long writeLong(Object obj, String name, long oldValue, long newValue) {
		return (Long) handleWrite( obj, name, oldValue, newValue );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object readObject(Object obj, String name, Object oldValue) {
		return handleRead( obj, name, oldValue );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object writeObject(Object obj, String name, Object oldValue, Object newValue) {
		return handleWrite( obj, name, oldValue, newValue );
	}
}
