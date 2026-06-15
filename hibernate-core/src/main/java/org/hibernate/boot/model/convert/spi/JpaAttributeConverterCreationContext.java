/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.convert.spi;

import org.hibernate.resource.beans.spi.ManagedBeanRegistry;
import org.hibernate.type.descriptor.java.spi.JavaTypeRegistry;
import org.hibernate.type.spi.TypeConfiguration;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Access to information that implementors of
 * {@link ConverterDescriptor#createJpaAttributeConverter} might
 * need
 *
 * @author Steve Ebersole
 */
public interface JpaAttributeConverterCreationContext {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ManagedBeanRegistry getManagedBeanRegistry();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	TypeConfiguration getTypeConfiguration();

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default JavaTypeRegistry getJavaTypeRegistry() {
		return getTypeConfiguration().getJavaTypeRegistry();
	}
}
