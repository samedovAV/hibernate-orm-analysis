/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.annotations.internal;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.hibernate.annotations.DialectOverride;
import org.hibernate.models.spi.ModelsContext;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

@SuppressWarnings({ "ClassExplicitlyAnnotation", "unused" })
@jakarta.annotation.Generated("org.hibernate.orm.build.annotations.ClassGeneratorProcessor")
public class OverrideVersionAnnotation implements DialectOverride.Version {
	private int major;
	private int minor;

	/**
	 * Used in creating dynamic annotation instances (e.g. from XML)
	 */
	public OverrideVersionAnnotation(ModelsContext modelContext) {
		this.minor = 0;
	}

	/**
	 * Used in creating annotation instances from JDK variant
	 */
	public OverrideVersionAnnotation(DialectOverride.Version annotation, ModelsContext modelContext) {
		major( annotation.major() );
		minor( annotation.minor() );
	}

	/**
	 * Used in creating annotation instances from Jandex variant
	 */
	public OverrideVersionAnnotation(Map<String, Object> attributeValues, ModelsContext modelContext) {
		major( (Integer) attributeValues.get( "major" ) );
		minor( (Integer) attributeValues.get( "minor" ) );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<? extends Annotation> annotationType() {
		return DialectOverride.Version.class;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int major() {
		return major;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void major(int value) {
		this.major = value;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int minor() {
		return minor;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void minor(int value) {
		this.minor = value;
	}
}
