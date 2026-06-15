/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.bytecode.enhance.internal.bytebuddy;

import java.lang.reflect.Member;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

public class ForeignPackageMember implements Member {

	private final Class<?> foreignPackageAccessor;
	private final Member member;

	public ForeignPackageMember(Class<?> foreignPackageAccessor, Member member) {
		this.foreignPackageAccessor = foreignPackageAccessor;
		this.member = member;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<?> getForeignPackageAccessor() {
		return foreignPackageAccessor;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Member getMember() {
		return member;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Class<?> getDeclaringClass() {
		return member.getDeclaringClass();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getName() {
		return member.getName();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public int getModifiers() {
		return member.getModifiers();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isSynthetic() {
		return member.isSynthetic();
	}
}
