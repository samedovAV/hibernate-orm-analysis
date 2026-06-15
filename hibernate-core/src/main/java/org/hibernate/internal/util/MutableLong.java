/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.internal.util;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * A more performant version of {@link java.util.concurrent.atomic.AtomicLong} in cases
 * where we do not have to worry about concurrency.  So usually as a variable referenced in
 * anonymous-inner or lambda or ...
 *
 * @author Andrea Boriero
 */
public class MutableLong {
	private long value;

	public MutableLong() {
	}

	public MutableLong(long value) {
		this.value = value;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public MutableLong deepCopy() {
		return new MutableLong( value );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public long getAndIncrement() {
		return value++;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public long incrementAndGet() {
		return ++value;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public long get() {
		return value;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void set(long value) {
		this.value = value;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void increase() {
		++value;
	}
}
