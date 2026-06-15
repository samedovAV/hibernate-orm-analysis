/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.annotations.internal;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.hibernate.annotations.FetchProfileOverride;
import org.hibernate.models.spi.ModelsContext;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

@SuppressWarnings({ "ClassExplicitlyAnnotation", "unused" })
@jakarta.annotation.Generated("org.hibernate.orm.build.annotations.ClassGeneratorProcessor")
public class FetchProfileOverrideAnnotation implements FetchProfileOverride {

	private org.hibernate.annotations.FetchMode mode;
	private jakarta.persistence.FetchType fetch;
	private String profile;

	/**
	 * Used in creating dynamic annotation instances (e.g. from XML)
	 */
	public FetchProfileOverrideAnnotation(ModelsContext modelContext) {
		this.mode = org.hibernate.annotations.FetchMode.JOIN;
		this.fetch = jakarta.persistence.FetchType.EAGER;
	}

	/**
	 * Used in creating annotation instances from JDK variant
	 */
	public FetchProfileOverrideAnnotation(FetchProfileOverride annotation, ModelsContext modelContext) {
		this.mode = annotation.mode();
		this.fetch = annotation.fetch();
		this.profile = annotation.profile();
	}

	/**
	 * Used in creating annotation instances from Jandex variant
	 */
	public FetchProfileOverrideAnnotation(
			Map<String, Object> attributeValues,
			ModelsContext modelContext) {
		this.mode = (org.hibernate.annotations.FetchMode) attributeValues.get( "mode" );
		this.fetch = (jakarta.persistence.FetchType) attributeValues.get( "fetch" );
		this.profile = (String) attributeValues.get( "profile" );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<? extends Annotation> annotationType() {
		return FetchProfileOverride.class;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public org.hibernate.annotations.FetchMode mode() {
		return mode;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void mode(org.hibernate.annotations.FetchMode value) {
		this.mode = value;
	}


	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public jakarta.persistence.FetchType fetch() {
		return fetch;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void fetch(jakarta.persistence.FetchType value) {
		this.fetch = value;
	}


	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String profile() {
		return profile;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void profile(String value) {
		this.profile = value;
	}


}
