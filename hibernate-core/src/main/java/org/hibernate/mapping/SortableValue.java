/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.mapping;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


public interface SortableValue {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isSorted();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	int[] sortProperties();
}
