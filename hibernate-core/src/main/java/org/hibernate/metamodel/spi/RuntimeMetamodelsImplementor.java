/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.spi;

import org.hibernate.metamodel.RuntimeMetamodels;
import org.hibernate.metamodel.model.domain.spi.JpaMetamodelImplementor;
import org.hibernate.type.BindingContext;
import org.hibernate.type.MappingContext;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * SPI extending {@link RuntimeMetamodels} and mixing in {@link MappingContext}.
 *
 * @author Steve Ebersole
 */
public interface RuntimeMetamodelsImplementor extends RuntimeMetamodels, MappingContext, BindingContext {
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	MappingMetamodelImplementor getMappingMetamodel();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaMetamodelImplementor getJpaMetamodel();
}
