/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.service;

import org.hibernate.service.spi.ServiceException;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Andrea Boriero
 */
public class NullServiceException extends ServiceException {
	public final Class<?> serviceRole;

	public NullServiceException(Class<?> serviceRole) {
		super( "Unknown service requested [" + serviceRole.getName() + "]" );
		this.serviceRole = serviceRole;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<?> getServiceRole() {
		return serviceRole;
	}
}
