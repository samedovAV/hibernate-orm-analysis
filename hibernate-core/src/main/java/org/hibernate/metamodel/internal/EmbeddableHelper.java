/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.internal;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


public class EmbeddableHelper {
	@Prove(complexity = Complexity.O_N2, n = "", count = {})
	public static int[] determineMappingIndex(String[] sortedNames, String[] names) {
		final int[] index = new int[sortedNames.length];
		int i = 0;
		for ( String name : names ) {
			int mappingIndex = -1;
			for ( int j = 0; j < sortedNames.length; j++ ) {
				if ( name.equals( sortedNames[j] ) ) {
					mappingIndex = j;
					break;
				}
			}
			if ( mappingIndex != -1 ) {
				index[i++] = mappingIndex;
			}
		}
		return index;
	}

	@Prove(complexity = Complexity.O_N2, n = "", count = {})
	public static boolean resolveIndex(String[] sortedComponentNames, String[] componentNames, int[] index) {
		boolean hasGaps = false;
		for ( int i = 0; i < componentNames.length; i++ ) {
			int newIndex = -1;
			for ( int j = 0; j < sortedComponentNames.length; j++ ) {
				if ( componentNames[i].equals( sortedComponentNames[j] ) ) {
					newIndex = j;
					break;
				}
			}
			index[i] = newIndex;
			hasGaps = hasGaps || newIndex < 0;
		}
		return hasGaps;
	}
}
