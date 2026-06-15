/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Generator for SqlAliasBase instances based on a stem.
 *
 * @author Steve Ebersole
 */
public interface SqlAliasBaseGenerator {
	/**
	 * Generate the SqlAliasBase based on the given stem.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqlAliasBase createSqlAliasBase(String stem);
}
