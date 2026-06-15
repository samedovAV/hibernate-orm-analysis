/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.internal.util;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * @author Christian Beikov
 */
public final class SubSequence implements CharSequence {
	private final CharSequence sequence;
	private final int start;
	private final int length;

	public SubSequence(CharSequence sequence, int start, int end) {
		this.sequence = sequence;
		this.start = start;
		this.length = end - start;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int length() {
		return length;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public char charAt(int index) {
		if ( index < 0 || index >= length ) {
			throw new StringIndexOutOfBoundsException( index );
		}
		return sequence.charAt( index + start );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public CharSequence subSequence(int start, int end) {
		if ( start < 0 || start > length ) {
			throw new StringIndexOutOfBoundsException( start );
		}
		if ( end < start || end > length ) {
			throw new StringIndexOutOfBoundsException( end );
		}
		return sequence.subSequence( this.start + start, this.start + end );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public int lastIndexOf(char c, int fromIndex, int endIndex) {
		int idx = CharSequenceHelper.lastIndexOf( sequence, c, start + fromIndex, this.start + endIndex );
		if ( idx == -1 ) {
			return -1;
		}
		return idx - this.start;
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public int indexOf(char c, int fromIndex, int endIndex) {
		int idx = CharSequenceHelper.indexOf( sequence, c, this.start + fromIndex, this.start + endIndex );
		if ( idx == -1 ) {
			return -1;
		}
		return idx - this.start;
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public int indexOf(String s, int fromIndex, int endIndex) {
		int idx = CharSequenceHelper.indexOf( sequence, s, this.start + fromIndex, this.start + endIndex );
		if ( idx == -1 ) {
			return -1;
		}
		return idx - this.start;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String toString() {
		return sequence.subSequence( start, start + length ).toString();
	}
}
