/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.internal;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SessionEventListener;
import org.hibernate.engine.spi.SessionEventListenerManager;

import static java.lang.System.arraycopy;
import static java.util.Arrays.copyOf;
import static java.util.Objects.requireNonNull;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class SessionEventListenerManagerImpl implements SessionEventListenerManager, Serializable {

	private SessionEventListener[] listeners;

	public SessionEventListenerManagerImpl(SessionEventListener... initialListener) {
		//no need for defensive copies until the array is mutated:
		listeners = initialListener;
	}

	public SessionEventListenerManagerImpl(List<SessionEventListener> initialListener) {
		//no need for defensive copies until the array is mutated:
		listeners = initialListener.toArray( new SessionEventListener[0] );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void addListener(final SessionEventListener... additionalListeners) {
		requireNonNull( additionalListeners );
		final var existing = listeners;
		if ( existing == null ) {
			//Make a defensive copy as this array can be tracked back to API (user code)
			listeners = copyOf( additionalListeners, additionalListeners.length );
		}
		else {
			// Resize our existing array and add the new listeners
			final var newList = new SessionEventListener[ existing.length + additionalListeners.length ];
			arraycopy( existing, 0, newList, 0, existing.length );
			arraycopy( additionalListeners, 0, newList, existing.length, additionalListeners.length );
			listeners = newList;
		}
	}

	@Override
	@Prove(complexity = Complexity.O_N2, n = "", count = {})
	public void transactionCompletion(boolean successful) {
		if ( listeners != null ) {
			for ( var listener : listeners ) {
				listener.transactionCompletion( successful );
			}
		}
	}

	@Override
	@Prove(complexity = Complexity.O_N2, n = "", count = {})
	public void jdbcConnectionAcquisitionStart() {
		if ( listeners != null ) {
			for ( var listener : listeners ) {
				listener.jdbcConnectionAcquisitionStart();
			}
		}
	}

	@Override
	@Prove(complexity = Complexity.O_N2, n = "", count = {})
	public void jdbcConnectionAcquisitionEnd() {
		if ( listeners != null ) {
			for ( var listener : listeners ) {
				listener.jdbcConnectionAcquisitionEnd();
			}
		}
	}

	@Override
	@Prove(complexity = Complexity.O_N2, n = "", count = {})
	public void jdbcConnectionReleaseStart() {
		if ( listeners != null ) {
			for ( var listener : listeners ) {
				listener.jdbcConnectionReleaseStart();
			}
		}
	}

	@Override
	@Prove(complexity = Complexity.O_N2, n = "", count = {})
	public void jdbcConnectionReleaseEnd() {
		if ( listeners != null ) {
			for ( var listener : listeners ) {
				listener.jdbcConnectionReleaseEnd();
			}
		}
	}

	@Override
	@Prove(complexity = Complexity.O_N2, n = "", count = {})
	public void jdbcPrepareStatementStart() {
		if ( listeners != null ) {
			for ( var listener : listeners ) {
				listener.jdbcPrepareStatementStart();
			}
		}
	}

	@Override
	@Prove(complexity = Complexity.O_N2, n = "", count = {})
	public void jdbcPrepareStatementEnd() {
		if ( listeners != null ) {
			for ( var listener : listeners ) {
				listener.jdbcPrepareStatementEnd();
			}
		}
	}

	@Override
	@Prove(complexity = Complexity.O_N2, n = "", count = {})
	public void jdbcExecuteStatementStart() {
		if ( listeners != null ) {
			for ( var listener : listeners ) {
				listener.jdbcExecuteStatementStart();
			}
		}
	}

	@Override
	@Prove(complexity = Complexity.O_N2, n = "", count = {})
	public void jdbcExecuteStatementEnd() {
		if ( listeners != null ) {
			for ( var listener : listeners ) {
				listener.jdbcExecuteStatementEnd();
			}
		}
	}

	@Override
	@Prove(complexity = Complexity.O_N2, n = "", count = {})
	public void jdbcExecuteBatchStart() {
		if ( listeners != null ) {
			for ( var listener : listeners ) {
				listener.jdbcExecuteBatchStart();
			}
		}
	}

	@Override
	@Prove(complexity = Complexity.O_N2, n = "", count = {})
	public void jdbcExecuteBatchEnd() {
		if ( listeners != null ) {
			for ( var listener : listeners ) {
				listener.jdbcExecuteBatchEnd();
			}
		}
	}

	@Override
	@Prove(complexity = Complexity.O_N2, n = "", count = {})
	public void cachePutStart() {
		if ( listeners != null ) {
			for ( var listener : listeners ) {
				listener.cachePutStart();
			}
		}
	}

	@Override
	@Prove(complexity = Complexity.O_N2, n = "", count = {})
	public void cachePutEnd() {
		if ( listeners != null ) {
			for ( var listener : listeners ) {
				listener.cachePutEnd();
			}
		}
	}

	@Override
	@Prove(complexity = Complexity.O_N2, n = "", count = {})
	public void cacheGetStart() {
		if ( listeners != null ) {
			for ( var listener : listeners ) {
				listener.cacheGetStart();
			}
		}
	}

	@Override
	@Prove(complexity = Complexity.O_N2, n = "", count = {})
	public void cacheGetEnd(boolean hit) {
		if ( listeners != null ) {
			for ( var listener : listeners ) {
				listener.cacheGetEnd( hit );
			}
		}
	}

	@Override
	@Prove(complexity = Complexity.O_N2, n = "", count = {})
	public void flushStart() {
		if ( listeners != null ) {
			for ( var listener : listeners ) {
				listener.flushStart();
			}
		}
	}

	@Override
	@Prove(complexity = Complexity.O_N2, n = "", count = {})
	public void flushEnd(int numberOfEntities, int numberOfCollections) {
		if ( listeners != null ) {
			for ( var listener : listeners ) {
				listener.flushEnd( numberOfEntities, numberOfCollections );
			}
		}
	}

	@Override
	@Prove(complexity = Complexity.O_N2, n = "", count = {})
	public void prePartialFlushStart() {
		if ( listeners != null ) {
			for ( var listener : listeners ) {
				listener.prePartialFlushStart();
			}
		}
	}

	@Override
	@Prove(complexity = Complexity.O_N2, n = "", count = {})
	public void prePartialFlushEnd() {
		if ( listeners != null ) {
			for ( var listener : listeners ) {
				listener.prePartialFlushEnd();
			}
		}
	}

	@Override
	@Prove(complexity = Complexity.O_N2, n = "", count = {})
	public void partialFlushStart() {
		if ( listeners != null ) {
			for ( var listener : listeners ) {
				listener.partialFlushStart();
			}
		}
	}

	@Override
	@Prove(complexity = Complexity.O_N2, n = "", count = {})
	public void partialFlushEnd(int numberOfEntities, int numberOfCollections) {
		if ( listeners != null ) {
			for ( var listener : listeners ) {
				listener.partialFlushEnd( numberOfEntities, numberOfCollections );
			}
		}
	}

	@Override
	@Prove(complexity = Complexity.O_N2, n = "", count = {})
	public void dirtyCalculationStart() {
		if ( listeners == null ) {
			return;
		}

		for ( var listener : listeners ) {
			listener.dirtyCalculationStart();
		}
	}

	@Override
	@Prove(complexity = Complexity.O_N2, n = "", count = {})
	public void dirtyCalculationEnd(boolean dirty) {
		if ( listeners != null ) {
			for ( var listener : listeners ) {
				listener.dirtyCalculationEnd( dirty );
			}
		}
	}

	@Override
	@Prove(complexity = Complexity.O_N2, n = "", count = {})
	public void end() {
		if ( listeners != null ) {
			for ( var listener : listeners ) {
				listener.end();
			}
		}
	}
}
