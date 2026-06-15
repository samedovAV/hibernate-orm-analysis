/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Used to put natural id values into collections.  Useful mainly to
 * apply equals/hashCode implementations.
 */
public interface Resolution {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object getNaturalIdValue();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isSame(Object otherValue);
}
