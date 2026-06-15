/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.bytecode.enhance.spi.interceptor;

import java.util.Set;

import org.hibernate.Incubating;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
@Incubating
public interface BytecodeLazyAttributeInterceptor extends SessionAssociableInterceptor {
	/**
	 * The name of the entity this interceptor is meant to intercept
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getEntityName();

	/**
	 * The id of the entity instance this interceptor is associated with
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object getIdentifier();

	/**
	 * The names of all lazy attributes which have been initialized
	 */
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Set<String> getInitializedLazyAttributeNames();

	/**
	 * Callback from the enhanced class that an attribute has been read or written
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void attributeInitialized(String name);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isAttributeLoaded(String fieldName);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean hasAnyUninitializedAttributes();

}
