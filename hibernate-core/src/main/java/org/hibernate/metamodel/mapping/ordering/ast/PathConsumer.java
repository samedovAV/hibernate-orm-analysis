/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.mapping.ordering.ast;

import org.hibernate.metamodel.mapping.PluralAttributeMapping;
import org.hibernate.metamodel.mapping.ordering.TranslationContext;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Represents the translation of an individual part of a path in `@OrderBy` translation
 *
 * Similar in purpose to {@link org.hibernate.query.hql.spi.DotIdentifierConsumer}, but for `@OrderBy` translation
 *
 * @author Steve Ebersole
 */
public class PathConsumer {

	private final TranslationContext translationContext;

	private final SequencePart rootSequencePart;

	private final StringBuilder pathSoFar = new StringBuilder();
	private SequencePart currentPart;

	public PathConsumer(
			PluralAttributeMapping pluralAttributeMapping,
			TranslationContext translationContext) {
		this.translationContext = translationContext;

		this.rootSequencePart = new RootSequencePart( pluralAttributeMapping );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SequencePart getConsumedPart() {
		return currentPart;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void consumeIdentifier(
			String unquotedIdentifier,
			String identifier, boolean isBase,
			boolean isTerminal) {
		if ( isBase ) {
			// each time we start a new sequence we need to reset our state
			reset();
		}

		if ( !pathSoFar.isEmpty() ) {
			pathSoFar.append( '.' );
		}
		pathSoFar.append( unquotedIdentifier );

		try {
			currentPart = currentPart.resolvePathPart( unquotedIdentifier, identifier, isTerminal, translationContext );
		}
		catch (PathResolutionException pre) {
			throw new PathResolutionException( "Unable to resolve path '" + pathSoFar + "'" );
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private void reset() {
		pathSoFar.setLength( 0 );
		currentPart = rootSequencePart;
	}
}
