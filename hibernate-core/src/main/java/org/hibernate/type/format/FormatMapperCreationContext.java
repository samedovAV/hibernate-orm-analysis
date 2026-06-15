/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.type.format;

import org.hibernate.Incubating;
import org.hibernate.boot.spi.BootstrapContext;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * The creation context for {@link FormatMapper} that is passed as constructor argument to implementations.
 */
@Incubating
public interface FormatMapperCreationContext {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	BootstrapContext getBootstrapContext();

}
