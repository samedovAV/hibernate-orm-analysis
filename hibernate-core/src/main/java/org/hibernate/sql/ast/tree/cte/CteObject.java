/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.cte;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * An object that is part of a WITH clause.
 *
 * @author Christian Beikov
 */
public interface CteObject {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getName();

}
