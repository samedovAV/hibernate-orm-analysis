/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.spi;

import org.hibernate.boot.internal.MetadataImpl;
import org.hibernate.service.Service;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Allows bootstrapping Hibernate ORM using a custom {@link SessionFactoryBuilderImplementor}.
 */
public interface SessionFactoryBuilderService extends Service {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SessionFactoryBuilderImplementor createSessionFactoryBuilder(MetadataImpl metadata, BootstrapContext bootstrapContext);

}
