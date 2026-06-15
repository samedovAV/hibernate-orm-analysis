/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.jpa.event.internal;

import org.hibernate.event.jpa.spi.Callback;
import org.hibernate.jpa.event.spi.CallbackType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/// Base support for [Callback] implementations.
///
/// @author Steve Ebersole
abstract class AbstractCallback implements Callback<Object> {
	private final CallbackType callbackType;

	AbstractCallback(CallbackType callbackType) {
		this.callbackType = callbackType;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public CallbackType getCallbackType() {
		return callbackType;
	}
}
