/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.annotations.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Commonality for annotations that are named within a {@linkplain RepeatableContainer repeatable container}.
 *
 * @author Steve Ebersole
 */
public interface Named {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String name();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void name(String name);
}
