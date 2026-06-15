/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.mapping;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A meta attribute is a named value or values.
 *
 * @author Gavin King
 */
public class MetaAttribute implements Serializable {
	private final String name;
	private final java.util.List<String> values = new ArrayList<>();

	public MetaAttribute(String name) {
		this.name = name;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getName() {
		return name;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public java.util.List<String> getValues() {
		return Collections.unmodifiableList(values);
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void addValue(String value) {
		values.add(value);
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getValue() {
		if ( values.size()!=1 ) {
			throw new IllegalStateException("no unique value");
		}
		return values.get(0);
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isMultiValued() {
		return values.size()>1;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String toString() {
		return "[" + name + "=" + values + "]";
	}
}
