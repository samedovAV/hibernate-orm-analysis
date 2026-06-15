/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.named;

import org.hibernate.metamodel.mapping.PluralAttributeMapping;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface ModelPartResultMementoCollection extends ModelPartResultMemento {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	PluralAttributeMapping getPluralAttributeDescriptor();
}
