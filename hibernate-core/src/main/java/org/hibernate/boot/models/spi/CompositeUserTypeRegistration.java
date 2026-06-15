/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.spi;

import org.hibernate.models.spi.ClassDetails;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Registration for a {@linkplain org.hibernate.usertype.CompositeUserType}
 *
 * @see org.hibernate.annotations.CompositeTypeRegistration
 * @see org.hibernate.boot.jaxb.mapping.spi.JaxbCompositeUserTypeRegistrationImpl
 *
 * @author Steve Ebersole
 */
public class CompositeUserTypeRegistration {
	private final ClassDetails embeddableClass;
	private final ClassDetails userTypeClass;

	public CompositeUserTypeRegistration(ClassDetails embeddableClass, ClassDetails userTypeClass) {
		this.embeddableClass = embeddableClass;
		this.userTypeClass = userTypeClass;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ClassDetails getEmbeddableClass() {
		return embeddableClass;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ClassDetails getUserTypeClass() {
		return userTypeClass;
	}
}
