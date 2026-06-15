/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.jdbc.env.spi;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import org.hibernate.engine.jdbc.env.internal.NormalizingIdentifierHelperImpl;
import org.hibernate.internal.util.collections.ArrayHelper;

import org.jboss.logging.Logger;

import static java.util.Collections.addAll;
import static org.hibernate.internal.util.StringHelper.splitAtCommas;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Builder for {@link IdentifierHelper} instances.  Mainly here to allow progressive
 * building of the immutable (after instantiation) {@link IdentifierHelper}.
 *
 * @author Steve Ebersole
 */
public class IdentifierHelperBuilder {
	private static final Logger LOG = Logger.getLogger( IdentifierHelperBuilder.class );

	private final JdbcEnvironment jdbcEnvironment;

	private NameQualifierSupport nameQualifierSupport = NameQualifierSupport.BOTH;

	//TODO interesting computer science puzzle: find a more compact representation?
	// we only need "contains" on this set, and it has to be case sensitive and efficient.
	private final TreeSet<String> reservedWords = new TreeSet<>( String.CASE_INSENSITIVE_ORDER );

	private boolean globallyQuoteIdentifiers = false;
	private boolean skipGlobalQuotingForColumnDefinitions = false;
	private boolean autoQuoteKeywords = true;
	private boolean autoQuoteInitialUnderscore = false;
	private boolean autoQuoteDollar = false;
	private IdentifierCaseStrategy unquotedCaseStrategy = IdentifierCaseStrategy.UPPER;
	private IdentifierCaseStrategy quotedCaseStrategy = IdentifierCaseStrategy.MIXED;

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static IdentifierHelperBuilder from(JdbcEnvironment jdbcEnvironment) {
		return new IdentifierHelperBuilder( jdbcEnvironment );
	}

	private IdentifierHelperBuilder(JdbcEnvironment jdbcEnvironment) {
		this.jdbcEnvironment = jdbcEnvironment;
	}

	/**
	 * Applies any reserved words reported via {@link DatabaseMetaData#getSQLKeywords()}
	 *
	 * @param metaData The metadata to get reserved words from
	 *
	 * @throws SQLException Any access to DatabaseMetaData can case SQLException; just re-throw.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void applyReservedWords(DatabaseMetaData metaData) throws SQLException {
		if ( metaData != null
				// Important optimisation: skip loading all keywords
				// from the DB when autoQuoteKeywords is disabled
				&& autoQuoteKeywords ) {
			addAll( reservedWords, splitAtCommas( metaData.getSQLKeywords() ) );
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void applyIdentifierCasing(DatabaseMetaData metaData) throws SQLException {
		if ( metaData != null ) {
			final int unquotedAffirmatives = ArrayHelper.countTrue(
					metaData.storesLowerCaseIdentifiers(),
					metaData.storesUpperCaseIdentifiers(),
					metaData.storesMixedCaseIdentifiers()
			);

			if ( unquotedAffirmatives == 0 ) {
				LOG.trace( "JDBC driver metadata reported database stores unquoted identifiers in neither upper, lower nor mixed case" );
			}
			else {
				// NOTE: still "dodgy" if more than one is true
				if ( unquotedAffirmatives > 1 ) {
					LOG.trace( "JDBC driver metadata reported database stores unquoted identifiers in more than one case" );
				}

				if ( metaData.storesUpperCaseIdentifiers() ) {
					unquotedCaseStrategy = IdentifierCaseStrategy.UPPER;
				}
				else if ( metaData.storesLowerCaseIdentifiers() ) {
					unquotedCaseStrategy = IdentifierCaseStrategy.LOWER;
				}
				else {
					unquotedCaseStrategy = IdentifierCaseStrategy.MIXED;
				}
			}

			final int quotedAffirmatives = ArrayHelper.countTrue(
					metaData.storesLowerCaseQuotedIdentifiers(),
					metaData.storesUpperCaseQuotedIdentifiers(),
					metaData.storesMixedCaseQuotedIdentifiers()
			);

			if ( quotedAffirmatives == 0 ) {
				LOG.trace( "JDBC driver metadata reported database stores quoted identifiers in neither upper, lower nor mixed case" );
			}
			else {
				// NOTE: still "dodgy" if more than one is true
				if ( quotedAffirmatives > 1 ) {
					LOG.trace( "JDBC driver metadata reported database stores quoted identifiers in more than one case" );
				}

				if ( metaData.storesMixedCaseQuotedIdentifiers() ) {
					quotedCaseStrategy = IdentifierCaseStrategy.MIXED;
				}
				else if ( metaData.storesLowerCaseQuotedIdentifiers() ) {
					quotedCaseStrategy = IdentifierCaseStrategy.LOWER;
				}
				else {
					quotedCaseStrategy = IdentifierCaseStrategy.UPPER;
				}
			}
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isGloballyQuoteIdentifiers() {
		return globallyQuoteIdentifiers;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setGloballyQuoteIdentifiers(boolean globallyQuoteIdentifiers) {
		this.globallyQuoteIdentifiers = globallyQuoteIdentifiers;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isSkipGlobalQuotingForColumnDefinitions() {
		return skipGlobalQuotingForColumnDefinitions;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setSkipGlobalQuotingForColumnDefinitions(boolean skipGlobalQuotingForColumnDefinitions) {
		this.skipGlobalQuotingForColumnDefinitions = skipGlobalQuotingForColumnDefinitions;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setAutoQuoteKeywords(boolean autoQuoteKeywords) {
		this.autoQuoteKeywords = autoQuoteKeywords;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setAutoQuoteInitialUnderscore(boolean autoQuoteInitialUnderscore) {
		this.autoQuoteInitialUnderscore = autoQuoteInitialUnderscore;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setAutoQuoteDollar(boolean autoQuoteDollar) {
		this.autoQuoteDollar = autoQuoteDollar;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public NameQualifierSupport getNameQualifierSupport() {
		return nameQualifierSupport;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setNameQualifierSupport(NameQualifierSupport nameQualifierSupport) {
		this.nameQualifierSupport = nameQualifierSupport == null ? NameQualifierSupport.BOTH : nameQualifierSupport;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public IdentifierCaseStrategy getUnquotedCaseStrategy() {
		return unquotedCaseStrategy;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setUnquotedCaseStrategy(IdentifierCaseStrategy unquotedCaseStrategy) {
		this.unquotedCaseStrategy = unquotedCaseStrategy;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public IdentifierCaseStrategy getQuotedCaseStrategy() {
		return quotedCaseStrategy;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setQuotedCaseStrategy(IdentifierCaseStrategy quotedCaseStrategy) {
		this.quotedCaseStrategy = quotedCaseStrategy;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void clearReservedWords() {
		this.reservedWords.clear();
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void applyReservedWords(String... words) {
		applyReservedWords( Arrays.asList( words ) );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void applyReservedWords(Collection<String> words) {
		//No use when autoQuoteKeywords is disabled
		if ( autoQuoteKeywords ) {
			reservedWords.addAll( words );
		}
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void applyReservedWords(Set<String> words) {
		applyReservedWords( (Collection<String>) words );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setReservedWords(Set<String> words) {
		clearReservedWords();
		applyReservedWords( words );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public IdentifierHelper build() {
		if ( unquotedCaseStrategy == quotedCaseStrategy ) {
			LOG.debugf(
					"IdentifierCaseStrategy for both quoted and unquoted identifiers was set " +
							"to the same strategy [%s]; that will likely lead to problems in schema update " +
							"and validation if using quoted identifiers",
					unquotedCaseStrategy.name()
			);
		}

		return new NormalizingIdentifierHelperImpl(
				jdbcEnvironment,
				nameQualifierSupport,
				globallyQuoteIdentifiers,
				skipGlobalQuotingForColumnDefinitions,
				autoQuoteKeywords,
				autoQuoteInitialUnderscore,
				autoQuoteDollar,
				reservedWords,
				unquotedCaseStrategy,
				quotedCaseStrategy
		);
	}
}
