/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.cache.spi.support;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Generates increasing identifiers (in a single VM only). Not valid across multiple VMs.  Identifiers are not
 * necessarily strictly increasing, but usually are.
 * <p>
 * Core while loop implemented by Alex Snaps - EHCache project - under ASL 2.0
 *
 * @author Hibernate team
 * @author Alex Snaps
 */
public final class SimpleTimestamper {
	private static final int BIN_DIGITS = 12;
	private static final AtomicLong VALUE = new AtomicLong();

	public static final short ONE_MS = 1 << BIN_DIGITS;

	@Prove(complexity = Complexity.O_N2, n = "", count = {})
	public static long next() {
		while ( true ) {
			long base = System.currentTimeMillis() << BIN_DIGITS;
			long maxValue = base + ONE_MS - 1;
			for ( long current = VALUE.get(), update = Math.max( base, current + 1 ); update < maxValue;
					current = VALUE.get(), update = Math.max( base, current + 1 ) ) {
				if ( VALUE.compareAndSet( current, update ) ) {
					return update;
				}
			}
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static int timeOut() {
		return (int) TimeUnit.SECONDS.toMillis( 60 ) * ONE_MS;
	}

	private SimpleTimestamper() {
	}
}
