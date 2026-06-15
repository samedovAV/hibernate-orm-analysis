/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.event.jpa.internal;

import jakarta.persistence.EntityListenerRegistration;
import org.hibernate.event.jpa.spi.EntityCallbacks;
import org.hibernate.event.jpa.spi.Callback;
import org.hibernate.jpa.event.spi.CallbackType;

import java.util.function.Consumer;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class NoCallbacks implements EntityCallbacks<Object> {
	public static final NoCallbacks NO_CALLBACKS = new NoCallbacks();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean hasRegisteredCallbacks(CallbackType callbackType) {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean preCreate(Object entity) {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean postCreate(Object entity) {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean preMerge(Object entity) {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean preInsert(Object entity) {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean postInsert(Object entity) {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean preUpdate(Object entity) {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean postUpdate(Object entity) {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean preUpsert(Object entity) {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean postUpsert(Object entity) {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean preRemove(Object entity) {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean postRemove(Object entity) {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean preDelete(Object entity) {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean postDelete(Object entity) {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean postLoad(Object entity) {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public EntityListenerRegistration addListener(CallbackType type, Consumer<? super Object> listener) {
		return RegistrationImpl.NO_REG;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public EntityListenerRegistration addListener(CallbackType type, Callback<? super Object> callback) {
		return RegistrationImpl.NO_REG;
	}

	public static class RegistrationImpl implements EntityListenerRegistration {
		public static RegistrationImpl NO_REG = new RegistrationImpl();

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public void cancel() {
		}
	}
}
