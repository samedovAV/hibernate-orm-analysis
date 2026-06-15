/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.type.descriptor.jdbc;

import org.hibernate.metamodel.spi.ValueAccess;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Marco Belladelli
 */
public class StructAttributeValues implements ValueAccess {
	private final Object[] attributeValues;
	private final int size;
	private Object discriminator;

	public StructAttributeValues(int size, Object[] rawJdbcValues) {
		this.size = size;
		if ( rawJdbcValues == null || size != rawJdbcValues.length) {
			attributeValues = new Object[size];
		}
		else {
			attributeValues = rawJdbcValues;
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object[] getValues() {
		return attributeValues;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setAttributeValue(int index, Object value) {
		if ( index == size ) {
			discriminator = value;
		}
		else {
			attributeValues[index] = value;
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object getDiscriminator() {
		return discriminator;
	}
}
