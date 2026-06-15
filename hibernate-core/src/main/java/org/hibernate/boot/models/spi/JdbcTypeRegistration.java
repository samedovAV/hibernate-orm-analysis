/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.spi;

import org.hibernate.models.spi.ClassDetails;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * {@linkplain org.hibernate.type.descriptor.jdbc.JdbcType} registration
 *
 * @see org.hibernate.annotations.JdbcTypeRegistration
 *
 * @author Steve Ebersole
 */
public class JdbcTypeRegistration {
	private final Integer code;
	private final ClassDetails descriptor;

	public JdbcTypeRegistration(Integer code, ClassDetails descriptor) {
		this.code = code;
		this.descriptor = descriptor;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Integer getCode() {
		return code;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ClassDetails getDescriptor() {
		return descriptor;
	}
}
