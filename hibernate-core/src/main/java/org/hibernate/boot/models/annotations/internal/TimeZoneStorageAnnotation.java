/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.annotations.internal;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.hibernate.annotations.TimeZoneStorage;
import org.hibernate.models.spi.ModelsContext;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

@SuppressWarnings({ "ClassExplicitlyAnnotation", "unused" })
@jakarta.annotation.Generated("org.hibernate.orm.build.annotations.ClassGeneratorProcessor")
public class TimeZoneStorageAnnotation implements TimeZoneStorage {
	private org.hibernate.annotations.TimeZoneStorageType value;

	/**
	 * Used in creating dynamic annotation instances (e.g. from XML)
	 */
	public TimeZoneStorageAnnotation(ModelsContext modelContext) {
		this.value = org.hibernate.annotations.TimeZoneStorageType.AUTO;
	}

	/**
	 * Used in creating annotation instances from JDK variant
	 */
	public TimeZoneStorageAnnotation(TimeZoneStorage annotation, ModelsContext modelContext) {
		this.value = annotation.value();
	}

	/**
	 * Used in creating annotation instances from Jandex variant
	 */
	public TimeZoneStorageAnnotation(Map<String, Object> attributeValues, ModelsContext modelContext) {
		this.value = (org.hibernate.annotations.TimeZoneStorageType) attributeValues.get( "value" );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<? extends Annotation> annotationType() {
		return TimeZoneStorage.class;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public org.hibernate.annotations.TimeZoneStorageType value() {
		return value;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void value(org.hibernate.annotations.TimeZoneStorageType value) {
		this.value = value;
	}


}
