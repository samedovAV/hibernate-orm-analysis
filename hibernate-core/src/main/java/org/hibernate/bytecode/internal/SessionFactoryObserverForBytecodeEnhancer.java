/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.bytecode.internal;

import org.hibernate.SessionFactory;
import org.hibernate.SessionFactoryObserver;
import org.hibernate.bytecode.spi.BytecodeProvider;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

public final class SessionFactoryObserverForBytecodeEnhancer implements SessionFactoryObserver {

	private final BytecodeProvider bytecodeProvider;

	public SessionFactoryObserverForBytecodeEnhancer(BytecodeProvider bytecodeProvider) {
		this.bytecodeProvider = bytecodeProvider;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void sessionFactoryCreated(final SessionFactory factory) {
		this.bytecodeProvider.resetCaches();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void sessionFactoryClosing(final SessionFactory factory) {
		//unnecessary
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void sessionFactoryClosed(final SessionFactory factory) {
		this.bytecodeProvider.resetCaches();
	}
}
