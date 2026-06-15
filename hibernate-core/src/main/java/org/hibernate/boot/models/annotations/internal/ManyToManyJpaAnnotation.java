/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.annotations.internal;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.hibernate.boot.models.annotations.spi.AttributeMarker;
import org.hibernate.models.spi.ModelsContext;

import jakarta.persistence.ManyToMany;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

@SuppressWarnings({ "ClassExplicitlyAnnotation", "unused" })
@jakarta.annotation.Generated("org.hibernate.orm.build.annotations.ClassGeneratorProcessor")
public class ManyToManyJpaAnnotation implements ManyToMany,
		AttributeMarker.Fetchable,
		AttributeMarker.Cascadeable,
		AttributeMarker.Mappable {
	private java.lang.Class<?> targetEntity;
	private jakarta.persistence.CascadeType[] cascade;
	private jakarta.persistence.FetchType fetch;
	private String mappedBy;

	/**
	 * Used in creating dynamic annotation instances (e.g. from XML)
	 */
	public ManyToManyJpaAnnotation(ModelsContext modelContext) {
		this.targetEntity = void.class;
		this.cascade = new jakarta.persistence.CascadeType[0];
		this.fetch = jakarta.persistence.FetchType.LAZY;
		this.mappedBy = "";
	}

	/**
	 * Used in creating annotation instances from JDK variant
	 */
	public ManyToManyJpaAnnotation(ManyToMany annotation, ModelsContext modelContext) {
		this.targetEntity = annotation.targetEntity();
		this.cascade = annotation.cascade();
		this.fetch = annotation.fetch();
		this.mappedBy = annotation.mappedBy();
	}

	/**
	 * Used in creating annotation instances from Jandex variant
	 */
	public ManyToManyJpaAnnotation(Map<String, Object> attributeValues, ModelsContext modelContext) {
		this.targetEntity = (Class<?>) attributeValues.get( "targetEntity" );
		this.cascade = (jakarta.persistence.CascadeType[]) attributeValues.get( "cascade" );
		this.fetch = (jakarta.persistence.FetchType) attributeValues.get( "fetch" );
		this.mappedBy = (String) attributeValues.get( "mappedBy" );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<? extends Annotation> annotationType() {
		return ManyToMany.class;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public java.lang.Class<?> targetEntity() {
		return targetEntity;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void targetEntity(java.lang.Class<?> value) {
		this.targetEntity = value;
	}


	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public jakarta.persistence.CascadeType[] cascade() {
		return cascade;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void cascade(jakarta.persistence.CascadeType[] value) {
		this.cascade = value;
	}


	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public jakarta.persistence.FetchType fetch() {
		return fetch;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void fetch(jakarta.persistence.FetchType value) {
		this.fetch = value;
	}


	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String mappedBy() {
		return mappedBy;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void mappedBy(String value) {
		this.mappedBy = value;
	}


}
