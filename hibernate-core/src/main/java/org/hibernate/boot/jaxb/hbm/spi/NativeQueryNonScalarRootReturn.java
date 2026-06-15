/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.hbm.spi;

import java.util.List;

import org.hibernate.LockMode;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Commonality for non-scalar root returns for a native query result mapping
 *
 * @author Strong Liu
 * @author Steve Ebersole
 */
public interface NativeQueryNonScalarRootReturn extends NativeQueryReturn {
	/**
	 * Access the alias associated with this return
	 *
	 * @return The alias
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getAlias();

	/**
	 * Access the LockMode associated with this return
	 *
	 * @return The LockMode
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	LockMode getLockMode();

	/**
	 * Access the nested property mappings associated with this return
	 *
	 * @return The nested property mappings
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JaxbHbmNativeQueryPropertyReturnType> getReturnProperty();
}
