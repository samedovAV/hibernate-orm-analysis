/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.action.queue.spi.meta;

import org.hibernate.Incubating;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/// @author Steve Ebersole
/// @since 8.0
@Incubating
@FunctionalInterface
public interface TableInclusionChecker {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean include(TableDescriptor tableDescriptor);
}
