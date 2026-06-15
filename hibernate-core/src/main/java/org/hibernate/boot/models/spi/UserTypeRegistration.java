/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.spi;

import org.hibernate.models.spi.ClassDetails;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Registration for a {@linkplain org.hibernate.usertype.UserType}
 *
 * @see org.hibernate.annotations.TypeRegistration
 *
 * @author Steve Ebersole
 */
public class UserTypeRegistration {
	private final ClassDetails domainClass;
	private final ClassDetails userTypeClass;

	public UserTypeRegistration(ClassDetails domainClass, ClassDetails userTypeClass) {
		this.domainClass = domainClass;
		this.userTypeClass = userTypeClass;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ClassDetails getDomainClass() {
		return domainClass;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ClassDetails getUserTypeClass() {
		return userTypeClass;
	}
}
