/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.annotations.internal;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.hibernate.models.spi.ModelsContext;

import jakarta.persistence.SequenceGenerator;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

@SuppressWarnings({ "ClassExplicitlyAnnotation", "unused" })
@jakarta.annotation.Generated("org.hibernate.orm.build.annotations.ClassGeneratorProcessor")
public class SequenceGeneratorJpaAnnotation implements SequenceGenerator {
	private String name;
	private String sequenceName;
	private String catalog;
	private String schema;
	private int initialValue;
	private int allocationSize;
	private String options;

	/**
	 * Used in creating dynamic annotation instances (e.g. from XML)
	 */
	public SequenceGeneratorJpaAnnotation(ModelsContext modelContext) {
		this( "", modelContext );
	}

	/**
	 * Used in creating named, defaulted annotation instances.  Generally this
	 * is a situation where we have:<ol>
	 *     <li>{@linkplain jakarta.persistence.GeneratedValue#strategy()} set to {@linkplain jakarta.persistence.GenerationType#SEQUENCE}</li>
	 *     <li>{@linkplain jakarta.persistence.GeneratedValue#generator()} set to a non-empty String, but with no matching {@linkplain SequenceGenerator}</li>
	 * </ol>
	 */
	public SequenceGeneratorJpaAnnotation(String name, ModelsContext modelContext) {
		this.name = name;
		this.sequenceName = "";
		this.catalog = "";
		this.schema = "";
		this.initialValue = 1;
		this.allocationSize = 50;
		this.options = "";
	}

	/**
	 * Used in creating annotation instances from JDK variant
	 */
	public SequenceGeneratorJpaAnnotation(SequenceGenerator annotation, ModelsContext modelContext) {
		this.name = annotation.name();
		this.sequenceName = annotation.sequenceName();
		this.catalog = annotation.catalog();
		this.schema = annotation.schema();
		this.initialValue = annotation.initialValue();
		this.allocationSize = annotation.allocationSize();
		this.options = annotation.options();
	}

	/**
	 * Used in creating annotation instances from Jandex variant
	 */
	public SequenceGeneratorJpaAnnotation(
			Map<String, Object> attributeValues,
			ModelsContext modelContext) {
		this.name = (String) attributeValues.get( "name" );
		this.sequenceName = (String) attributeValues.get( "sequenceName" );
		this.catalog = (String) attributeValues.get( "catalog" );
		this.schema = (String) attributeValues.get( "schema" );
		this.initialValue = (int) attributeValues.get( "initialValue" );
		this.allocationSize = (int) attributeValues.get( "allocationSize" );
		this.options = (String) attributeValues.get( "options" );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<? extends Annotation> annotationType() {
		return SequenceGenerator.class;
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
	public String sequenceName() {
		return sequenceName;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void sequenceName(String value) {
		this.sequenceName = value;
	}


	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String catalog() {
		return catalog;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void catalog(String value) {
		this.catalog = value;
	}


	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String schema() {
		return schema;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void schema(String value) {
		this.schema = value;
	}


	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int initialValue() {
		return initialValue;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void initialValue(int value) {
		this.initialValue = value;
	}


	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int allocationSize() {
		return allocationSize;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void allocationSize(int value) {
		this.allocationSize = value;
	}


	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String options() {
		return options;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void options(String value) {
		this.options = value;
	}


}
