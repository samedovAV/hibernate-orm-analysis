/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * @author Ståle W. Pedersen
 */
public interface CompositeTracker extends PrimeAmongSecondarySupertypes {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void $$_hibernate_setOwner(String name, CompositeOwner tracker);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void $$_hibernate_clearOwner(String name);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default CompositeTracker asCompositeTracker() {
		return this;
	}

}
