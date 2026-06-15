/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.event.spi;

import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;

import java.io.Serializable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Base class for events which are generated from a {@link org.hibernate.Session}
 * or {@linkplain org.hibernate.StatelessSession}.
 *
 * @author Steve Ebersole
 */
public abstract class AbstractEvent implements Serializable {
	protected final SharedSessionContractImplementor source;

	public AbstractEvent(SharedSessionContractImplementor source) {
		this.source = source;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SharedSessionContractImplementor getSession() {
		return source;
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SessionFactoryImplementor getFactory() {
		return source.getFactory();
	}
}
