/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.mapping.ordering.ast;

import org.hibernate.metamodel.mapping.ModelPart;
import org.hibernate.metamodel.mapping.PluralAttributeMapping;
import org.hibernate.spi.NavigablePath;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Represents a domain-path (model part path) used in an order-by fragment
 *
 * @author Steve Ebersole
 */
public interface DomainPath extends OrderingExpression, SequencePart {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NavigablePath getNavigablePath();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	DomainPath getLhs();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ModelPart getReferenceModelPart();

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default PluralAttributeMapping getPluralAttribute() {
		return getLhs().getPluralAttribute();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default String toDescriptiveText() {
		return "domain path (" + getNavigablePath().getFullPath() + ")";
	}
}
