/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.extension.spi;

import org.hibernate.Incubating;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

@Incubating
public interface ExtensionIntegration<E extends Extension> {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Class<E> getExtensionType();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	E createExtension(ExtensionIntegrationContext context);
}
