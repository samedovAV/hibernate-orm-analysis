/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.internal.hbm;

import java.util.List;

import org.hibernate.boot.MappingException;
import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmCompositeKeyManyToOneType;
import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmLazyEnum;
import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmLazyWithNoProxyEnum;
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
 * Descriptor for {@code <key-many-to-one/>} mapping
 *
 * @author Steve Ebersole
 * @author Gail Badner
 */
public class CompositeIdentifierSingularAttributeSourceManyToOneImpl
		extends AbstractToOneAttributeSourceImpl
		implements SingularAttributeSourceManyToOne, RelationalValueSourceContainer {

	private final JaxbHbmCompositeKeyManyToOneType keyManyToOneElement;

	private final String referencedEntityName;
	private final HibernateTypeSourceImpl typeSource;
	private final List<RelationalValueSource> valueSources;

	private final AttributePath attributePath;
	private final AttributeRole attributeRole;
	private final FetchCharacteristicsSingularAssociationImpl fetchCharacteristics;
	private final ToolingHintContext toolingHintContext;

	public CompositeIdentifierSingularAttributeSourceManyToOneImpl(
			MappingDocument mappingDocument,
			AttributeSourceContainer container,
			final JaxbHbmCompositeKeyManyToOneType keyManyToOneElement) {
		super( mappingDocument, NaturalIdMutability.NOT_NATURAL_ID );
		this.keyManyToOneElement = keyManyToOneElement;

		this.referencedEntityName = keyManyToOneElement.getClazz() != null
				? mappingDocument.qualifyClassName( keyManyToOneElement.getClazz() )
				: keyManyToOneElement.getEntityName();

		final JavaTypeDescriptor referencedTypeDescriptor = new JavaTypeDescriptor() {
			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public String getName() {
				return referencedEntityName;
			}
		};
		this.typeSource = new HibernateTypeSourceImpl( referencedTypeDescriptor );

		this.valueSources = RelationalValueSourceHelper.buildValueSources(
				mappingDocument,
				null,
				new RelationalValueSourceHelper.AbstractColumnsAndFormulasSource() {
					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public XmlElementMetadata getSourceType() {
						return XmlElementMetadata.KEY_MANY_TO_ONE;
					}

					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public String getSourceName() {
						return keyManyToOneElement.getName();
					}

					@Override
					@Prove(complexity = Complexity.O_N, n = "", count = {})
					public String getColumnAttribute() {
						return keyManyToOneElement.getColumnAttribute();
					}

					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public List getColumnOrFormulaElements() {
						return keyManyToOneElement.getColumn();
					}
				}
		);

		this.attributePath = container.getAttributePathBase().append( getName() );
		this.attributeRole = container.getAttributeRoleBase().append( getName() );

		this.fetchCharacteristics = FetchCharacteristicsSingularAssociationImpl.interpretManyToOne(
				mappingDocument.getEffectiveDefaults(),
				null,
				null,
				interpretLazy( mappingDocument, keyManyToOneElement )
		);

		this.toolingHintContext = Helper.collectToolingHints(
				container.getToolingHintContext(),
				keyManyToOneElement
		);
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private static JaxbHbmLazyWithNoProxyEnum interpretLazy(
			MappingDocument mappingDocument,
			JaxbHbmCompositeKeyManyToOneType keyManyToOne) {
		if ( keyManyToOne.getLazy() == null ) {
			return null;
		}
		else if ( keyManyToOne.getLazy() == JaxbHbmLazyEnum.FALSE ) {
			return JaxbHbmLazyWithNoProxyEnum.FALSE;
		}
		else if ( keyManyToOne.getLazy() == JaxbHbmLazyEnum.PROXY ) {
			return JaxbHbmLazyWithNoProxyEnum.PROXY;
		}

		throw new MappingException(
				"Unrecognized lazy value [" + keyManyToOne.getLazy().name() +
						"] specified for key-many-to-one [" + keyManyToOne.getName() + "]",
				mappingDocument.getOrigin()
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SingularAttributeNature getSingularAttributeNature() {
		return SingularAttributeNature.MANY_TO_ONE;
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
		return XmlElementMetadata.KEY_MANY_TO_ONE;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getName() {
		return keyManyToOneElement.getName();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getXmlNodeName() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public AttributePath getAttributePath() {
		return attributePath;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public HibernateTypeSourceImpl getTypeInformation() {
		return typeSource;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getPropertyAccessorName() {
		return keyManyToOneElement.getAccess();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public AttributeRole getAttributeRole() {
		return attributeRole;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<RelationalValueSource> getRelationalValueSources() {
		return valueSources;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean areValuesIncludedInInsertByDefault() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean areValuesIncludedInUpdateByDefault() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isIncludedInOptimisticLocking() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public FetchCharacteristicsSingularAssociationImpl getFetchCharacteristics() {
		return fetchCharacteristics;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isVirtualAttribute() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean areValuesNullableByDefault() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getReferencedEntityAttributeName() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getReferencedEntityName() {
		return referencedEntityName;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Boolean isEmbedXml() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isUnique() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ForeignKeyDirection getForeignKeyDirection() {
		return ForeignKeyDirection.TO_PARENT;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getCascadeStyleName() {
		return "";
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getExplicitForeignKeyName() {
		return keyManyToOneElement.getForeignKey();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isCascadeDeleteEnabled() {
		return "cascade".equals( keyManyToOneElement.getOnDelete().value() );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected String getClassName() {
		return sourceMappingDocument().qualifyClassName( keyManyToOneElement.getClazz() );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ToolingHintContext getToolingHintContext() {
		return toolingHintContext;
	}
}
