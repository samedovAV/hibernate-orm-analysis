/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.internal.find;

import org.hibernate.Incubating;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/// Models the ability to get reference to an entity by a key, either primary or natural.
///
/// @apiNote It is obviously expected that the entity-type and all options are
/// defined on the implementors of this contract.
///
/// @param <T> The entity type.
///
/// @author Steve Ebersole
@Incubating
public interface GetReferenceOperation<T> {
	/// Perform the operation based on the given key.
	///
	/// @param key The primary or natural key by which to get the entity reference.
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T performGetReference(Object key);
}
