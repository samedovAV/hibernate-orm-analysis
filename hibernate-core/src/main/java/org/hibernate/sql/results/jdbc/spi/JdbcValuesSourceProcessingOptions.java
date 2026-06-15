/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.results.jdbc.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Essentially processing options only for entity loading
 *
 * @author Steve Ebersole
 */
public interface JdbcValuesSourceProcessingOptions {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object getEffectiveOptionalObject();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getEffectiveOptionalEntityName();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object getEffectiveOptionalId();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean shouldReturnProxies();

	JdbcValuesSourceProcessingOptions NO_OPTIONS =
			new JdbcValuesSourceProcessingOptions() {
				@Override
				@Prove(complexity = Complexity.O_1, n = "", count = {})
				public Object getEffectiveOptionalObject() {
					return null;
				}

				@Override
				@Prove(complexity = Complexity.O_1, n = "", count = {})
				public String getEffectiveOptionalEntityName() {
					return null;
				}

				@Override
				@Prove(complexity = Complexity.O_1, n = "", count = {})
				public Object getEffectiveOptionalId() {
					return null;
				}

				@Override
				@Prove(complexity = Complexity.O_1, n = "", count = {})
				public boolean shouldReturnProxies() {
					return true;
				}
			};
}
