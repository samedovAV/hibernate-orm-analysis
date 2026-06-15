/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.bytecode.enhance.internal.tracker;

import org.hibernate.bytecode.enhance.spi.CollectionTracker;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * small low memory class to keep track of the number of elements in a collection
 *
 * @author Ståle W. Pedersen
 */
public final class NoopCollectionTracker implements CollectionTracker {

	public static final CollectionTracker INSTANCE = new NoopCollectionTracker();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void add(String name, int size) {
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getSize(String name) {
		return -1;
	}

}
