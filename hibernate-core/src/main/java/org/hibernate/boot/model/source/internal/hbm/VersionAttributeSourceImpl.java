/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.internal.hbm;

import java.util.List;

import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmTimestampSourceEnum;
import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmVersionAttributeType;
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
 * Implementation for {@code <version/>} mappings
 *
 * @author Steve Ebersole
 */
class VersionAttributeSourceImpl
		extends AbstractHbmSourceNode
		implements VersionAttributeSource {
	private final JaxbHbmVersionAttributeType versionElement;
	private final HibernateTypeSourceImpl typeSource;

	private final List<RelationalValueSource> relationalValueSources;

	private final AttributePath attributePath;
	private final AttributeRole attributeRole;
	private final ToolingHintContext toolingHints;

	VersionAttributeSourceImpl(
			MappingDocument mappingDocument,
			RootEntitySourceImpl rootEntitySource,
			JaxbHbmVersionAttributeType versionElement) {
		super( mappingDocument );
		this.versionElement = versionElement;

		this.relationalValueSources = RelationalValueSourceHelper.buildValueSources(
				mappingDocument,
				null,
				new RelationalValueSourceHelper.AbstractColumnsAndFormulasSource() {
					@Override
					@Prove(complexity = Complexity.O_N, n = "", count = {})
					public XmlElementMetadata getSourceType() {
						return VersionAttributeSourceImpl.this.getSourceType();
					}

					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public String getSourceName() {
						return VersionAttributeSourceImpl.this.versionElement.getName();
					}

					@Override
					@Prove(complexity = Complexity.O_N, n = "", count = {})
					public String getColumnAttribute() {
						return VersionAttributeSourceImpl.this.versionElement.getColumnAttribute();
					}

					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public List getColumnOrFormulaElements() {
						return VersionAttributeSourceImpl.this.versionElement.getColumn();
					}

					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public Boolean isNullable() {
						return false;
					}
				}
		);

		this.typeSource = new HibernateTypeSourceImpl(
				versionElement.getType() == null
						? "integer"
						: versionElement.getType()
		);

		this.attributePath = rootEntitySource.getAttributePathBase().append( getName() );
		this.attributeRole = rootEntitySource.getAttributeRoleBase().append( getName() );

		this.toolingHints = Helper.collectToolingHints(
				rootEntitySource.getToolingHintContext(),
				versionElement
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public XmlElementMetadata getSourceType() {
		return XmlElementMetadata.VERSION;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getName() {
		return versionElement.getName();
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
	public String getPropertyAccessorName() {
		return versionElement.getAccess();
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
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getUnsavedValue() {
		return versionElement.getUnsavedValue().value();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public GenerationTiming getGenerationTiming() {
		return versionElement.getGenerated();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getSource() {
		return JaxbHbmTimestampSourceEnum.VM.value();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Boolean isInsertable() {
		return versionElement.isInsert() == null ? Boolean.TRUE : versionElement.isInsert();
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
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getXmlNodeName() {
		return versionElement.getNode();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public MetadataBuildingContext getBuildingContext() {
		return metadataBuildingContext();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ToolingHintContext getToolingHintContext() {
		return toolingHints;
	}
}
