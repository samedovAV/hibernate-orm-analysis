/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.internal.hbm;

import java.util.List;

import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmManyToOneType;
import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmOnDeleteEnum;
import org.hibernate.boot.model.JavaTypeDescriptor;
import org.hibernate.boot.model.source.spi.AttributePath;
import org.hibernate.boot.model.source.spi.AttributeRole;
import org.hibernate.boot.model.source.spi.AttributeSourceContainer;
import org.hibernate.boot.model.source.spi.NaturalIdMutability;
import org.hibernate.boot.model.source.spi.RelationalValueSource;
import org.hibernate.boot.model.source.spi.RelationalValueSourceContainer;
import org.hibernate.boot.model.source.spi.SingularAttributeNature;
import org.hibernate.boot.model.source.spi.SingularAttributeSourceManyToOne;
import org.hibernate.boot.model.source.spi.ToolingHintContext;
import org.hibernate.type.ForeignKeyDirection;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Implementation for {@code <many-to-one/>} mappings
 *
 * @author Steve Ebersole
 */
class SingularAttributeSourceManyToOneImpl
		extends AbstractToOneAttributeSourceImpl
		implements SingularAttributeSourceManyToOne, RelationalValueSourceContainer {

	private final JaxbHbmManyToOneType manyToOneElement;
	private final HibernateTypeSourceImpl typeSource;

	private final String referencedTypeName;

	private final List<RelationalValueSource> relationalValueSources;

	private final AttributeRole attributeRole;
	private final AttributePath attributePath;

	private final ToolingHintContext toolingHintContext;

	private final FetchCharacteristicsSingularAssociationImpl fetchCharacteristics;

	SingularAttributeSourceManyToOneImpl(
			MappingDocument mappingDocument,
			AttributeSourceContainer container,
			final JaxbHbmManyToOneType manyToOneElement,
			final String logicalTableName,
			NaturalIdMutability naturalIdMutability) {
		super( mappingDocument, naturalIdMutability );
		this.manyToOneElement = manyToOneElement;

		this.referencedTypeName = manyToOneElement.getClazz() != null
				? mappingDocument.qualifyClassName( manyToOneElement.getClazz() )
				: manyToOneElement.getEntityName();

		final JavaTypeDescriptor referencedTypeDescriptor = new JavaTypeDescriptor() {
			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public String getName() {
				return referencedTypeName;
			}
		};
		this.typeSource = new HibernateTypeSourceImpl( referencedTypeDescriptor );

		this.relationalValueSources = RelationalValueSourceHelper.buildValueSources(
				mappingDocument,
				logicalTableName,
				new ManyToOneAttributeColumnsAndFormulasSource( manyToOneElement )
		);

		this.attributeRole = container.getAttributeRoleBase().append( manyToOneElement.getName() );
		this.attributePath = container.getAttributePathBase().append( manyToOneElement.getName() );

		this.fetchCharacteristics = FetchCharacteristicsSingularAssociationImpl.interpretManyToOne(
				mappingDocument.getEffectiveDefaults(),
				manyToOneElement.getFetch(),
				manyToOneElement.getOuterJoin(),
				manyToOneElement.getLazy()
		);

		this.toolingHintContext = Helper.collectToolingHints(
				container.getToolingHintContext(),
				manyToOneElement
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public XmlElementMetadata getSourceType() {
		return XmlElementMetadata.MANY_TO_ONE;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getName() {
			return manyToOneElement.getName();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getXmlNodeName() {
		return manyToOneElement.getNode();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public AttributePath getAttributePath() {
		return attributePath;
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
		return manyToOneElement.getAccess();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public FetchCharacteristicsSingularAssociationImpl getFetchCharacteristics() {
		return fetchCharacteristics;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isIgnoreNotFound() {
		return manyToOneElement.getNotFound() != null && "ignore".equalsIgnoreCase( manyToOneElement.getNotFound().value() );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isIncludedInOptimisticLocking() {
		return manyToOneElement.isOptimisticLock();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getCascadeStyleName() {
		return manyToOneElement.getCascade() == null
				? ""
				: manyToOneElement.getCascade();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SingularAttributeNature getSingularAttributeNature() {
		return SingularAttributeNature.MANY_TO_ONE;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Boolean isInsertable() {
		return manyToOneElement.isInsert();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Boolean isUpdatable() {
		return manyToOneElement.isUpdate();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isBytecodeLazy() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getReferencedEntityAttributeName() {
		return manyToOneElement.getPropertyRef();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getReferencedEntityName() {
		return referencedTypeName;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Boolean isEmbedXml() {
		return manyToOneElement.isEmbedXml();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isUnique() {
		return manyToOneElement.isUnique();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getExplicitForeignKeyName() {
		return manyToOneElement.getForeignKey();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isCascadeDeleteEnabled() {
		return JaxbHbmOnDeleteEnum.CASCADE.equals( manyToOneElement.getOnDelete() );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ForeignKeyDirection getForeignKeyDirection() {
		return ForeignKeyDirection.TO_PARENT;
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
		return getNaturalIdMutability() == NaturalIdMutability.NOT_NATURAL_ID;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ToolingHintContext getToolingHintContext() {
		return toolingHintContext;
	}
}
