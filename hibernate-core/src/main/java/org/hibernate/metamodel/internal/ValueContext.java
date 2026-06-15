/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.internal;

import org.hibernate.mapping.Value;
import org.hibernate.metamodel.ValueClassification;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A contract for defining the meta information about a {@link Value}
 */
public interface ValueContext {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ValueClassification getValueClassification();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Value getHibernateValue();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Class<?> getJpaBindableType();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	AttributeMetadata<?,?> getAttributeMetadata();
}
