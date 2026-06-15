/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.bytecode.enhance.internal.tracker;

import java.util.Arrays;

import org.hibernate.internal.util.collections.ArrayHelper;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * small low memory class to keep track of changed fields
 * <p>
 * uses an array as a set (under the assumption that the number of elements will be low) to avoid having to instantiate an HashSet.
 * if the assumption does not, hold the array can be kept ordered to reduce the cost of verifying duplicates
 *
 * @author Luis Barreiro
 */
public final class SimpleFieldTracker implements DirtyTracker {

	private String[] names;
	private boolean suspended;

	public SimpleFieldTracker() {
		names = ArrayHelper.EMPTY_STRING_ARRAY;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void add(String name) {
		if ( suspended ) {
			return;
		}
		if ( !contains( name ) ) {
			names = Arrays.copyOf( names, names.length + 1 );
			names[names.length - 1] = name;
		}
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean contains(String name) {
		for ( String existing : names ) {
			if ( existing.equals( name ) ) {
				return true;
			}
		}
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void clear() {
		if ( !isEmpty() ) {
			names = ArrayHelper.EMPTY_STRING_ARRAY;
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isEmpty() {
		return names.length == 0;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String[] get() {
		return names;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void suspend(boolean suspend) {
		this.suspended = suspend;
	}

}
