/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.internal.util;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * A consumer, like {@link java.util.function.Consumer}, accepting a value and its index.
 *
 * @see IndexedBiConsumer
 *
 * @author Christian Beikov
 * @author Steve Ebersole
 */
@FunctionalInterface
public interface IndexedConsumer<T> {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void accept(int index, T t);
}
