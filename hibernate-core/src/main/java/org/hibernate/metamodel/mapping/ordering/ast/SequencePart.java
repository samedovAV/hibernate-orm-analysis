/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.mapping.ordering.ast;

import org.hibernate.metamodel.mapping.ordering.TranslationContext;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Represents an individual identifier in a dot-identifier sequence
 *
 * @author Steve Ebersole
 */
public interface SequencePart {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SequencePart resolvePathPart(
			String name,
			String identifier,
			boolean isTerminal,
			TranslationContext translationContext);
}
