/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.model.domain.internal;

import jakarta.annotation.Nullable;
import org.hibernate.query.sqm.SqmPathSource;
import org.hibernate.query.sqm.tree.domain.SqmPath;
import org.hibernate.spi.NavigablePath;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

public class PathHelper {
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public static NavigablePath append(SqmPath<?> lhs, SqmPathSource<?> rhs, @Nullable SqmPathSource<?> intermediatePathSource) {
		final var navigablePath = lhs.getNavigablePath();
		return intermediatePathSource == null
				? navigablePath.append( rhs.getPathName() )
				: navigablePath.append( intermediatePathSource.getPathName() ).append( rhs.getPathName() );
	}
}
