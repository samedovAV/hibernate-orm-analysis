/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.internal.util.collections;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Gavin King
 */
public class JoinedList<E> extends AbstractList<E> {
	private final List<List<E>> lists;
	private final int size;

	public JoinedList(List<List<E>> lists) {
		this.lists = lists;
		size = lists.stream().map(List::size).reduce(0, Integer::sum);
	}

	@SafeVarargs
	public JoinedList(List<E>... lists) {
		this( Arrays.asList(lists) );
	}

	@Override
	@Prove(complexity = Complexity.O_N2, n = "", count = {})
	public E get(int index) {
		for (List<E> list: lists) {
			if ( list.size() > index ) {
				return list.get(index);
			}
			index -= list.size();
		}
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int size() {
		return size;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Iterator<E> iterator() {
		return lists.stream().flatMap(List::stream).iterator();
	}
}
