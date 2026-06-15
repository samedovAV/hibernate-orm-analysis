/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.model.domain.internal;

import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Acts as a virtual Member definition for dynamic (Map-based) models.
 *
 * @author Brad Koehn
 */
public class MapMember implements Member {
	private final String name;
	private final Class<?> type;

	public MapMember(String name, Class<?> type) {
		this.name = name;
		this.type = type;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<?> getType() {
		return type;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getModifiers() {
		return Modifier.PUBLIC;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isSynthetic() {
		return false;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getName() {
		return name;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<?> getDeclaringClass() {
		return null;
	}
}
