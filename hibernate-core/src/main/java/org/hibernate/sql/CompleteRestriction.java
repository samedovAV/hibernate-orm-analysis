/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql;

import org.hibernate.Internal;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * For a complete predicate.  E.g. {@link org.hibernate.annotations.SQLRestriction}
 *
 * @author Steve Ebersole
 */
@Internal
public class CompleteRestriction implements Restriction {
	private final String predicate;

	public CompleteRestriction(String predicate) {
		this.predicate = predicate;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void render(StringBuilder sqlBuffer, RestrictionRenderingContext context) {
		sqlBuffer.append( predicate );
	}
}
