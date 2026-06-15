/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.type.spi;

import org.hibernate.metamodel.mapping.EmbeddableValuedModelPart;
import org.hibernate.metamodel.mapping.internal.MappingModelCreationProcess;
import org.hibernate.type.CompositeType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface CompositeTypeImplementor extends CompositeType {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void injectMappingModelPart(EmbeddableValuedModelPart part, MappingModelCreationProcess process);
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EmbeddableValuedModelPart getMappingModelPart();
}
