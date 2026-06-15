/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.annotations.internal;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.hibernate.annotations.NotFound;
import org.hibernate.models.spi.ModelsContext;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

@SuppressWarnings({ "ClassExplicitlyAnnotation", "unused" })
@jakarta.annotation.Generated("org.hibernate.orm.build.annotations.ClassGeneratorProcessor")
public class NotFoundAnnotation implements NotFound {
	private org.hibernate.annotations.NotFoundAction action;

	/**
	 * Used in creating dynamic annotation instances (e.g. from XML)
	 */
	public NotFoundAnnotation(ModelsContext modelContext) {
		this.action = org.hibernate.annotations.NotFoundAction.EXCEPTION;
	}

	/**
	 * Used in creating annotation instances from JDK variant
	 */
	public NotFoundAnnotation(NotFound annotation, ModelsContext modelContext) {
		this.action = annotation.action();
	}

	/**
	 * Used in creating annotation instances from Jandex variant
	 */
	public NotFoundAnnotation(Map<String, Object> attributeValues, ModelsContext modelContext) {
		this.action = (org.hibernate.annotations.NotFoundAction) attributeValues.get( "action" );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<? extends Annotation> annotationType() {
		return NotFound.class;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public org.hibernate.annotations.NotFoundAction action() {
		return action;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void action(org.hibernate.annotations.NotFoundAction value) {
		this.action = value;
	}


}
