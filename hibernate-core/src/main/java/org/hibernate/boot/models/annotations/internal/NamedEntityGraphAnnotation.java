/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.annotations.internal;

import org.hibernate.annotations.NamedEntityGraph;
import org.hibernate.models.spi.ModelsContext;

import java.lang.annotation.Annotation;
import java.util.Map;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * @author Steve Ebersole
 */
public class NamedEntityGraphAnnotation implements NamedEntityGraph {
	private String name;
	private String graph;
	private Class<?> root;

	/**
	 * Used in creating dynamic annotation instances (e.g. from XML)
	 */
	public NamedEntityGraphAnnotation(ModelsContext modelContext) {
		name = "";
	}

	/**
	 * Used in creating annotation instances from JDK variant
	 */
	public NamedEntityGraphAnnotation(NamedEntityGraph annotation, ModelsContext modelContext) {
		this.name = annotation.name();
		this.graph = annotation.graph();
		this.root = annotation.root();
	}

	/**
	 * Used in creating annotation instances from Jandex variant
	 */
	public NamedEntityGraphAnnotation(Map<String, Object> attributeValues, ModelsContext modelContext) {
		this.name = (String) attributeValues.get( "name" );
		this.graph = (String) attributeValues.get( "graph" );
		this.root = (Class<?>) attributeValues.get( "root" );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<? extends Annotation> annotationType() {
		return NamedEntityGraph.class;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<?> root() {
		return this.root;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void root(Class<?> root) {
		this.root = root;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String name() {
		return name;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void name(String name) {
		this.name = name;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String graph() {
		return graph;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void graph(String graph) {
		this.graph = graph;
	}
}
