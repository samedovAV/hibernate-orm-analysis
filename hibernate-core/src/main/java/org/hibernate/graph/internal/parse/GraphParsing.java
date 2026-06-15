/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.graph.internal.parse;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import org.hibernate.GraphParserMode;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.grammars.graph.GraphLanguageLexer;
import org.hibernate.grammars.graph.GraphLanguageParser;
import org.hibernate.grammars.graph.LegacyGraphLanguageParser;
import org.hibernate.graph.internal.parse.strategy.ModernGraphParsingStrategy;
import org.hibernate.graph.internal.parse.strategy.GraphParsingStrategy;
import org.hibernate.graph.internal.parse.strategy.LegacyGraphParsingStrategy;
import org.hibernate.graph.spi.GraphParserEntityNameResolver;
import org.hibernate.graph.spi.GraphImplementor;
import org.hibernate.graph.spi.RootGraphImplementor;
import org.hibernate.metamodel.model.domain.EntityDomainType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Helper for dealing with graph text parsing.
 *
 * @author Steve Ebersole
 */
public class GraphParsing {

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public static <T> RootGraphImplementor<T> parse(
			EntityDomainType<T> entityDomainType,
			String graphText,
			SessionFactoryImplementor sessionFactory) {
		return getGraphParsingStrategy( sessionFactory ).parse(
				entityDomainType,
				graphText,
				sessionFactory
		);
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public static <T> RootGraphImplementor<T> parse(
			Class<T> entityClass,
			String graphText,
			SessionFactoryImplementor sessionFactory) {
		return parse( sessionFactory.getJpaMetamodel().entity( entityClass ),
				graphText, sessionFactory );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public static RootGraphImplementor<?> parse(
			String entityName,
			String graphText,
			SessionFactoryImplementor sessionFactory) {
		return parse( sessionFactory.getJpaMetamodel().entity( entityName ),
				graphText, sessionFactory );
	}

	@Deprecated(forRemoval = true)
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public static RootGraphImplementor<?> parse(
			String graphText,
			SessionFactoryImplementor sessionFactory) {

		return getGraphParsingStrategy( sessionFactory ).parse( graphText, sessionFactory );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public static <T> RootGraphImplementor<T> visit(
			EntityDomainType<T> rootType,
			LegacyGraphLanguageParser.AttributeListContext attributeListContext,
			SessionFactoryImplementor sessionFactory) {
		return visit( rootType, attributeListContext, sessionFactory.getJpaMetamodel()::findEntityType );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public static <T> RootGraphImplementor<T> visit(
			EntityDomainType<T> rootType,
			LegacyGraphLanguageParser.AttributeListContext attributeListContext,
			GraphParserEntityNameResolver entityNameResolver) {
		return visit( null, rootType, attributeListContext, entityNameResolver );
	}

	@Deprecated(forRemoval = true)
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static @Nonnull LegacyGraphLanguageParser.GraphContext parseLegacyGraphText(String graphText) {
		final var lexer = new GraphLanguageLexer( CharStreams.fromString( graphText ) );
		final var parser = new LegacyGraphLanguageParser( new CommonTokenStream( lexer ) );
		return parser.graph();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static @Nonnull GraphLanguageParser.GraphContext parseText(String graphText) {
		final var lexer = new GraphLanguageLexer( CharStreams.fromString( graphText ) );
		final var parser = new GraphLanguageParser( new CommonTokenStream( lexer ) );
		return parser.graph();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static <T> RootGraphImplementor<T> visit(
			@Nullable String name,
			EntityDomainType<T> rootType,
			LegacyGraphLanguageParser.AttributeListContext graphElementListContext,
			GraphParserEntityNameResolver entityNameResolver) {

		return LegacyGraphParsingStrategy.parse( name, rootType, graphElementListContext, entityNameResolver );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static <T> RootGraphImplementor<T> visit(
			@Nullable String name,
			EntityDomainType<T> rootType,
			GraphLanguageParser.GraphElementListContext graphElementListContext,
			GraphParserEntityNameResolver entityNameResolver) {

		return ModernGraphParsingStrategy.parse( name, rootType, graphElementListContext, entityNameResolver );
	}

	/**
	 * Parse the passed graph textual representation into the passed Graph.
	 * Essentially overlays the text representation on top of the graph.
	 */
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public static void parseInto(
			GraphImplementor<?> targetGraph,
			CharSequence graphString,
			SessionFactoryImplementor sessionFactory) {

		getGraphParsingStrategy( sessionFactory ).parseInto( targetGraph, graphString.toString(), sessionFactory );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private static GraphParsingStrategy getGraphParsingStrategy(SessionFactoryImplementor sessionFactory) {
		final GraphParserMode mode = sessionFactory.getSessionFactoryOptions().getGraphParserMode();

		if ( mode == GraphParserMode.MODERN ) {
			return new ModernGraphParsingStrategy();
		}

		return new LegacyGraphParsingStrategy();
	}

}
