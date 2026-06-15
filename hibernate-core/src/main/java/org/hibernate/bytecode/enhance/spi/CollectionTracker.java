/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.bytecode.enhance.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Interface to be implemented by collection trackers that hold the expected size od collections, a simplified {@code Map<String, int>}.
 *
 * @author Luis Barreiro
 */
public interface CollectionTracker {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void add(String name, int size);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	int getSize(String name);
}
