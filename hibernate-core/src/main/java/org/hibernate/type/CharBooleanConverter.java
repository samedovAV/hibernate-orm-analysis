/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.type;

import org.hibernate.type.descriptor.java.BooleanJavaType;
import org.hibernate.type.descriptor.java.CharacterJavaType;
import org.hibernate.type.descriptor.java.JavaType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Abstract supertype of converters which map {@link Boolean} to {@link Character}.
 *
 * @author Steve Ebersole
 * @author Gavin King
 */
public abstract class CharBooleanConverter implements StandardBooleanConverter<Character> {
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Character convertToDatabaseColumn(Boolean attribute) {
		return toRelationalValue( attribute );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Boolean convertToEntityAttribute(Character dbData) {
		return toDomainValue( dbData );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JavaType<Boolean> getDomainJavaType() {
		return BooleanJavaType.INSTANCE;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JavaType<Character> getRelationalJavaType() {
		return CharacterJavaType.INSTANCE;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected abstract String[] getValues();
}
