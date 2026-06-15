/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.bytecode.enhance.internal.bytebuddy;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

public class BridgeMembersClassInfo {
	private final Class<?> clazz;
	private final List<String> propertyNames = new ArrayList<>();
	private final List<Member> getters = new ArrayList<>();
	private final List<Member> setters = new ArrayList<>();

	public BridgeMembersClassInfo(Class<?> clazz) {
		this.clazz = Objects.requireNonNull(clazz);
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<?> getClazz() {
		return clazz;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Iterable<Member> gettersIterable() {
		return getters;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Iterable<Member> settersIterable() {
		return setters;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean containsGetter(Member getter) {
		return getters.contains( getter );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean containsSetter(Member setter) {
		return setters.contains( setter );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void addGetter(Member getter) {
		getters.add( getter );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void addSetter(Member setter) {
		setters.add( setter );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void addProperty(String propertyName) {
		propertyNames.add( propertyName );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean propertyNamesIsEmpty() {
		return propertyNames.isEmpty();
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String encodeName() {
		return NameEncodeHelper.encodeName(
				propertyNames.toArray( new String[0] ),
				getters.toArray( new Member[0] ),
				setters.toArray( new Member[0] )
		);
	}
}
