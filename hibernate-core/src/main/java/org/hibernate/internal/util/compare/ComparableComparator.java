/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.internal.util.compare;

import java.io.Serializable;
import java.util.Comparator;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Delegates to Comparable
 *
 * @author Gavin King
 * @author Steve Ebersole
 */
public class ComparableComparator<T extends Comparable<T>> implements Comparator<T>, Serializable {

	@SuppressWarnings("rawtypes")
	public static final Comparator INSTANCE = new ComparableComparator();

	@SuppressWarnings("unchecked")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static <T extends Comparable<T>> Comparator<T> instance() {
		return INSTANCE;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int compare(T one, T another) {
		return one.compareTo( another );
	}
}
