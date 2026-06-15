/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.jdbc.batch.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Unique key for batch identification.
 *
 * @author Steve Ebersole
 */
public interface BatchKey {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default String toLoggableString() {
		return toString();
	}
}
