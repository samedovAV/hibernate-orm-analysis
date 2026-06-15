/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Access to appending SQL fragments to a StringBuilder
 */
public class StringBuilderSqlAppender implements SqlAppender {

	protected final StringBuilder sb;

	public StringBuilderSqlAppender() {
		this(new StringBuilder());
	}

	public StringBuilderSqlAppender(StringBuilder sb) {
		this.sb = sb;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public StringBuilder getStringBuilder() {
		return sb;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void appendSql(String fragment) {
		append( fragment );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void appendSql(char fragment) {
		append( fragment );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void appendSql(int value) {
		sb.append( value );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void appendSql(long value) {
		sb.append( value );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void appendSql(boolean value) {
		sb.append( value );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void appendSql(double value) {
		sb.append( value );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void appendSql(float value) {
		sb.append( value );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Appendable append(CharSequence csq) {
		sb.append( csq );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Appendable append(CharSequence csq, int start, int end) {
		sb.append( csq, start, end );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Appendable append(char c) {
		sb.append( c );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String toString() {
		return sb.toString();
	}
}
