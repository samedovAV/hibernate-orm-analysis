/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.annotations.internal;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.hibernate.annotations.TypeRegistration;
import org.hibernate.models.spi.ModelsContext;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

@SuppressWarnings({ "ClassExplicitlyAnnotation", "unused" })
@jakarta.annotation.Generated("org.hibernate.orm.build.annotations.ClassGeneratorProcessor")
public class TypeRegistrationAnnotation implements TypeRegistration {
	private java.lang.Class<?> basicClass;
	private java.lang.Class<? extends org.hibernate.usertype.UserType<?>> userType;

	/**
	 * Used in creating dynamic annotation instances (e.g. from XML)
	 */
	public TypeRegistrationAnnotation(ModelsContext modelContext) {
	}

	/**
	 * Used in creating annotation instances from JDK variant
	 */
	public TypeRegistrationAnnotation(TypeRegistration annotation, ModelsContext modelContext) {
		this.basicClass = annotation.basicClass();
		this.userType = annotation.userType();
	}

	/**
	 * Used in creating annotation instances from Jandex variant
	 */
	public TypeRegistrationAnnotation(Map<String, Object> attributeValues, ModelsContext modelContext) {
		this.basicClass = (Class<?>) attributeValues.get( "basicClass" );
		this.userType = (Class<? extends org.hibernate.usertype.UserType<?>>) attributeValues.get( "userType" );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<? extends Annotation> annotationType() {
		return TypeRegistration.class;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public java.lang.Class<?> basicClass() {
		return basicClass;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void basicClass(java.lang.Class<?> value) {
		this.basicClass = value;
	}


	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public java.lang.Class<? extends org.hibernate.usertype.UserType<?>> userType() {
		return userType;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void userType(java.lang.Class<? extends org.hibernate.usertype.UserType<?>> value) {
		this.userType = value;
	}


}
