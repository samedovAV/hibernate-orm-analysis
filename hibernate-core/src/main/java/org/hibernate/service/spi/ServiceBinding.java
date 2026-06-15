/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.service.spi;

import org.hibernate.service.Service;


import jakarta.annotation.Nullable;

import static org.hibernate.service.internal.ServiceLogger.SERVICE_LOGGER;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Models a binding for a particular service.
 *
 * @author Steve Ebersole
 */
public final class ServiceBinding<R extends Service> {

	public interface ServiceLifecycleOwner {
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		<R extends Service> R initiateService(ServiceInitiator<R> serviceInitiator);

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		<R extends Service> void configureService(ServiceBinding<R> binding);
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		<R extends Service> void injectDependencies(ServiceBinding<R> binding);
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		<R extends Service> void startService(ServiceBinding<R> binding);

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		<R extends Service> void stopService(ServiceBinding<R> binding);
	}

	private final ServiceLifecycleOwner lifecycleOwner;
	private final Class<R> serviceRole;
	private final @Nullable ServiceInitiator<R> serviceInitiator;
	@SuppressWarnings( "NullAway.Init" )
	private volatile R service;

	public ServiceBinding(ServiceLifecycleOwner lifecycleOwner, Class<R> serviceRole, R service) {
		this.lifecycleOwner = lifecycleOwner;
		this.serviceRole = serviceRole;
		this.serviceInitiator = null;
		this.service = service;
	}

	public ServiceBinding(ServiceLifecycleOwner lifecycleOwner, ServiceInitiator<R> serviceInitiator) {
		this.lifecycleOwner = lifecycleOwner;
		this.serviceRole = serviceInitiator.getServiceInitiated();
		this.serviceInitiator = serviceInitiator;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ServiceLifecycleOwner getLifecycleOwner() {
		return lifecycleOwner;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<R> getServiceRole() {
		return serviceRole;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public @Nullable ServiceInitiator<R> getServiceInitiator() {
		return serviceInitiator;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public R getService() {
		return service;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setService(R service) {
		if ( this.service != null && SERVICE_LOGGER.isDebugEnabled() ) {
			SERVICE_LOGGER.overridingExistingBinding( serviceRole.getName() );
		}
		this.service = service;
	}
}
