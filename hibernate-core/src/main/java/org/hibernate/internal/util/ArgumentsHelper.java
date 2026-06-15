/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.internal.util;

import jakarta.persistence.Reference;
import org.hibernate.query.spi.QueryImplementor;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

public class ArgumentsHelper {

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public static void bindReferenceArguments(QueryImplementor<?> query, Reference reference) {
		final var arguments = reference.getArguments();
		if ( arguments != null && !arguments.isEmpty() ) {
			final var parameterNames = reference.getParameterNames();
			if ( hasNamedParameters( query ) && parameterNames != null ) {
				for ( int i = 0; i < arguments.size(); i++ ) {
					query.setParameter( parameterNames.get( i ), arguments.get( i ) );
				}
			}
			else {
				for ( int i = 0; i < arguments.size(); i++ ) {
					query.setParameter( i + 1, arguments.get( i ) );
				}
			}
		}
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	private static boolean hasNamedParameters(QueryImplementor<?> query) {
		for ( var parameter : query.getParameters() ) {
			if ( parameter.getName() != null ) {
				return true;
			}
		}
		return false;
	}
}
