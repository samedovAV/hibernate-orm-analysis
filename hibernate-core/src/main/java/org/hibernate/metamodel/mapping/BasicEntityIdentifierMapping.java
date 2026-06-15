/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.mapping;

import org.hibernate.metamodel.mapping.internal.SingleAttributeIdentifierMapping;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Mapping for a simple, single-column identifier
 *
 * @author Steve Ebersole
 */
public interface BasicEntityIdentifierMapping extends SingleAttributeIdentifierMapping, BasicValuedModelPart {
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default int getFetchableKey() {
		return -1;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isNullable();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isInsertable();

}
