/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.naming;

import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

import java.util.Locale;

import static java.lang.Character.isDigit;
import static java.lang.Character.isLowerCase;
import static java.lang.Character.isUpperCase;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Converts {@code camelCase} or {@code MixedCase} logical names to {@code snake_case}.
 * <p>
 * This strategy leaves quoted identifiers alone. If quoted identifiers should also be
 * processed, then this class may be extended the implement the required behavior. For
 * example:
 * <pre>
 * public class AlwaysSnakeEverythingStrategy extends PhysicalNamingStrategySnakeCaseImpl {
 *     &#64;Override
 *     protected Identifier quotedIdentifier(Identifier quotedName) {
 *         return super.unquotedIdentifier( quotedName ).quoted();
 *     }
 * }
 * </pre>
 *
 * @author Phillip Webb
 * @author Madhura Bhave
 *
 * @since 7.0
 */
// Originally copied from Spring's SpringPhysicalNamingStrategy as this strategy is popular there.
public class PhysicalNamingStrategySnakeCaseImpl implements PhysicalNamingStrategy {

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Identifier toPhysicalCatalogName(Identifier logicalName, JdbcEnvironment jdbcEnvironment) {
		return apply( logicalName );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Identifier toPhysicalSchemaName(Identifier logicalName, JdbcEnvironment jdbcEnvironment) {
		return apply( logicalName );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Identifier toPhysicalTableName(Identifier logicalName, JdbcEnvironment jdbcEnvironment) {
		return apply( logicalName );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Identifier toPhysicalSequenceName(Identifier logicalName, JdbcEnvironment jdbcEnvironment) {
		return apply( logicalName );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Identifier toPhysicalColumnName(Identifier logicalName, JdbcEnvironment jdbcEnvironment) {
		return apply( logicalName );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private Identifier apply(final Identifier name) {
		if ( name == null ) {
			return null;
		}
		else if ( name.isQuoted() ) {
			return quotedIdentifier( name );
		}
		else {
			return unquotedIdentifier( name );
		}
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	private String camelCaseToSnakeCase(String name) {
		final var builder = new StringBuilder( name.replace( '.', '_' ) );
		for ( int i = 1; i < builder.length() - 1; i++ ) {
			if ( isUnderscoreRequired( builder.charAt( i - 1 ), builder.charAt( i ), builder.charAt( i + 1 ) ) ) {
				builder.insert( i++, '_' );
			}
		}
		return builder.toString();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected Identifier unquotedIdentifier(Identifier name) {
		return new Identifier( camelCaseToSnakeCase( name.getText() ).toLowerCase( Locale.ROOT ) );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected Identifier quotedIdentifier(Identifier quotedName) {
		return quotedName;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private boolean isUnderscoreRequired(final char before, final char current, final char after) {
		return ( isLowerCase( before ) || isDigit( before ) )
			&& isUpperCase( current )
			&& ( isLowerCase( after ) || isDigit( after ) );
	}
}
