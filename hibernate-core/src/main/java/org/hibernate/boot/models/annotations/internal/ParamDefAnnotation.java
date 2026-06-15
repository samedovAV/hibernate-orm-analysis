/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.annotations.internal;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.hibernate.annotations.ParamDef;
import org.hibernate.models.spi.ModelsContext;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

@SuppressWarnings({ "ClassExplicitlyAnnotation", "unused" })
@jakarta.annotation.Generated("org.hibernate.orm.build.annotations.ClassGeneratorProcessor")
public class ParamDefAnnotation implements ParamDef {
	private String name;
	private java.lang.Class<?> type;
	private java.lang.Class<? extends java.util.function.Supplier> resolver;

	/**
	 * Used in creating dynamic annotation instances (e.g. from XML)
	 */
	public ParamDefAnnotation(ModelsContext modelContext) {
		this.resolver = java.util.function.Supplier.class;
	}

	/**
	 * Used in creating annotation instances from JDK variant
	 */
	public ParamDefAnnotation(ParamDef annotation, ModelsContext modelContext) {
		this.name = annotation.name();
		this.type = annotation.type();
		this.resolver = annotation.resolver();
	}

	/**
	 * Used in creating annotation instances from Jandex variant
	 */
	public ParamDefAnnotation(Map<String, Object> attributeValues, ModelsContext modelContext) {
		this.name = (String) attributeValues.get( "name" );
		this.type = (Class<?>) attributeValues.get( "type" );
		this.resolver = (Class<? extends java.util.function.Supplier>) attributeValues.get( "resolver" );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<? extends Annotation> annotationType() {
		return ParamDef.class;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String name() {
		return name;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void name(String value) {
		this.name = value;
	}


	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public java.lang.Class<?> type() {
		return type;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void type(java.lang.Class<?> value) {
		this.type = value;
	}


	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public java.lang.Class<? extends java.util.function.Supplier> resolver() {
		return resolver;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void resolver(java.lang.Class<? extends java.util.function.Supplier> value) {
		this.resolver = value;
	}


}
