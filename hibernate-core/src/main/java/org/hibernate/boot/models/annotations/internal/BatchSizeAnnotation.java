/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.annotations.internal;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.hibernate.annotations.BatchSize;
import org.hibernate.models.spi.ModelsContext;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

@SuppressWarnings({ "ClassExplicitlyAnnotation", "unused" })
@jakarta.annotation.Generated("org.hibernate.orm.build.annotations.ClassGeneratorProcessor")
public class BatchSizeAnnotation implements BatchSize {
	private int size;

	/**
	 * Used in creating dynamic annotation instances (e.g. from XML)
	 */
	public BatchSizeAnnotation(ModelsContext modelContext) {
	}

	/**
	 * Used in creating annotation instances from JDK variant
	 */
	public BatchSizeAnnotation(BatchSize annotation, ModelsContext modelContext) {
		this.size = annotation.size();
	}

	/**
	 * Used in creating annotation instances from Jandex variant
	 */
	public BatchSizeAnnotation(Map<String, Object> attributeValues, ModelsContext modelContext) {
		this.size = (int) attributeValues.get( "size" );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<? extends Annotation> annotationType() {
		return BatchSize.class;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int size() {
		return size;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void size(int value) {
		this.size = value;
	}


}
