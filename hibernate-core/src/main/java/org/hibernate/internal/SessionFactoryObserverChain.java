/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.internal;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.SessionFactoryObserver;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
class SessionFactoryObserverChain implements SessionFactoryObserver {
	private List<SessionFactoryObserver> observers;

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void addObserver(SessionFactoryObserver observer) {
		if ( observers == null ) {
			observers = new ArrayList<>();
		}
		observers.add( observer );
	}

	@Override
	@Prove(complexity = Complexity.O_N2, n = "", count = {})
	public void sessionFactoryCreated(SessionFactory factory) {
		if ( observers == null ) {
			return;
		}

		for ( SessionFactoryObserver observer : observers ) {
			observer.sessionFactoryCreated( factory );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_N2, n = "", count = {})
	public void sessionFactoryClosing(SessionFactory factory) {
		if ( observers == null ) {
			return;
		}

		//notify in reverse order of create notification
		int size = observers.size();
		for (int index = size - 1 ; index >= 0 ; index--) {
			observers.get( index ).sessionFactoryClosing( factory );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_N2, n = "", count = {})
	public void sessionFactoryClosed(SessionFactory factory) {
		if ( observers == null ) {
			return;
		}

		//notify in reverse order of create notification
		int size = observers.size();
		for (int index = size - 1 ; index >= 0 ; index--) {
			observers.get( index ).sessionFactoryClosed( factory );
		}
	}
}
