/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Enumeration of standard binary arithmetic operators
 *
 * @author Steve Ebersole
 */
public enum BinaryArithmeticOperator {
	ADD {
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public String toLoggableText(String lhs, String rhs) {
			return standardToLoggableText( lhs, this, rhs );
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public char getOperatorSqlText() {
			return '+';
		}
	},

	SUBTRACT {
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public String toLoggableText(String lhs, String rhs) {
			return standardToLoggableText( lhs, this, rhs );
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public char getOperatorSqlText() {
			return '-';
		}
	},

	MULTIPLY {
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public String toLoggableText(String lhs, String rhs) {
			return standardToLoggableText( lhs, this, rhs );
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public char getOperatorSqlText() {
			return '*';
		}
	},

	DIVIDE {
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public String toLoggableText(String lhs, String rhs) {
			return standardToLoggableText( lhs, this, rhs );
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public char getOperatorSqlText() {
			return '/';
		}
	},

	QUOT {
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public String toLoggableText(String lhs, String rhs) {
			return standardToLoggableText( lhs, this, rhs );
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public char getOperatorSqlText() {
			return '/';
		}
	},

	MODULO {
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public String toLoggableText(String lhs, String rhs) {
//				return lhs + " % " + rhs;
			return "mod(" + lhs + "," + rhs + ")";
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public char getOperatorSqlText() {
			return '%';
		}
	},

	/**
	 * "Portable" division, that is, true integer division when the
	 * operands are integers.
	 *
	 * @see org.hibernate.cfg.AvailableSettings#PORTABLE_INTEGER_DIVISION
	 * @see org.hibernate.query.spi.QueryEngineOptions#isPortableIntegerDivisionEnabled()
	 */
	DIVIDE_PORTABLE {
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public String toLoggableText(String lhs, String rhs) {
			return standardToLoggableText( lhs, this, rhs );
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public char getOperatorSqlText() {
			return '/';
		}
	},

	;

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public abstract String toLoggableText(String lhs, String rhs);
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public abstract char getOperatorSqlText();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getOperatorSqlTextString() {
		return Character.toString( getOperatorSqlText() );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	private static String standardToLoggableText(String lhs, BinaryArithmeticOperator operator, String rhs) {
		return standardToLoggableText( lhs, operator.getOperatorSqlText(), rhs );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private static String standardToLoggableText(String lhs, char operator, String rhs) {
		return '(' + lhs + operator + rhs + ')';
	}

}
