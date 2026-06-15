/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Enumerates the binary comparison operators.
 *
 * @apiNote This is an SPI type allowing collaboration
 * between {@code org.hibernate.dialect} and
 * {@code org.hibernate.sqm}. It should never occur in
 * APIs visible to the application program.
 *
 * @author Steve Ebersole
 */
public enum ComparisonOperator {
	EQUAL {
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public ComparisonOperator negated() {
			return NOT_EQUAL;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public ComparisonOperator invert() {
			return EQUAL;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public ComparisonOperator broader() {
			return EQUAL;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public ComparisonOperator sharper() {
			return EQUAL;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public String sqlText() {
			return "=";
		}
	},

	NOT_EQUAL {
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public ComparisonOperator negated() {
			return EQUAL;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public ComparisonOperator invert() {
			return NOT_EQUAL;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public ComparisonOperator broader() {
			return NOT_EQUAL;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public ComparisonOperator sharper() {
			return NOT_EQUAL;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public String sqlText() {
			return "<>";
		}
	},
	NOT_DISTINCT_FROM {
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public ComparisonOperator negated() {
			return DISTINCT_FROM;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public ComparisonOperator invert() {
			return NOT_DISTINCT_FROM;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public ComparisonOperator broader() {
			return NOT_DISTINCT_FROM;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public ComparisonOperator sharper() {
			return NOT_DISTINCT_FROM;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public String sqlText() {
			return " is not distinct from ";
		}
	},

	DISTINCT_FROM {
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public ComparisonOperator negated() {
			return NOT_DISTINCT_FROM;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public ComparisonOperator invert() {
			return DISTINCT_FROM;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public ComparisonOperator broader() {
			return DISTINCT_FROM;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public ComparisonOperator sharper() {
			return DISTINCT_FROM;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public String sqlText() {
			return " is distinct from ";
		}
	},

	LESS_THAN {
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public ComparisonOperator negated() {
			return GREATER_THAN_OR_EQUAL;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public ComparisonOperator invert() {
			return GREATER_THAN;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public ComparisonOperator broader() {
			return LESS_THAN_OR_EQUAL;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public ComparisonOperator sharper() {
			return LESS_THAN;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public String sqlText() {
			return "<";
		}
	},

	LESS_THAN_OR_EQUAL {
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public ComparisonOperator negated() {
			return GREATER_THAN;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public ComparisonOperator invert() {
			return GREATER_THAN_OR_EQUAL;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public ComparisonOperator broader() {
			return LESS_THAN_OR_EQUAL;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public ComparisonOperator sharper() {
			return LESS_THAN;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public String sqlText() {
			return "<=";
		}
	},

	GREATER_THAN {
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public ComparisonOperator negated() {
			return LESS_THAN_OR_EQUAL;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public ComparisonOperator invert() {
			return LESS_THAN;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public ComparisonOperator broader() {
			return GREATER_THAN_OR_EQUAL;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public ComparisonOperator sharper() {
			return GREATER_THAN;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public String sqlText() {
			return ">";
		}
	},

	GREATER_THAN_OR_EQUAL {
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public ComparisonOperator negated() {
			return LESS_THAN;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public ComparisonOperator invert() {
			return LESS_THAN_OR_EQUAL;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public ComparisonOperator broader() {
			return GREATER_THAN_OR_EQUAL;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public ComparisonOperator sharper() {
			return GREATER_THAN;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public String sqlText() {
			return ">=";
		}
	};

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public abstract ComparisonOperator negated();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public abstract ComparisonOperator invert();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public abstract ComparisonOperator broader();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public abstract ComparisonOperator sharper();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public abstract String sqlText();
}
