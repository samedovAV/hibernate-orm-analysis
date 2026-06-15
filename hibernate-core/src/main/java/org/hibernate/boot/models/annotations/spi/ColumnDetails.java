/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.annotations.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * @author Steve Ebersole
 */
public interface ColumnDetails extends Commentable, Optionable {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String name();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void name(String value);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String columnDefinition();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void columnDefinition(String value);

	interface AlternateTableCapable {
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		String table();

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		void table(String value);
	}

	interface Nullable extends ColumnDetails {
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		boolean nullable();

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		void nullable(boolean value);
	}

	interface Mutable extends ColumnDetails {

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		boolean insertable();

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		void insertable(boolean value);

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		boolean updatable();

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		void updatable(boolean value);
	}

	interface Sizable extends ColumnDetails {
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		int length();

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		void length(int value);

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		int precision();

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		void precision(int value);

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		int scale();

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		void scale(int value);
	}

	interface SecondSizable extends Sizable {
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		int secondPrecision();

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		void secondPrecision(int value);
	}

	interface Uniqueable extends ColumnDetails {
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		boolean unique();

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		void unique(boolean value);
	}
}
