/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.jdbc.proxy;

import java.sql.Blob;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Contract for {@link Blob} wrappers.
 *
 * @author Steve Ebersole
 */
public interface WrappedBlob {
	/**
	 * Retrieve the wrapped {@link Blob} reference
	 *
	 * @return The wrapped {@link Blob} reference
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Blob getWrappedBlob();
}
