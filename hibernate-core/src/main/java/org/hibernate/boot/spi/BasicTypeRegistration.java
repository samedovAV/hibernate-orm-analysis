/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.spi;

import org.hibernate.type.BasicType;
import org.hibernate.type.CustomType;
import org.hibernate.type.spi.TypeConfiguration;
import org.hibernate.usertype.UserType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class BasicTypeRegistration {
	private final BasicType<?> basicType;
	private final String[] registrationKeys;

	public BasicTypeRegistration(BasicType<?> basicType) {
		this( basicType, basicType.getRegistrationKeys() );
	}

	public BasicTypeRegistration(BasicType<?> basicType, String[] registrationKeys) {
		this.basicType = basicType;
		this.registrationKeys = registrationKeys;
	}

	public BasicTypeRegistration(UserType<?> type, String[] keys, TypeConfiguration typeConfiguration) {
		this( new CustomType<>( type, keys, typeConfiguration ), keys );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public BasicType<?> getBasicType() {
		return basicType;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String[] getRegistrationKeys() {
		return registrationKeys;
	}
}
