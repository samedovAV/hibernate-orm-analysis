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
public interface CompositeOwner extends PrimeAmongSecondarySupertypes {
	/**
	 * @param attributeName to be added to the dirty list
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void $$_hibernate_trackChange(String attributeName);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default CompositeOwner asCompositeOwner() {
		return this;
	}

}
