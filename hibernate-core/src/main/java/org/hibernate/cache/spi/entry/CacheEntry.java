/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.cache.spi.entry;

import java.io.Serializable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A cached instance of a persistent class
 *
 * @author Gavin King
 * @author Steve Ebersole
 */
public interface CacheEntry extends Serializable {
	/**
	 * Does this entry represent a direct entity reference (rather than disassembled state)?
	 *
	 * @return true/false
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isReferenceEntry();

	/**
	 * Hibernate stores all entries pertaining to a given entity hierarchy in a single region.  This attribute
	 * tells us the specific entity type represented by the cached data.
	 *
	 * @return The entry's exact entity type.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getSubclass();

	/**
	 * Retrieves the version (optimistic locking) associated with this cache entry.
	 *
	 * @return The version of the entity represented by this entry
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object getVersion();

	/**
	 * Get the underlying disassembled state
	 *
	 * todo : this was added to support initializing an entity's EntityEntry snapshot during reattach;
	 * this should be refactored to instead expose a method to assemble an EntityEntry based on this
	 * state for return.
	 *
	 * @return The disassembled state
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Serializable[] getDisassembledState();
}
