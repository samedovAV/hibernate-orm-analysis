/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.mapping.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * JAXB binding interface for lifecycle callbacks.
 *
 * @author Strong Liu
 * @author Steve Ebersole
 */
public interface JaxbLifecycleCallback {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getMethodName();
}
