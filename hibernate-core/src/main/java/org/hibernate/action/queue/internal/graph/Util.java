/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.action.queue.internal.graph;


import org.hibernate.metamodel.mapping.SelectableConsumer;
import org.hibernate.metamodel.mapping.SelectableMapping;
import org.hibernate.metamodel.mapping.SelectableMappings;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/// Utility code
///
/// @author Steve Ebersole
public class Util {
	/// Empty SelectableMappings for non-breakable DELETE edges
	public static final SelectableMappings EMPTY_SELECTABLES = new SelectableMappings() {
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public int getJdbcTypeCount() {
			return 0;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public SelectableMapping getSelectable(int columnIndex) {
			throw new IndexOutOfBoundsException( "No selectables in empty instance" );
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public int forEachSelectable(int offset, SelectableConsumer consumer) {
			return 0;
		}
	};
}
