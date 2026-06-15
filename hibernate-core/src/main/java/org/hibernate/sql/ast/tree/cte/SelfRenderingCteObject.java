/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.cte;

import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.sql.ast.SqlAstTranslator;
import org.hibernate.sql.ast.spi.SqlAppender;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A self rendering object that is part of a WITH clause, like a function.
 *
 * @author Christian Beikov
 */
public interface SelfRenderingCteObject extends CteObject {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void render(SqlAppender sqlAppender, SqlAstTranslator<?> walker, SessionFactoryImplementor sessionFactory);

}
