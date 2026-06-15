/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.bytecode.enhance.internal.tracker;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Interface to be implemented by dirty trackers, a simplified Set of String.
 *
 * @author Luis Barreiro
 */
public interface DirtyTracker {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void add(String name);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean contains(String name);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void clear();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isEmpty();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String[] get();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void suspend(boolean suspend);
}
