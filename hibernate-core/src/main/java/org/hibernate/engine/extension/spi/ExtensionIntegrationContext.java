/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.extension.spi;

import org.hibernate.Incubating;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

@Incubating
public interface ExtensionIntegrationContext {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SharedSessionContractImplementor getSession();
}
