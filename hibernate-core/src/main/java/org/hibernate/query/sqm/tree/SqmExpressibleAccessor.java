/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.tree;

import jakarta.annotation.Nullable;
import org.hibernate.query.sqm.SqmBindableType;
import org.hibernate.query.sqm.SqmExpressible;
import org.hibernate.type.descriptor.java.JavaType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Accessor for {@link SqmExpressible}.
 *
 * @author Christian Beikov
 */
public interface SqmExpressibleAccessor<T> {
	/**
	 * The Java type descriptor for this node.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default @Nullable JavaType<T> getNodeJavaType() {
		final SqmExpressible<T> nodeType = getExpressible();
		return nodeType == null ? null : nodeType.getExpressibleJavaType();
	}

	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmBindableType<T> getExpressible();
}
