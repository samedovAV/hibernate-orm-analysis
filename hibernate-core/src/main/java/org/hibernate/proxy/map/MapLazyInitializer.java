/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.proxy.map;

import java.io.Serializable;
import java.util.Map;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.proxy.AbstractLazyInitializer;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Lazy initializer for "dynamic-map" entity representations.
 *
 * @author Gavin King
 */
public class MapLazyInitializer extends AbstractLazyInitializer implements Serializable {

	MapLazyInitializer(String entityName, Object id, SharedSessionContractImplementor session) {
		super( entityName, id, session );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Map getMap() {
		return (Map) getImplementation();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<?> getPersistentClass() {
		throw new UnsupportedOperationException("dynamic-map entity representation");
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<?> getImplementationClass() {
		throw new UnsupportedOperationException("dynamic-map entity representation");
	}

	// Expose the following methods to MapProxy by overriding them (so that classes in this package see them)

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	protected void prepareForPossibleLoadingOutsideTransaction() {
		super.prepareForPossibleLoadingOutsideTransaction();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	protected boolean isAllowLoadOutsideTransaction() {
		return super.isAllowLoadOutsideTransaction();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	protected String getSessionFactoryUuid() {
		return super.getSessionFactoryUuid();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	protected String getSessionFactoryName() {
		return super.getSessionFactoryName();
	}
}
