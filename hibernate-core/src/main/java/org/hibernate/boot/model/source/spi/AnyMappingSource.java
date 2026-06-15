/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Base description for all discriminated associations ("any mappings"), including
 * {@code <any/>}, {@code <many-to-any/>}, etc.
 *
 * @author Steve Ebersole
 */
public interface AnyMappingSource {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	AnyDiscriminatorSource getDiscriminatorSource();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	AnyKeySource getKeySource();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean isLazy() {
		return true;
	}
}
