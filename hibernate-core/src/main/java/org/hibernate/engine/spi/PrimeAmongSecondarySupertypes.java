/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.spi;

import org.hibernate.Internal;
import org.hibernate.engine.internal.ManagedTypeHelper;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.ProxyConfiguration;

import jakarta.annotation.Nullable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * For a full explanation of the purpose of this interface see {@link ManagedTypeHelper}.
 *
 * @apiNote This is an internal, private marking interface; it's exposed in the SPI
 *          package as bytecode enhanced user code needs to be able to reference it.
 *
 * @author Sanne Grinovero
 */
@Internal
public interface PrimeAmongSecondarySupertypes {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default @Nullable ManagedEntity asManagedEntity() {
		return null;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default @Nullable PersistentAttributeInterceptable asPersistentAttributeInterceptable() {
		return null;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default @Nullable SelfDirtinessTracker asSelfDirtinessTracker() {
		return null;
	}

	//Included for consistency but doesn't seem to be used?
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default @Nullable Managed asManaged() {
		return null;
	}

	//Included for consistency but doesn't seem to be used?
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default @Nullable ManagedComposite asManagedComposite() {
		return null;
	}

	//Included for consistency but doesn't seem to be used?
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default @Nullable ManagedMappedSuperclass asManagedMappedSuperclass() {
		return null;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default @Nullable CompositeOwner asCompositeOwner() {
		return null;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default @Nullable CompositeTracker asCompositeTracker() {
		return null;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default @Nullable HibernateProxy asHibernateProxy() {
		return null;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default @Nullable ProxyConfiguration asProxyConfiguration() {
		return null;
	}

}
