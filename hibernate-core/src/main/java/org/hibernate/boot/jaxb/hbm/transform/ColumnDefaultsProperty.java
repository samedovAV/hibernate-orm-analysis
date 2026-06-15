/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.hbm.transform;

import org.hibernate.mapping.Property;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class ColumnDefaultsProperty implements ColumnDefaults {
	private final Property property;

	public ColumnDefaultsProperty(Property property) {
		this.property = property;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Boolean isNullable() {
		return property.isOptional();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Boolean isInsertable() {
		return property.isInsertable();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Boolean isUpdatable() {
		return property.isUpdatable();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Integer getLength() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Integer getScale() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Integer getPrecision() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Boolean isUnique() {
		return Boolean.FALSE;
	}
}
