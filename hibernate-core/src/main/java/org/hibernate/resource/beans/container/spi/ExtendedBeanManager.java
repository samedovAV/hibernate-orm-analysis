/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.resource.beans.container.spi;

import jakarta.enterprise.inject.spi.BeanManager;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * This contract and the nested LifecycleListener contract represent the changes
 * we'd like to propose to the CDI spec.  The idea being simply to allow contextual
 * registration of {@link BeanManager} lifecycle callbacks
 *
 * @author Steve Ebersole
 */
public interface ExtendedBeanManager {
	/**
	 * Register a BeanManager LifecycleListener
	 *
	 * @param lifecycleListener The listener to register
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void registerLifecycleListener(LifecycleListener lifecycleListener);

	/**
	 * Contract for things interested in receiving notifications of
	 * BeanManager lifecycle events.
	 * <p>
	 * A "beanManagerDestroyed" notifications would probably also be generally
	 * useful, although we do not need it here and not sure WildFly can really
	 * tell us that reliably.
	 */
	interface LifecycleListener {
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		void beanManagerInitialized(BeanManager beanManager);
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		void beforeBeanManagerDestroyed(BeanManager beanManager);
	}
}
