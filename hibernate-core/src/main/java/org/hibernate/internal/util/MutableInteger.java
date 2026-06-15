/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.internal.util;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * A more performant version of {@link java.util.concurrent.atomic.AtomicInteger} in cases
 * where we do not have to worry about concurrency.  So usually as a variable referenced in
 * anonymous-inner or lambda or ...
 *
 * @author Andrea Boriero
 * @author Steve Ebersole
 */
public class MutableInteger {
	private int value;

	public MutableInteger() {
		this( 0 );
	}

	public MutableInteger(int value) {
		this.value = value;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public MutableInteger deepCopy() {
		return new MutableInteger( value );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getAndIncrement() {
		return value++;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getAndIncrementBy(int i) {
		final int local = value;
		value += i;
		return local;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int incrementAndGet() {
		return ++value;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int get() {
		return value;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void set(int value) {
		this.value = value;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void increase() {
		++value;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void increment() {
		++value;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void incrementBy(int i) {
		value += i;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void increase(int i) {
		value += i;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void plus(int i) {
		value += i;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void minus(int i) {
		value -= i;
	}
}
