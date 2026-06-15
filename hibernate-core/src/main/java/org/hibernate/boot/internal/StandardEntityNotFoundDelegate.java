/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.internal;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.proxy.EntityNotFoundDelegate;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Standard non-JPA implementation of {@link EntityNotFoundDelegate},
 * throwing the Hibernate-specific {@link ObjectNotFoundException}.
 *
 * @author Steve Ebersole
 */
public class StandardEntityNotFoundDelegate implements EntityNotFoundDelegate {
	/**
	 * Singleton access
	 */
	public static final StandardEntityNotFoundDelegate INSTANCE = new StandardEntityNotFoundDelegate();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void handleEntityNotFound(String entityName, Object id) {
		throw new ObjectNotFoundException( entityName, id );
	}
}
