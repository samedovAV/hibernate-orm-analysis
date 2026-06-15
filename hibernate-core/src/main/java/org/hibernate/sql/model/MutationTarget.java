/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.model;

import org.hibernate.Incubating;
import org.hibernate.action.queue.spi.meta.TableDescriptor;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Bridge interface combining legacy and graph-based mutation target contracts.
 * <p>
 * This interface exists to maintain compatibility during the transition from the
 * legacy sequential action queue to the graph-based action queue. Implementations
 * (like {@code AbstractEntityPersister}) provide both sets of methods, allowing
 * them to work with both queue implementations.
 * <p>
 * New code should prefer using the more specific {@link LegacyMutationTarget} or
 * {@link GraphMutationTarget} interfaces directly.
 *
 * @author Steve Ebersole
 *
 * @deprecated Transitional interface - use {@link LegacyMutationTarget} for legacy
 * coordinators or {@link GraphMutationTarget} for graph-based decomposers
 */
@Deprecated(since = "8.0", forRemoval = true)
@Incubating
public interface MutationTarget<T extends TableMapping, TD extends TableDescriptor>
		extends LegacyMutationTarget<T>, GraphMutationTarget<TD> {

	// Bridge interface - all methods inherited from LegacyMutationTarget and GraphMutationTarget

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default String getRolePath() {
		return getNavigableRole().getFullPath();
	}
}
