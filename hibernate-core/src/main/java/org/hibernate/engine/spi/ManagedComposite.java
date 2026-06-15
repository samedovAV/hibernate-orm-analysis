/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Specialized {@link Managed} contract for
 * {@linkplain jakarta.persistence.Embeddable embeddable classes}.
 *
 * @author Steve Ebersole
 */
public interface ManagedComposite extends Managed {

	/**
	 * Special internal contract to optimize type checking
	 * @see PrimeAmongSecondarySupertypes
	 * @return this same instance
	 */
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default ManagedComposite asManagedComposite() {
		return this;
	}

}
