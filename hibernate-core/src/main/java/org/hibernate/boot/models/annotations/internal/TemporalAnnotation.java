/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.annotations.internal;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.hibernate.annotations.Temporal;
import org.hibernate.models.spi.ModelsContext;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

@SuppressWarnings({ "ClassExplicitlyAnnotation", "unused" })
@jakarta.annotation.Generated("org.hibernate.orm.build.annotations.ClassGeneratorProcessor")
public class TemporalAnnotation implements Temporal {
	private String rowStart;
	private String rowEnd;
	private int secondPrecision;

	/**
	 * Used in creating dynamic annotation instances (e.g. from XML)
	 */
	public TemporalAnnotation(ModelsContext modelContext) {
		this.rowStart = "effective";
		this.rowEnd = "superseded";
		this.secondPrecision = -1;
	}

	/**
	 * Used in creating annotation instances from JDK variant
	 */
	public TemporalAnnotation(Temporal annotation, ModelsContext modelContext) {
		this.rowStart = annotation.rowStart();
		this.rowEnd = annotation.rowEnd();
		this.secondPrecision = annotation.secondPrecision();
	}

	/**
	 * Used in creating annotation instances from Jandex variant
	 */
	public TemporalAnnotation(Map<String, Object> attributeValues, ModelsContext modelContext) {
		this.rowStart = (String) attributeValues.get( "rowStart" );
		this.rowEnd = (String) attributeValues.get( "rowEnd" );
		final Integer secondPrecision = (Integer) attributeValues.get( "secondPrecision" );
		this.secondPrecision = secondPrecision == null ? -1 : secondPrecision;
		if ( this.rowStart == null ) {
			this.rowStart = "effective";
		}
		if ( this.rowEnd == null ) {
			this.rowEnd = "superseded";
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<? extends Annotation> annotationType() {
		return Temporal.class;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String rowStart() {
		return rowStart;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setRowStart(String rowStart) {
		this.rowStart = rowStart;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String rowEnd() {
		return rowEnd;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setRowEnd(String rowEnd) {
		this.rowEnd = rowEnd;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int secondPrecision() {
		return secondPrecision;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setSecondPrecision(int secondPrecision) {
		this.secondPrecision = secondPrecision;
	}

}
