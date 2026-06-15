/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.internal.hbm;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.hibernate.boot.MappingException;
import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmAnyAssociationType;
import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmAnyValueMappingType;
import org.hibernate.boot.model.source.spi.AnyDiscriminatorSource;
import org.hibernate.boot.model.source.spi.AnyKeySource;
import org.hibernate.boot.model.source.spi.AttributePath;
import org.hibernate.boot.model.source.spi.AttributeRole;
import org.hibernate.boot.model.source.spi.AttributeSourceContainer;
import org.hibernate.boot.model.source.spi.HibernateTypeSource;
import org.hibernate.boot.model.source.spi.NaturalIdMutability;
import org.hibernate.boot.model.source.spi.RelationalValueSource;
import org.hibernate.boot.model.source.spi.SingularAttributeNature;
import org.hibernate.boot.model.source.spi.SingularAttributeSourceAny;
import org.hibernate.boot.model.source.spi.ToolingHintContext;
import org.hibernate.boot.spi.MetadataBuildingContext;
import org.hibernate.boot.jaxb.mapping.GenerationTiming;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class SingularAttributeSourceAnyImpl
		extends AbstractHbmSourceNode
		implements SingularAttributeSourceAny {

	private final JaxbHbmAnyAssociationType jaxbAnyMapping;
	private final NaturalIdMutability naturalIdMutability;

	private final AttributePath attributePath;
	private final AttributeRole attributeRole;

	// we don't really know the type of the attribute overall
	private final HibernateTypeSource attributeTypeSource = new HibernateTypeSourceImpl( (String) null );

	private final AnyDiscriminatorSource discriminatorSource;
	private final AnyKeySource keySource;

	private final ToolingHintContext toolingHintContext;

	public SingularAttributeSourceAnyImpl(
			final MappingDocument sourceMappingDocument,
			AttributeSourceContainer container,
			final JaxbHbmAnyAssociationType jaxbAnyMapping,
			String logicalTableName,
			NaturalIdMutability naturalIdMutability) {
		super( sourceMappingDocument );
		this.jaxbAnyMapping = jaxbAnyMapping;
		this.naturalIdMutability = naturalIdMutability;

		this.attributePath = container.getAttributePathBase().append( jaxbAnyMapping.getName() );
		this.attributeRole = container.getAttributeRoleBase().append( jaxbAnyMapping.getName() );


		final List<RelationalValueSource> relationalValueSources = RelationalValueSourceHelper.buildValueSources(
				sourceMappingDocument,
				logicalTableName,
				new RelationalValueSourceHelper.AbstractColumnsAndFormulasSource() {
					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public XmlElementMetadata getSourceType() {
						return XmlElementMetadata.ANY;
					}

					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public String getSourceName() {
						return jaxbAnyMapping.getName();
					}

					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public List getColumnOrFormulaElements() {
						return jaxbAnyMapping.getColumn();
					}
				}
		);

		// the list of relational values should contain 2 or more values:
		//		* the first represents the discriminator
		//		* the rest represent the fk

		if ( relationalValueSources.size() < 2 ) {
			throw new MappingException(
					String.format(
							Locale.ENGLISH,
							"<any name=\"%s\" /> mapping needs to specify 2 or more columns",
							jaxbAnyMapping.getName()
					),
					origin()
			);
		}

		this.discriminatorSource = new AnyDiscriminatorSource() {
			private final HibernateTypeSource typeSource = new HibernateTypeSourceImpl( jaxbAnyMapping.getMetaType() );
			private final RelationalValueSource relationalValueSource = relationalValueSources.get( 0 );
			private final Map<String,String> valueMappings = new HashMap<>();
			{
				for ( JaxbHbmAnyValueMappingType valueMapping : jaxbAnyMapping.getMetaValue() ) {
					valueMappings.put(
							valueMapping.getValue(),
							sourceMappingDocument.qualifyClassName( valueMapping.getClazz() )
					);
				}
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public HibernateTypeSource getTypeSource() {
				return typeSource;
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public RelationalValueSource getRelationalValueSource() {
				return relationalValueSource;
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public Map<String, String> getValueMappings() {
				return valueMappings;
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public AttributePath getAttributePath() {
				return attributePath;
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public MetadataBuildingContext getBuildingContext() {
				return sourceMappingDocument;
			}
		};

		this.keySource = new AnyKeySource() {
			private final HibernateTypeSource fkTypeSource = new HibernateTypeSourceImpl( jaxbAnyMapping.getIdType() );
			private final List<RelationalValueSource> fkRelationalValueSources = relationalValueSources.subList( 1, relationalValueSources.size() );

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public HibernateTypeSource getTypeSource() {
				return fkTypeSource;
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public List<RelationalValueSource> getRelationalValueSources() {
				return fkRelationalValueSources;
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public AttributePath getAttributePath() {
				return attributePath;
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public MetadataBuildingContext getBuildingContext() {
				return sourceMappingDocument;
			}
		};

		toolingHintContext = Helper.collectToolingHints(
				sourceMappingDocument.getToolingHintContext(),
				jaxbAnyMapping
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SingularAttributeNature getSingularAttributeNature() {
		return SingularAttributeNature.ANY;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public XmlElementMetadata getSourceType() {
		return XmlElementMetadata.ANY;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isSingular() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getName() {
		return jaxbAnyMapping.getName();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getXmlNodeName() {
		return jaxbAnyMapping.getNode();
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
	public boolean isVirtualAttribute() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public GenerationTiming getGenerationTiming() {
		return GenerationTiming.NEVER;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Boolean isInsertable() {
		return jaxbAnyMapping.isInsert();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Boolean isUpdatable() {
		return jaxbAnyMapping.isUpdate();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isBytecodeLazy() {
		return jaxbAnyMapping.isLazy();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public NaturalIdMutability getNaturalIdMutability() {
		return naturalIdMutability;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public HibernateTypeSource getTypeInformation() {
		return attributeTypeSource;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getPropertyAccessorName() {
		return jaxbAnyMapping.getAccess();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isIncludedInOptimisticLocking() {
		return jaxbAnyMapping.isOptimisticLock();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ToolingHintContext getToolingHintContext() {
		return toolingHintContext;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public AnyDiscriminatorSource getDiscriminatorSource() {
		return discriminatorSource;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public AnyKeySource getKeySource() {
		return keySource;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getCascadeStyleName() {
		return jaxbAnyMapping.getCascade();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isLazy() {
		return isBytecodeLazy();
	}
}
