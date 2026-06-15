/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.loader.ast.internal;

import org.hibernate.loader.ast.spi.Loadable;
import org.hibernate.metamodel.mapping.ModelPart;
import org.hibernate.sql.exec.spi.JdbcSelect;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Common contract for SQL AST based loading
 *
 * @author Steve Ebersole
 */
public interface LoadPlan {
	/**
	 * The thing being loaded
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Loadable getLoadable();

	/**
	 * The part of the thing being loaded used to restrict which loadables get loaded
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ModelPart getRestrictivePart();

	/**
	 * The JdbcSelect for the load
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JdbcSelect getJdbcSelect();
}
