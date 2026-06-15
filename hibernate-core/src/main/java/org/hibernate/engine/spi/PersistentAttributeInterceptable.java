/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * @author Steve Ebersole
 */
public interface PersistentAttributeInterceptable extends PrimeAmongSecondarySupertypes {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	PersistentAttributeInterceptor $$_hibernate_getInterceptor();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void $$_hibernate_setInterceptor(PersistentAttributeInterceptor interceptor);

	/**
	 * Special internal contract to optimize type checking
	 * @see PrimeAmongSecondarySupertypes
	 * @return this same instance
	 */
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default PersistentAttributeInterceptable asPersistentAttributeInterceptable() {
		return this;
	}

}
