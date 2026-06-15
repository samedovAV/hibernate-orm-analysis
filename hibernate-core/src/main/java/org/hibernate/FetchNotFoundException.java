/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate;

import java.util.Locale;
import jakarta.persistence.EntityNotFoundException;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Exception for {@link org.hibernate.annotations.NotFoundAction#EXCEPTION}.
 *
 * @see org.hibernate.annotations.NotFound
 *
 * @author Steve Ebersole
 */
public class FetchNotFoundException extends EntityNotFoundException {
	private final String entityName;
	private final Object identifier;

	public FetchNotFoundException(String entityName, Object identifier) {
		super(
				String.format(
						Locale.ROOT,
						"Entity `%s` with identifier value `%s` does not exist",
						entityName,
						identifier
				)
		);
		this.entityName = entityName;
		this.identifier = identifier;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getEntityName() {
		return entityName;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object getIdentifier() {
		return identifier;
	}
}
