/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.spi;

import java.util.Iterator;

import org.hibernate.Incubating;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Unification of {@link Iterator} and {@link AutoCloseable}.
 *
 * @author Steve Ebersole
 *
 * @since 5.2
 */
@Incubating
public interface CloseableIterator<T> extends Iterator<T>, AutoCloseable {
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void close();
}
