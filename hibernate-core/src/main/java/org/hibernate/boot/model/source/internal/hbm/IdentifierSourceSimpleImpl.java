/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.internal.hbm;

import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmRootEntityType;
import org.hibernate.boot.model.IdentifierGeneratorDefinition;
import org.hibernate.boot.model.source.spi.IdentifierSourceSimple;
import org.hibernate.boot.model.source.spi.SingularAttributeSource;
import org.hibernate.boot.model.source.spi.ToolingHintContext;
import org.hibernate.id.EntityIdentifierNature;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Models a simple {@code <id/>} mapping
 *
 * @author Steve Ebersole
 */
class IdentifierSourceSimpleImpl implements IdentifierSourceSimple {
	private final SingularIdentifierAttributeSourceImpl attribute;
	private final IdentifierGeneratorDefinition generatorDefinition;
	private final String unsavedValue;

	private final ToolingHintContext toolingHintContext;

	public IdentifierSourceSimpleImpl(RootEntitySourceImpl rootEntitySource) {
		final JaxbHbmRootEntityType jaxbEntityMapping = rootEntitySource.jaxbEntityMapping();
		this.attribute = new SingularIdentifierAttributeSourceImpl(
				rootEntitySource.sourceMappingDocument(),
				rootEntitySource,
				jaxbEntityMapping.getId()
		);
		this.generatorDefinition = EntityHierarchySourceImpl.interpretGeneratorDefinition(
				rootEntitySource.sourceMappingDocument(),
				rootEntitySource.getEntityNamingSource(),
				rootEntitySource.jaxbEntityMapping().getId().getGenerator()
		);
		this.unsavedValue = jaxbEntityMapping.getId().getUnsavedValue();

		this.toolingHintContext = Helper.collectToolingHints(
				rootEntitySource.getToolingHintContext(),
				jaxbEntityMapping.getId()
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SingularAttributeSource getIdentifierAttributeSource() {
		return attribute;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public IdentifierGeneratorDefinition getIdentifierGeneratorDescriptor() {
		return generatorDefinition;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public EntityIdentifierNature getNature() {
		return EntityIdentifierNature.SIMPLE;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getUnsavedValue() {
		return unsavedValue;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ToolingHintContext getToolingHintContext() {
		return toolingHintContext;
	}
}
