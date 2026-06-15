/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.jdbc.internal;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Represents the understood types or styles of formatting.
 *
 * @author Steve Ebersole
 */
public enum FormatStyle {
	/**
	 * Formatting for SELECT, INSERT, UPDATE and DELETE statements
	 */
	BASIC( "basic", new BasicFormatterImpl() ),
	/**
	 * Formatting for DDL (CREATE, ALTER, DROP, etc) statements
	 */
	DDL( "ddl", DDLFormatterImpl.INSTANCE ),
	/**
	 * Syntax highlighting via ANSI escape codes
	 */
	HIGHLIGHT( "highlight", HighlightingFormatter.INSTANCE ),
	/**
	 * No formatting
	 */
	NONE( "none", NoFormatImpl.INSTANCE );

	private final String name;
	private final Formatter formatter;

	FormatStyle(String name, Formatter formatter) {
		this.name = name;
		this.formatter = formatter;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getName() {
		return name;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Formatter getFormatter() {
		return formatter;
	}

	private static class NoFormatImpl implements Formatter {
		/**
		 * Singleton access
		 */
		public static final NoFormatImpl INSTANCE = new NoFormatImpl();

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public String format(String source) {
			return source;
		}
	}
}
