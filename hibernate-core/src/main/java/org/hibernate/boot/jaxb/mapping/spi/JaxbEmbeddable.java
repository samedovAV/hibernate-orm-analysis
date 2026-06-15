/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.mapping.spi;

import jakarta.annotation.Nullable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface JaxbEmbeddable extends JaxbManagedType {
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getName();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setName(@Nullable String name);
}
