/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.spi;

import org.hibernate.models.spi.ClassDetails;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * {@linkplain org.hibernate.type.descriptor.java.JavaType} registration
 *
 * @see org.hibernate.annotations.JavaTypeRegistration
 * @see org.hibernate.boot.jaxb.mapping.spi.JaxbJavaTypeRegistrationImpl
 *
 * @author Steve Ebersole
 */
public class JavaTypeRegistration {
	private final ClassDetails domainType;
	private final ClassDetails descriptor;

	public JavaTypeRegistration(ClassDetails domainType, ClassDetails descriptor) {
		this.domainType = domainType;
		this.descriptor = descriptor;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ClassDetails getDomainType() {
		return domainType;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ClassDetails getDescriptor() {
		return descriptor;
	}
}
