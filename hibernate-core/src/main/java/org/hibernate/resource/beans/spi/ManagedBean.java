/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.resource.beans.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Generalized contract for a (CDI or Spring) "managed bean" as seen by Hibernate
 *
 * @author Steve Ebersole
 */
public interface ManagedBean<T> {
	/**
	 * The bean Java type
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Class<T> getBeanClass();

	/**
	 * The bean reference
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T getBeanInstance();
}
