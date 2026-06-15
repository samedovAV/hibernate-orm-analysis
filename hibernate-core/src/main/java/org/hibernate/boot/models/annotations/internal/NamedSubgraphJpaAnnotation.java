/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.annotations.internal;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.hibernate.models.spi.ModelsContext;

import jakarta.persistence.NamedSubgraph;

import static org.hibernate.boot.models.JpaAnnotations.NAMED_SUBGRAPH;
import static org.hibernate.boot.models.internal.OrmAnnotationHelper.extractJdkValue;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

@SuppressWarnings({ "ClassExplicitlyAnnotation", "unused" })
@jakarta.annotation.Generated("org.hibernate.orm.build.annotations.ClassGeneratorProcessor")
public class NamedSubgraphJpaAnnotation implements NamedSubgraph {
	private String name;
	private java.lang.Class<?> type;
	private jakarta.persistence.NamedAttributeNode[] attributeNodes;

	/**
	 * Used in creating dynamic annotation instances (e.g. from XML)
	 */
	public NamedSubgraphJpaAnnotation(ModelsContext modelContext) {
		this.type = void.class;
	}

	/**
	 * Used in creating annotation instances from JDK variant
	 */
	public NamedSubgraphJpaAnnotation(NamedSubgraph annotation, ModelsContext modelContext) {
		this.name = annotation.name();
		this.type = annotation.type();
		this.attributeNodes = extractJdkValue( annotation, NAMED_SUBGRAPH, "attributeNodes", modelContext );
	}

	/**
	 * Used in creating annotation instances from Jandex variant
	 */
	public NamedSubgraphJpaAnnotation(Map<String, Object> attributeValues, ModelsContext modelContext) {
		this.name = (String) attributeValues.get( "name" );
		this.type = (Class<?>) attributeValues.get( "type" );
		this.attributeNodes = (jakarta.persistence.NamedAttributeNode[]) attributeValues.get( "attributeNodes" );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<? extends Annotation> annotationType() {
		return NamedSubgraph.class;
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
	public jakarta.persistence.NamedAttributeNode[] attributeNodes() {
		return attributeNodes;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void attributeNodes(jakarta.persistence.NamedAttributeNode[] value) {
		this.attributeNodes = value;
	}


}
