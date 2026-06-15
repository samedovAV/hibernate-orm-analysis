/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.internal.hbm;

import java.util.List;

import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmBasicAttributeType;
import org.hibernate.boot.model.source.spi.AttributePath;
import org.hibernate.boot.model.source.spi.AttributeRole;
import org.hibernate.boot.model.source.spi.AttributeSourceContainer;
import org.hibernate.boot.model.source.spi.NaturalIdMutability;
import org.hibernate.boot.model.source.spi.RelationalValueSource;
import org.hibernate.boot.model.source.spi.SingularAttributeNature;
import org.hibernate.boot.model.source.spi.SingularAttributeSourceBasic;
import org.hibernate.boot.model.source.spi.ToolingHintContext;
import org.hibernate.boot.spi.MetadataBuildingContext;
import org.hibernate.boot.jaxb.mapping.GenerationTiming;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Implementation for {@code <property/>} mappings
 *
 * @author Steve Ebersole
 */
class SingularAttributeSourceBasicImpl
		extends AbstractHbmSourceNode
		implements SingularAttributeSourceBasic {
	private final JaxbHbmBasicAttributeType propertyElement;
	private final HibernateTypeSourceImpl typeSource;
	private final NaturalIdMutability naturalIdMutability;

	private final List<RelationalValueSource> relationalValueSources;

	private final AttributeRole attributeRole;
	private final AttributePath attributePath;

	private final ToolingHintContext toolingHintContext;

	SingularAttributeSourceBasicImpl(
			MappingDocument sourceMappingDocument,
			AttributeSourceContainer container,
			final JaxbHbmBasicAttributeType propertyElement,
			final String logicalTableName,
			NaturalIdMutability naturalIdMutability) {
		super( sourceMappingDocument );
		this.propertyElement = propertyElement;
		this.typeSource = new HibernateTypeSourceImpl( propertyElement );
		this.naturalIdMutability = naturalIdMutability;

		this.relationalValueSources = RelationalValueSourceHelper.buildValueSources(
				sourceMappingDocument,
				logicalTableName,
				new BasicAttributeColumnsAndFormulasSource( propertyElement )
		);

		this.attributeRole = container.getAttributeRoleBase().append( getName() );
		this.attributePath = container.getAttributePathBase().append( getName() );

		this.toolingHintContext = Helper.collectToolingHints(
				container.getToolingHintContext(),
				propertyElement
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isSingular() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SingularAttributeNature getSingularAttributeNature() {
		return SingularAttributeNature.BASIC;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public XmlElementMetadata getSourceType() {
		return XmlElementMetadata.PROPERTY;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getName() {
		return propertyElement.getName();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getXmlNodeName() {
		return propertyElement.getNode();
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
		return propertyElement.getAccess();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public GenerationTiming getGenerationTiming() {
		return propertyElement.getGenerated();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Boolean isInsertable() {
		return propertyElement.isInsert() == null
				? Boolean.TRUE
				: propertyElement.isInsert();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Boolean isUpdatable() {
		return propertyElement.isUpdate() == null
				? Boolean.TRUE
				: propertyElement.isUpdate();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isBytecodeLazy() {
		return Helper.getValue( propertyElement.isLazy(), false );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public NaturalIdMutability getNaturalIdMutability() {
		return naturalIdMutability;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isIncludedInOptimisticLocking() {
		return Helper.getValue( propertyElement.isOptimisticLock(), true );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isVirtualAttribute() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<RelationalValueSource> getRelationalValueSources() {
		return relationalValueSources;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean areValuesIncludedInInsertByDefault() {
		return Helper.getValue( propertyElement.isInsert(), true );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean areValuesIncludedInUpdateByDefault() {
		return Helper.getValue( propertyElement.isUpdate(), true );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean areValuesNullableByDefault() {
		return ! Helper.getValue(
				propertyElement.isNotNull(),
				naturalIdMutability != NaturalIdMutability.NOT_NATURAL_ID
		);
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
