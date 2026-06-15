/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.event.jpa.internal;

import jakarta.persistence.EntityListenerRegistration;
import org.hibernate.event.jpa.spi.Callback;
import org.hibernate.jpa.event.spi.CallbackType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class EntityListenerRegistrationImpl<E> implements EntityListenerRegistration {
	private final EntityCallbacksImpl<E> entityCallbacks;
	private final CallbackType registeredCallbackType;
	private final Callback<? super E> registeredCallback;

	public EntityListenerRegistrationImpl(
			EntityCallbacksImpl<E> entityCallbacks,
			CallbackType registeredCallbackType,
			Callback<? super E> registeredCallback) {
		this.entityCallbacks = entityCallbacks;
		this.registeredCallbackType = registeredCallbackType;
		this.registeredCallback = registeredCallback;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void cancel() {
		entityCallbacks.remove( registeredCallbackType, registeredCallback );
	}
}
