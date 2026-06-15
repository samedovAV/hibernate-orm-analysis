/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.results.internal.complete;

import org.hibernate.metamodel.mapping.EntityValuedModelPart;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface ModelPartReferenceEntity extends ModelPartReference {
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EntityValuedModelPart getReferencedPart();
}
