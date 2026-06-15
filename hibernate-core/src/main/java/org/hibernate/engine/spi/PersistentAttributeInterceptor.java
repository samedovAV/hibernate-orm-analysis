/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.spi;

import java.util.Collections;
import java.util.Set;

import org.hibernate.Incubating;
import org.hibernate.bytecode.enhance.spi.LazyPropertyInitializer.InterceptorImplementor;
import org.hibernate.bytecode.enhance.spi.interceptor.BytecodeLazyAttributeInterceptor;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * The base contract for interceptors that can be injected into
 * enhanced entities for the purpose of intercepting attribute access
 *
 * @author Steve Ebersole
 *
 * @see PersistentAttributeInterceptable
 */
@Incubating
@SuppressWarnings("unused")
public interface PersistentAttributeInterceptor extends InterceptorImplementor {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean readBoolean(Object obj, String name, boolean oldValue);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean writeBoolean(Object obj, String name, boolean oldValue, boolean newValue);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	byte readByte(Object obj, String name, byte oldValue);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	byte writeByte(Object obj, String name, byte oldValue, byte newValue);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	char readChar(Object obj, String name, char oldValue);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	char writeChar(Object obj, String name, char oldValue, char newValue);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	short readShort(Object obj, String name, short oldValue);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	short writeShort(Object obj, String name, short oldValue, short newValue);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	int readInt(Object obj, String name, int oldValue);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	int writeInt(Object obj, String name, int oldValue, int newValue);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	float readFloat(Object obj, String name, float oldValue);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	float writeFloat(Object obj, String name, float oldValue, float newValue);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	double readDouble(Object obj, String name, double oldValue);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	double writeDouble(Object obj, String name, double oldValue, double newValue);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	long readLong(Object obj, String name, long oldValue);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	long writeLong(Object obj, String name, long oldValue, long newValue);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object readObject(Object obj, String name, Object oldValue);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object writeObject(Object obj, String name, Object oldValue, Object newValue);

	/**
	 * @deprecated Just as the method it overrides.  Interceptors that deal with
	 * lazy state should implement {@link BytecodeLazyAttributeInterceptor}
	 */
	@Deprecated
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default Set<String> getInitializedLazyAttributeNames() {
		return Collections.emptySet();
	}

	/**
	 * @deprecated Just as the method it overrides.  Interceptors that deal with
	 * lazy state should implement {@link BytecodeLazyAttributeInterceptor}
	 */
	@Override
	@Deprecated
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void attributeInitialized(String name) {
	}

	/**
	 *
	 * Callback from the enhanced class that an attribute has been loaded
	 *
	 * @deprecated Interceptors that deal with
	 * 	 * lazy state should implement {@link BytecodeLazyAttributeInterceptor}
	 *
	 * @param fieldName
	 * @return true id the attribute is loaded false otherwise
	 */
	@Deprecated
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean isAttributeLoaded(String fieldName){
		return false;
	}
}
