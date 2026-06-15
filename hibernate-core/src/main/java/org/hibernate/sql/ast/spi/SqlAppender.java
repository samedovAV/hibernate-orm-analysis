/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.spi;

import org.hibernate.internal.util.QuotingHelper;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Access to appending SQL fragments to an in-flight buffer
 *
 * @author Steve Ebersole
 */
@FunctionalInterface
public interface SqlAppender extends Appendable {
	String NO_SEPARATOR = "";
	String COMMA_SEPARATOR = ",";
	char COMMA_SEPARATOR_CHAR = ',';
	char WHITESPACE = ' ';

	char OPEN_PARENTHESIS = '(';
	char CLOSE_PARENTHESIS = ')';

	char PARAM_MARKER = '?';

	String NULL_KEYWORD = "null";

	/**
	 * Add the passed fragment into the in-flight buffer
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void appendSql(String fragment);

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default void appendSql(char fragment) {
		appendSql( Character.toString( fragment ) );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default void appendSql(int value) {
		appendSql( Integer.toString( value ) );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default void appendSql(long value) {
		appendSql( Long.toString( value ) );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default void appendSql(boolean value) {
		appendSql( String.valueOf( value ) );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default void appendSql(double value) {
		appendSql( String.valueOf( value ) );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default void appendSql(float value) {
		appendSql( String.valueOf( value ) );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default void appendDoubleQuoteEscapedString(String value) {
		final StringBuilder sb = new StringBuilder( value.length() + 2 );
		QuotingHelper.appendDoubleQuoteEscapedString( sb, value );
		appendSql( sb.toString() );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default void appendSingleQuoteEscapedString(String value) {
		final StringBuilder sb = new StringBuilder( value.length() + 2 );
		QuotingHelper.appendSingleQuoteEscapedString( sb, value );
		appendSql( sb.toString() );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default Appendable append(CharSequence csq) {
		appendSql( csq.toString() );
		return this;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default Appendable append(CharSequence csq, int start, int end) {
		appendSql( csq.toString().substring( start, end ) );
		return this;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default Appendable append(char c) {
		appendSql( Character.toString( c ) );
		return this;
	}

}
