/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.exec.spi;

import java.util.List;

import org.hibernate.sql.ast.tree.expression.JdbcParameter;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Conceptually similar to a List of JdbcParameters, but exposing a
 * read-only immutable contract.
 * Also as nice side effect, avoid any potential type pollution
 * problems during access.
 */
public interface JdbcParametersList {

	JdbcParametersList EMPTY = new JdbcParametersListMulti( new JdbcParameter[]{} );

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JdbcParameter get(int selectionIndex);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	int size();

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	static Builder newBuilder() {
		return newBuilder( 2 );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	static JdbcParametersList fromList(final List<JdbcParameter> originalList) {
		final Builder builder = newBuilder( originalList.size() );
		for ( JdbcParameter element : originalList ) {
			builder.add( element );
		}
		return builder.build();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	static JdbcParametersList empty() {
		return EMPTY;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	static JdbcParametersList singleton(final JdbcParameter p) {
		return new JdbcParametersListSingleton( p );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	static Builder newBuilder(final int i) {
		return new Builder( i );
	}

	class Builder {
		private JdbcParameter[] array;
		private int index = 0;

		private Builder(final int sizeEstimate) {
			this.array = new JdbcParameter[sizeEstimate];
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public void add(final JdbcParameter jdbcParameter) {
			if ( index >= array.length ) {
				int newSize = Math.max( index + 2, array.length >> 1 );
				JdbcParameter[] newArray = new JdbcParameter[newSize];
				System.arraycopy( array, 0, newArray, 0, array.length );
				this.array = newArray;
			}
			this.array[index++] = jdbcParameter;
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public JdbcParametersList build() {
			if ( index == 0 ) {
				return EMPTY;
			}
			else if ( index == 1 ) {
				return singleton( array[0] );
			}
			else if ( index == array.length ) {
				return new JdbcParametersListMulti( array );
			}
			else {
				JdbcParameter[] newArray = new JdbcParameter[index];
				System.arraycopy( array, 0, newArray, 0, index );
				return new JdbcParametersListMulti( newArray );
			}
		}
	}

	final class JdbcParametersListMulti implements JdbcParametersList {

		private final JdbcParameter[] array;

		private JdbcParametersListMulti(JdbcParameter[] inputArray) {
			this.array = inputArray;
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public JdbcParameter get(int selectionIndex) {
			return array[selectionIndex];
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public int size() {
			return array.length;
		}
	}

	final class JdbcParametersListSingleton implements JdbcParametersList {

		private final JdbcParameter singleElement;

		private JdbcParametersListSingleton(JdbcParameter singleElement) {
			this.singleElement = singleElement;
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public JdbcParameter get(int selectionIndex) {
			if ( selectionIndex != 0 ) {
				throw new ArrayIndexOutOfBoundsException( selectionIndex );
			}
			return singleElement;
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public int size() {
			return 1;
		}
	}

}
