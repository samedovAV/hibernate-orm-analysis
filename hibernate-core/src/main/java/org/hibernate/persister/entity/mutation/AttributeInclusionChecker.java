/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.persister.entity.mutation;

import org.hibernate.metamodel.mapping.SingularAttributeMapping;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
@FunctionalInterface
public interface AttributeInclusionChecker {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean include(int position, SingularAttributeMapping attribute);
}
