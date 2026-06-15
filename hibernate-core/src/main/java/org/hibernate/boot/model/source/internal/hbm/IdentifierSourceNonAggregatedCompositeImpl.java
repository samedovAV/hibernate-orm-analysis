/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.internal.hbm;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmCompositeIdType;
import org.hibernate.boot.model.IdentifierGeneratorDefinition;
import org.hibernate.boot.model.JavaTypeDescriptor;
import org.hibernate.boot.model.source.spi.AttributePath;
import org.hibernate.boot.model.source.spi.AttributeRole;
import org.hibernate.boot.model.source.spi.AttributeSource;
import org.hibernate.boot.model.source.spi.AttributeSourceContainer;
import org.hibernate.boot.model.source.spi.EmbeddableSource;
import org.hibernate.boot.model.source.spi.IdentifierSourceNonAggregatedComposite;
import org.hibernate.boot.model.source.spi.LocalMetadataBuildingContext;
import org.hibernate.boot.model.source.spi.SingularAttributeSource;
import org.hibernate.boot.model.source.spi.ToolingHintContext;
import org.hibernate.id.EntityIdentifierNature;
import org.hibernate.internal.util.StringHelper;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Models a composite identifier with is not not encapsulated in a dedicated "id class".
 *
 * @author Steve Ebersole
 */
class IdentifierSourceNonAggregatedCompositeImpl implements IdentifierSourceNonAggregatedComposite, EmbeddableSource {
	private final RootEntitySourceImpl rootEntitySource;

	private final AttributePath attributePathBase;
	private final AttributeRole attributeRoleBase;
	private final IdentifierGeneratorDefinition generatorDefinition;

	// NOTE: not typed because we need to expose as both:
	// 		List<AttributeSource>
	//		List<SingularAttributeSource>
	// :(
	private final List attributeSources;

	private final EmbeddableSource idClassSource;
	private final ToolingHintContext toolingHintContext;

	IdentifierSourceNonAggregatedCompositeImpl(RootEntitySourceImpl rootEntitySource) {
		this.rootEntitySource = rootEntitySource;

		this.attributePathBase = rootEntitySource.getAttributePathBase().append( "<id>" );
		this.attributeRoleBase = rootEntitySource.getAttributeRoleBase().append( "<id>" );
		this.generatorDefinition = EntityHierarchySourceImpl.interpretGeneratorDefinition(
				rootEntitySource.sourceMappingDocument(),
				rootEntitySource.getEntityNamingSource(),
				rootEntitySource.jaxbEntityMapping().getCompositeId().getGenerator()
		);

		this.attributeSources = new ArrayList();
		AttributesHelper.processCompositeKeySubAttributes(
				rootEntitySource.sourceMappingDocument(),
				new AttributesHelper.Callback() {
					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public AttributeSourceContainer getAttributeSourceContainer() {
						return IdentifierSourceNonAggregatedCompositeImpl.this;
					}

					@Override
					@SuppressWarnings("unchecked")
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public void addAttributeSource(AttributeSource attributeSource) {
						attributeSources.add( attributeSource );
					}
				},
				rootEntitySource.jaxbEntityMapping().getCompositeId().getKeyPropertyOrKeyManyToOne()
		);

		// NOTE : the HBM support for IdClass is very limited.  Essentially
		// we assume that all identifier attributes occur in the IdClass
		// using the same name and type.
		this.idClassSource = interpretIdClass(
				rootEntitySource.sourceMappingDocument(),
				rootEntitySource.jaxbEntityMapping().getCompositeId()
		);

		this.toolingHintContext = Helper.collectToolingHints(
				rootEntitySource.getToolingHintContext(),
				rootEntitySource.jaxbEntityMapping().getCompositeId()
		);
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private EmbeddableSource interpretIdClass(
			MappingDocument mappingDocument,
			JaxbHbmCompositeIdType jaxbHbmCompositeIdMapping) {
		// if <composite-id/> is null here we have much bigger problems :)

		if ( !jaxbHbmCompositeIdMapping.isMapped() ) {
			return null;
		}

		final String className = jaxbHbmCompositeIdMapping.getClazz();
		if ( StringHelper.isEmpty( className ) ) {
			return null;
		}

		final String idClassQualifiedName = mappingDocument.qualifyClassName( className );
		final JavaTypeDescriptor idClassTypeDescriptor = new JavaTypeDescriptor() {
			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public String getName() {
				return idClassQualifiedName;
			}
		};
		return new IdClassSource( idClassTypeDescriptor, rootEntitySource, mappingDocument );
	}

	@Override
	@SuppressWarnings("unchecked")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<SingularAttributeSource> getAttributeSourcesMakingUpIdentifier() {
		return attributeSources;
	}


	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public EmbeddableSource getIdClassSource() {
		return idClassSource;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public IdentifierGeneratorDefinition getIdentifierGeneratorDescriptor() {
		return generatorDefinition;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public EntityIdentifierNature getNature() {
		return EntityIdentifierNature.NON_AGGREGATED_COMPOSITE;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JavaTypeDescriptor getTypeDescriptor() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getParentReferenceAttributeName() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isDynamic() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isUnique() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public AttributePath getAttributePathBase() {
		return attributePathBase;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public AttributeRole getAttributeRoleBase() {
		return attributeRoleBase;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<AttributeSource> attributeSources() {
		return attributeSources;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public LocalMetadataBuildingContext getLocalMetadataBuildingContext() {
		return rootEntitySource.metadataBuildingContext();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public EmbeddableSource getEmbeddableSource() {
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ToolingHintContext getToolingHintContext() {
		return toolingHintContext;
	}
}
