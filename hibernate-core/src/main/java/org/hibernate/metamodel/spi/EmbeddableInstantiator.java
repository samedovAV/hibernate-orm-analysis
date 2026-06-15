/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.spi;

import org.hibernate.Incubating;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Contract for instantiating embeddable values.
 *
 * @apiNote Incubating until the proposed
 * {@code instantiate(IntFunction valueAccess)}
 * form can be implemented.
 *
 * @see org.hibernate.annotations.EmbeddableInstantiator
 */
@Incubating
public interface EmbeddableInstantiator extends Instantiator {
	/**
	 * Create an instance of the embeddable
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object instantiate(ValueAccess valueAccess);
}
