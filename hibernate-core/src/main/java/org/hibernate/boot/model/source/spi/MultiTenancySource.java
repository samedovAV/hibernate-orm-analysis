/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Describes the source information related to mapping the multi-tenancy of an entity
 *
 * @author Steve Ebersole
 */
public interface MultiTenancySource {
	/**
	 * Obtain the column/formula information about the multi-tenancy discriminator.
	 *
	 * @return The column/formula information
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	RelationalValueSource getRelationalValueSource();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isShared();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean bindAsParameter();
}
