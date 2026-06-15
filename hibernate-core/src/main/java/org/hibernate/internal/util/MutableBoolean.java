/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.internal.util;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Support for mutable boolean references, generally used from within
 * anon inner classes, lambdas, etc
 *
 * @author Steve Ebersole
 */
public class MutableBoolean {
	private boolean value;

	public MutableBoolean() {
	}

	public MutableBoolean(boolean value) {
		this.value = value;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean getValue() {
		return value;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setValue(boolean value) {
		this.value = value;
	}
}
