/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.annotations.internal;

import java.lang.annotation.Annotation;

import jakarta.persistence.FetchType;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.FetchProfile;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

@SuppressWarnings({ "ClassExplicitlyAnnotation", "unused" })
public class FetchOverrideAnnotation implements FetchProfile.FetchOverride {
	private Class<?> entity;
	private String association;
	private FetchMode mode;
	private FetchType fetch;

	public FetchOverrideAnnotation() {
		this.mode = FetchMode.JOIN;
		this.fetch = FetchType.EAGER;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<? extends Annotation> annotationType() {
		return FetchProfile.FetchOverride.class;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<?> entity() {
		return entity;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void entity(Class<?> value) {
		this.entity = value;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String association() {
		return association;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void association(String value) {
		this.association = value;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public FetchMode mode() {
		return mode;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void mode(FetchMode value) {
		this.mode = value;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public FetchType fetch() {
		return fetch;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void fetch(FetchType value) {
		this.fetch = value;
	}
}
