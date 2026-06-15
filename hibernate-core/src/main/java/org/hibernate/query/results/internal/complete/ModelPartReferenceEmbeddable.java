/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.results.internal.complete;

import org.hibernate.metamodel.mapping.EmbeddableValuedModelPart;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface ModelPartReferenceEmbeddable extends ModelPartReference {
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EmbeddableValuedModelPart getReferencedPart();
}
