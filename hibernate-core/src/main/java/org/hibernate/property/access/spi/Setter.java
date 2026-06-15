/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.property.access.spi;

import java.lang.reflect.Method;

import jakarta.annotation.Nullable;
import org.hibernate.Remove;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * The contract for setting the value of a persistent attribute on its container/owner.
 *
 * @author Gavin King
 * @author Steve Ebersole
 */
@Remove // Remove/replace with a different SPI that is based on Hibernate Models
public interface Setter {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void set(Object target, @Nullable Object value);

	/**
	 * Optional operation (may return {@code null})
	 */
	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	String getMethodName();

	/**
	 * Optional operation (may return {@code null})
	 */
	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	Method getMethod();
}
