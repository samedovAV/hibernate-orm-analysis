/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.event.jpa.internal;

import org.hibernate.event.jpa.spi.Callback;
import org.hibernate.jpa.event.spi.CallbackType;

import java.util.function.Consumer;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class AddedCallbackImpl<E> implements Callback<E> {
	private final CallbackType callbackType;
	private final Consumer<? super E> listener;

	public AddedCallbackImpl(CallbackType callbackType, Consumer<? super E> listener) {
		this.callbackType = callbackType;
		this.listener = listener;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public CallbackType getCallbackType() {
		return callbackType;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <S extends E> void performCallback(S entity) {
		listener.accept( entity );
	}
}
