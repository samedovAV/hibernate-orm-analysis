/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Contract describing source of "table specification" information.
 *
 * @author Steve Ebersole
 */
public interface TableSpecificationSource {
	/**
	 * Obtain the supplied schema name
	 *
	 * @return The schema name. If {@code null}, the binder will apply the default.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getExplicitSchemaName();

	/**
	 * Obtain the supplied catalog name
	 *
	 * @return The catalog name. If {@code null}, the binder will apply the default.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getExplicitCatalogName();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getComment();

}
