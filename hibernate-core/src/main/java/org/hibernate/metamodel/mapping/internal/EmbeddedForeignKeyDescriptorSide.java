/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.mapping.internal;

import org.hibernate.metamodel.mapping.EmbeddableValuedModelPart;
import org.hibernate.metamodel.mapping.ForeignKeyDescriptor;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class EmbeddedForeignKeyDescriptorSide implements ForeignKeyDescriptor.Side {

	private final ForeignKeyDescriptor.Nature nature;
	private final EmbeddableValuedModelPart modelPart;

	public EmbeddedForeignKeyDescriptorSide(
			ForeignKeyDescriptor.Nature nature,
			EmbeddableValuedModelPart modelPart) {
		this.nature = nature;
		this.modelPart = modelPart;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ForeignKeyDescriptor.Nature getNature() {
		return nature;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public EmbeddableValuedModelPart getModelPart() {
		return modelPart;
	}
}
