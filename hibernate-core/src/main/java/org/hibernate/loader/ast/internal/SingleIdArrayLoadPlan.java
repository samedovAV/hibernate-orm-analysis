/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.loader.ast.internal;

import org.hibernate.LockOptions;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.metamodel.mapping.EntityMappingType;
import org.hibernate.metamodel.mapping.ModelPart;
import org.hibernate.sql.ast.tree.select.SelectStatement;
import org.hibernate.sql.exec.spi.JdbcParametersList;
import org.hibernate.sql.results.internal.RowTransformerArrayImpl;
import org.hibernate.sql.results.spi.RowTransformer;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A load plan for loading an array of state by a single restrictive part.
 *
 * @author Christian Beikov
 */
public class SingleIdArrayLoadPlan extends SingleIdLoadPlan<Object[]> {

	public SingleIdArrayLoadPlan(
			EntityMappingType entityMappingType,
			ModelPart restrictivePart,
			SelectStatement sqlAst,
			JdbcParametersList jdbcParameters,
			LockOptions lockOptions,
			SessionFactoryImplementor sessionFactory) {
		super( entityMappingType, restrictivePart, sqlAst, jdbcParameters, lockOptions, sessionFactory );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected RowTransformer<Object[]> getRowTransformer() {
		return RowTransformerArrayImpl.instance();
	}

}
