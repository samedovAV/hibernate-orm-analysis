/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.mapping;

import org.hibernate.boot.spi.MetadataBuildingContext;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Andrea Boriero
 */
public interface Resolvable {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean resolve(MetadataBuildingContext buildingContext);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	BasicValue.Resolution<?> resolve();
}
