/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.internal.hbm;

import java.util.Collections;
import java.util.List;

import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmCompositeIdType;
import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmToolingHintType;
import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmTuplizerType;
import org.hibernate.boot.model.IdentifierGeneratorDefinition;
import org.hibernate.boot.model.source.spi.AttributePath;
import org.hibernate.boot.model.source.spi.AttributeRole;
import org.hibernate.boot.model.source.spi.EmbeddableMapping;
import org.hibernate.boot.model.source.spi.EmbeddableSource;
import org.hibernate.boot.model.source.spi.EmbeddedAttributeMapping;
import org.hibernate.boot.model.source.spi.IdentifierSourceAggregatedComposite;
import org.hibernate.boot.model.source.spi.MapsIdSource;
import org.hibernate.boot.model.source.spi.NaturalIdMutability;
import org.hibernate.boot.model.source.spi.SingularAttributeSourceEmbedded;
import org.hibernate.boot.model.source.spi.ToolingHintContext;
import org.hibernate.id.EntityIdentifierNature;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Models a {@code <composite-id/>} mapping where we have a named (embeddable) attribute.
 *
 * @author Steve Ebersole
 */
class IdentifierSourceAggregatedCompositeImpl implements IdentifierSourceAggregatedComposite {
	private final SingularAttributeSourceAggregatedCompositeIdentifierImpl attributeSource;
	private final IdentifierGeneratorDefinition generatorDefinition;
	private final ToolingHintContext toolingHintContext;

	public IdentifierSourceAggregatedCompositeImpl(final RootEntitySourceImpl rootEntitySource) {
		final EmbeddedAttributeMappingAdapterAggregatedCompositeId compositeIdAdapter =
				new EmbeddedAttributeMappingAdapterAggregatedCompositeId( rootEntitySource );

		this.attributeSource = new SingularAttributeSourceAggregatedCompositeIdentifierImpl(
				rootEntitySource.sourceMappingDocument(),
				compositeIdAdapter
		);
		this.generatorDefinition = EntityHierarchySourceImpl.interpretGeneratorDefinition(
				rootEntitySource.sourceMappingDocument(),
				rootEntitySource.getEntityNamingSource(),
				rootEntitySource.jaxbEntityMapping().getCompositeId().getGenerator()
		);

		this.toolingHintContext = Helper.collectToolingHints(
				rootEntitySource.getToolingHintContext(),
				rootEntitySource.jaxbEntityMapping().getCompositeId()
		);

	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SingularAttributeSourceEmbedded getIdentifierAttributeSource() {
		return attributeSource;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<MapsIdSource> getMapsIdSources() {
		return Collections.emptyList();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public IdentifierGeneratorDefinition getIdentifierGeneratorDescriptor() {
		return generatorDefinition;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public EntityIdentifierNature getNature() {
		return EntityIdentifierNature.AGGREGATED_COMPOSITE;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public EmbeddableSource getEmbeddableSource() {
		return attributeSource.getEmbeddableSource();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ToolingHintContext getToolingHintContext() {
		return toolingHintContext;
	}

	private static class SingularAttributeSourceAggregatedCompositeIdentifierImpl
			extends AbstractSingularAttributeSourceEmbeddedImpl {
		private final EmbeddedAttributeMappingAdapterAggregatedCompositeId compositeIdAdapter;

		protected SingularAttributeSourceAggregatedCompositeIdentifierImpl(
				MappingDocument mappingDocument,
				EmbeddedAttributeMappingAdapterAggregatedCompositeId compositeIdAdapter) {
			super(
					mappingDocument,
					compositeIdAdapter,
					new EmbeddableSourceImpl(
							mappingDocument,
							compositeIdAdapter,
							compositeIdAdapter,
							compositeIdAdapter.getAttributes(),
							false,
							false,
							null,
							NaturalIdMutability.NOT_NATURAL_ID
					),
					NaturalIdMutability.NOT_NATURAL_ID
			);
			this.compositeIdAdapter = compositeIdAdapter;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public Boolean isInsertable() {
			return true;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public Boolean isUpdatable() {
			return false;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public boolean isBytecodeLazy() {
			return false;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public XmlElementMetadata getSourceType() {
			return XmlElementMetadata.COMPOSITE_ID;
		}

		@Override
		@Prove(complexity = Complexity.O_N, n = "", count = {})
		public String getXmlNodeName() {
			return compositeIdAdapter.getXmlNodeName();
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public AttributePath getAttributePath() {
			return getEmbeddableSource().getAttributePathBase();
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public AttributeRole getAttributeRole() {
			return getEmbeddableSource().getAttributeRoleBase();
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public boolean isIncludedInOptimisticLocking() {
			return false;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public ToolingHintContext getToolingHintContext() {
			return compositeIdAdapter.toolingHintContext;
		}
	}

	private static class EmbeddedAttributeMappingAdapterAggregatedCompositeId
			implements EmbeddedAttributeMapping, EmbeddableSourceContainer, EmbeddableMapping {
		private final RootEntitySourceImpl rootEntitySource;
		private final JaxbHbmCompositeIdType jaxbCompositeIdMapping;

		private final AttributeRole idAttributeRole;
		private final AttributePath idAttributePath;
		private final ToolingHintContext toolingHintContext;

		private EmbeddedAttributeMappingAdapterAggregatedCompositeId(
				RootEntitySourceImpl rootEntitySource) {
			this.rootEntitySource = rootEntitySource;
			this.jaxbCompositeIdMapping = rootEntitySource.jaxbEntityMapping().getCompositeId();

			this.idAttributeRole = rootEntitySource.getAttributeRoleBase().append( jaxbCompositeIdMapping.getName() );
			this.idAttributePath = rootEntitySource.getAttributePathBase().append( jaxbCompositeIdMapping.getName() );

			this.toolingHintContext = Helper.collectToolingHints(
					rootEntitySource.getToolingHintContext(),
					jaxbCompositeIdMapping
			);
		}

		@Override
		@Prove(complexity = Complexity.O_N, n = "", count = {})
		public String getName() {
			return jaxbCompositeIdMapping.getName();
		}

		@Override
		@Prove(complexity = Complexity.O_N, n = "", count = {})
		public String getAccess() {
			return jaxbCompositeIdMapping.getAccess();
		}

		@Override
		@Prove(complexity = Complexity.O_N, n = "", count = {})
		public String getClazz() {
			return jaxbCompositeIdMapping.getClazz();
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public List<JaxbHbmTuplizerType> getTuplizer() {
			return Collections.emptyList();
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public String getParent() {
			return null;
		}

		@Override
		@Prove(complexity = Complexity.O_N, n = "", count = {})
		public List<JaxbHbmToolingHintType> getToolingHints() {
			return jaxbCompositeIdMapping.getToolingHints();
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public AttributeRole getAttributeRoleBase() {
			return idAttributeRole;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public AttributePath getAttributePathBase() {
			return idAttributePath;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public ToolingHintContext getToolingHintContextBaselineForEmbeddable() {
			return toolingHintContext;
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public List getAttributes() {
			return jaxbCompositeIdMapping.getKeyPropertyOrKeyManyToOne();
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public String getXmlNodeName() {
			return jaxbCompositeIdMapping.getNode();
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public boolean isUnique() {
			return false;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public EmbeddableMapping getEmbeddableMapping() {
			return this;
		}
	}
}
