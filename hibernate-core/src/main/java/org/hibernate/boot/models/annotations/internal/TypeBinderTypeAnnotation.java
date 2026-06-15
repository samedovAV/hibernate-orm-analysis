/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.annotations.internal;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.hibernate.annotations.TypeBinderType;
import org.hibernate.models.spi.ModelsContext;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

@SuppressWarnings({ "ClassExplicitlyAnnotation", "unused" })
@jakarta.annotation.Generated("org.hibernate.orm.build.annotations.ClassGeneratorProcessor")
public class TypeBinderTypeAnnotation implements TypeBinderType {
	private java.lang.Class<? extends org.hibernate.binder.TypeBinder<?>> binder;

	/**
	 * Used in creating dynamic annotation instances (e.g. from XML)
	 */
	public TypeBinderTypeAnnotation(ModelsContext modelContext) {
	}

	/**
	 * Used in creating annotation instances from JDK variant
	 */
	public TypeBinderTypeAnnotation(TypeBinderType annotation, ModelsContext modelContext) {
		this.binder = annotation.binder();
	}

	/**
	 * Used in creating annotation instances from Jandex variant
	 */
	public TypeBinderTypeAnnotation(Map<String, Object> attributeValues, ModelsContext modelContext) {
		this.binder = (Class<? extends org.hibernate.binder.TypeBinder<?>>) attributeValues.get( "binder" );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<? extends Annotation> annotationType() {
		return TypeBinderType.class;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public java.lang.Class<? extends org.hibernate.binder.TypeBinder<?>> binder() {
		return binder;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void binder(java.lang.Class<? extends org.hibernate.binder.TypeBinder<?>> value) {
		this.binder = value;
	}


}
