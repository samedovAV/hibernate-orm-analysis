/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.internal.util;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;



public final class ExceptionHelper {

	private ExceptionHelper() {
	}

	/**
	 * Throws the given {@link Throwable}, even if it's a checked exception.
	 *
	 * @param throwable The {@code Throwable} to throw.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static void rethrow(Throwable throwable) {
		sneakyThrow( throwable );
	}

	@SuppressWarnings("unchecked")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private static <T extends Throwable> void sneakyThrow(Throwable e)
			throws T {
		throw (T) e;
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public static Throwable getRootCause(Throwable error) {
		var next = error;
		while ( true ) {
			final var current = next;
			next = current.getCause();
			if ( next == null ) {
				return current;
			}
		}
	}
}
