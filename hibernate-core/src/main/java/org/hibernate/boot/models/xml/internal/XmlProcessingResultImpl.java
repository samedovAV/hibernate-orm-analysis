/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.xml.internal;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.boot.jaxb.mapping.spi.JaxbEmbeddableImpl;
import org.hibernate.boot.jaxb.mapping.spi.JaxbEntityImpl;
import org.hibernate.boot.jaxb.mapping.spi.JaxbMappedSuperclassImpl;
import org.hibernate.boot.models.xml.spi.XmlProcessingResult;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class XmlProcessingResultImpl implements XmlProcessingResult {
	private final List<OverrideTuple<JaxbEntityImpl>> entityOverrides = new ArrayList<>();
	private final List<OverrideTuple<JaxbMappedSuperclassImpl>> mappedSuperclassesOverrides = new ArrayList<>();
	private final List<OverrideTuple<JaxbEmbeddableImpl>> embeddableOverrides = new ArrayList<>();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void addEntityOverride(OverrideTuple<JaxbEntityImpl> overrideTuple) {
		entityOverrides.add( overrideTuple );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void addMappedSuperclassesOverride(OverrideTuple<JaxbMappedSuperclassImpl> overrideTuple) {
		mappedSuperclassesOverrides.add( overrideTuple );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void addEmbeddableOverride(OverrideTuple<JaxbEmbeddableImpl> overrideTuple) {
		embeddableOverrides.add( overrideTuple );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void apply() {
		ManagedTypeProcessor.processOverrideEmbeddable( getEmbeddableOverrides() );

		ManagedTypeProcessor.processOverrideMappedSuperclass( getMappedSuperclassesOverrides() );

		ManagedTypeProcessor.processOverrideEntity( getEntityOverrides() );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<OverrideTuple<JaxbEntityImpl>> getEntityOverrides() {
		return entityOverrides;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<OverrideTuple<JaxbMappedSuperclassImpl>> getMappedSuperclassesOverrides() {
		return mappedSuperclassesOverrides;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<OverrideTuple<JaxbEmbeddableImpl>> getEmbeddableOverrides() {
		return embeddableOverrides;
	}
}
