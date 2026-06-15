/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.internal.hbm;

import java.util.Collections;
import java.util.List;

import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmTimestampAttributeType;
import org.hibernate.boot.model.source.spi.AttributePath;
import org.hibernate.boot.model.source.spi.AttributeRole;
import org.hibernate.boot.model.source.spi.NaturalIdMutability;
import org.hibernate.boot.model.source.spi.RelationalValueSource;
import org.hibernate.boot.model.source.spi.SingularAttributeNature;
import org.hibernate.boot.model.source.spi.ToolingHintContext;
import org.hibernate.boot.model.source.spi.VersionAttributeSource;
import org.hibernate.boot.spi.MetadataBuildingContext;
import org.hibernate.boot.jaxb.mapping.GenerationTiming;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Implementation for {@code <timestamp/>} mappings
 *
 * @author Steve Ebersole
 */
class TimestampAttributeSourceImpl
		extends AbstractHbmSourceNode
		implements VersionAttributeSource {
	private final JaxbHbmTimestampAttributeType timestampElement;
	private final HibernateTypeSourceImpl typeSource;

	private final List<RelationalValueSource> relationalValueSources;

	private final AttributePath attributePath;
	private final AttributeRole attributeRole;
	private final ToolingHintContext toolingHintContext;

	TimestampAttributeSourceImpl(
			MappingDocument mappingDocument,
			RootEntitySourceImpl rootEntitySource,
			JaxbHbmTimestampAttributeType timestampElement) {
		super( mappingDocument );
		this.timestampElement = timestampElement;
		this.typeSource = new HibernateTypeSourceImpl("timestamp");

		final RelationalValueSource columnSource = RelationalValueSourceHelper.buildColumnSource(
				mappingDocument,
				null,
				new RelationalValueSourceHelper.AbstractColumnsAndFormulasSource() {
					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public XmlElementMetadata getSourceType() {
						return XmlElementMetadata.TIMESTAMP;
					}

					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public String getSourceName() {
						return TimestampAttributeSourceImpl.this.getName();
					}

					@Override
					@Prove(complexity = Complexity.O_N, n = "", count = {})
					public String getColumnAttribute() {
						return TimestampAttributeSourceImpl.this.timestampElement.getColumnAttribute();
					}

					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public Boolean isNullable() {
						return false;
					}
				}
		);
		this.relationalValueSources = Collections.singletonList( columnSource );

		this.attributePath = rootEntitySource.getAttributePathBase().append( getName() );
		this.attributeRole = rootEntitySource.getAttributeRoleBase().append( getName() );

		this.toolingHintContext = Helper.collectToolingHints(
				rootEntitySource.getToolingHintContext(),
				timestampElement
		);
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getName() {
		return timestampElement.getName();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public XmlElementMetadata getSourceType() {
		return XmlElementMetadata.TIMESTAMP;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getXmlNodeName() {
		return timestampElement.getNode();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public AttributePath getAttributePath() {
		return attributePath;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isCollectionElement() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public AttributeRole getAttributeRole() {
		return attributeRole;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public HibernateTypeSourceImpl getTypeInformation() {
		return typeSource;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<RelationalValueSource> getRelationalValueSources() {
		return relationalValueSources;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean areValuesIncludedInInsertByDefault() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean areValuesIncludedInUpdateByDefault() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean areValuesNullableByDefault() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getPropertyAccessorName() {
		return timestampElement.getAccess();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public GenerationTiming getGenerationTiming() {
		return timestampElement.getGenerated();
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getSource() {
		return timestampElement.getSource().value();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Boolean isInsertable() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Boolean isUpdatable() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isBytecodeLazy() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public NaturalIdMutability getNaturalIdMutability() {
		return NaturalIdMutability.NOT_NATURAL_ID;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isIncludedInOptimisticLocking() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SingularAttributeNature getSingularAttributeNature() {
		return SingularAttributeNature.BASIC;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isVirtualAttribute() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isSingular() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getUnsavedValue() {
		return timestampElement.getUnsavedValue().value();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ToolingHintContext getToolingHintContext() {
		return toolingHintContext;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public MetadataBuildingContext getBuildingContext() {
		return sourceMappingDocument();
	}
}
