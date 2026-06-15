/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql;
import org.hibernate.dialect.Dialect;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * An alias generator for SQL identifiers.
 *
 * @author Gavin King
 */
public final class Alias {

	private final int length;
	private final String suffix;

	/**
	 * Constructor for Alias.
	 */
	public Alias(int length, String suffix) {
		super();
		this.length = (suffix==null) ? length : length - suffix.length();
		this.suffix = suffix;
	}

	/**
	 * Constructor for Alias.
	 */
	public Alias(String suffix) {
		super();
		this.length = Integer.MAX_VALUE;
		this.suffix = suffix;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String toAliasString(String sqlIdentifier) {
		char begin = sqlIdentifier.charAt(0);
		int quoteType = Dialect.QUOTE.indexOf(begin);
		String unquoted = getUnquotedAliasString(sqlIdentifier, quoteType);
		if ( quoteType >= 0 ) {
			char endQuote = Dialect.CLOSED_QUOTE.charAt(quoteType);
			return begin + unquoted + endQuote;
		}
		else {
			return unquoted;
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String toUnquotedAliasString(String sqlIdentifier) {
		return getUnquotedAliasString(sqlIdentifier);
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	private String getUnquotedAliasString(String sqlIdentifier) {
		char begin = sqlIdentifier.charAt(0);
		int quoteType = Dialect.QUOTE.indexOf(begin);
		return getUnquotedAliasString(sqlIdentifier, quoteType);
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private String getUnquotedAliasString(String sqlIdentifier, int quoteType) {
		String unquoted = sqlIdentifier;
		if ( quoteType >= 0 ) {
			//if the identifier is quoted, remove the quotes
			unquoted = unquoted.substring( 1, unquoted.length()-1 );
		}
		if ( unquoted.length() > length ) {
			//truncate the identifier to the max alias length, less the suffix length
			unquoted = unquoted.substring(0, length);
		}
		return ( suffix == null ) ? unquoted : unquoted + suffix;
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String[] toUnquotedAliasStrings(String[] sqlIdentifiers) {
		String[] aliases = new String[ sqlIdentifiers.length ];
		for ( int i=0; i<sqlIdentifiers.length; i++ ) {
			aliases[i] = toUnquotedAliasString(sqlIdentifiers[i]);
		}
		return aliases;
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String[] toAliasStrings(String[] sqlIdentifiers) {
		String[] aliases = new String[ sqlIdentifiers.length ];
		for ( int i=0; i<sqlIdentifiers.length; i++ ) {
			aliases[i] = toAliasString(sqlIdentifiers[i]);
		}
		return aliases;
	}

}
