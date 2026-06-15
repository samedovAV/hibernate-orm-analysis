/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.internal.hbm;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.hibernate.boot.MappingException;
import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmCompositeIdType;
import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmEntityDiscriminatorType;
import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmGeneratorSpecificationType;
import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmMultiTenancyType;
import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmPolymorphismEnum;
import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmRootEntityType;
import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmSimpleIdType;
import org.hibernate.boot.model.source.spi.Caching;
import org.hibernate.boot.model.IdentifierGeneratorDefinition;
import org.hibernate.boot.model.naming.EntityNaming;
import org.hibernate.boot.model.source.spi.DiscriminatorSource;
import org.hibernate.boot.model.source.spi.EntityHierarchySource;
import org.hibernate.boot.model.source.spi.EntityNamingSource;
import org.hibernate.boot.model.source.spi.IdentifierSource;
import org.hibernate.boot.model.source.spi.InheritanceType;
import org.hibernate.boot.model.source.spi.MultiTenancySource;
import org.hibernate.boot.model.source.spi.RelationalValueSource;
import org.hibernate.boot.model.source.spi.SizeSource;
import org.hibernate.boot.model.source.spi.VersionAttributeSource;
import org.hibernate.boot.spi.MetadataBuildingContext;
import org.hibernate.engine.OptimisticLockStyle;
import org.hibernate.internal.util.StringHelper;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Models an entity hierarchy as defined by {@code hbm.xml} documents
 *
 * @author Steve Ebersole
 */
public class EntityHierarchySourceImpl implements EntityHierarchySource {
	private final RootEntitySourceImpl rootEntitySource;
	private final MappingDocument rootEntityMappingDocument;

	private final IdentifierSource identifierSource;
	private final VersionAttributeSource versionAttributeSource;
	private final DiscriminatorSource discriminatorSource;
	private final MultiTenancySource multiTenancySource;

	private final Caching caching;
	private final Caching naturalIdCaching;

	private InheritanceType hierarchyInheritanceType = InheritanceType.NO_INHERITANCE;

	private final Set<String> collectedEntityNames = new HashSet<>();

	public EntityHierarchySourceImpl(
			RootEntitySourceImpl rootEntitySource,
			MappingDocument rootEntityMappingDocument) {
		this.rootEntitySource = rootEntitySource;
		this.rootEntityMappingDocument = rootEntityMappingDocument;
		this.rootEntitySource.injectHierarchy( this );

		this.identifierSource = interpretIdentifierSource( rootEntitySource );
		this.versionAttributeSource = interpretVersionSource( rootEntitySource );
		this.discriminatorSource = interpretDiscriminatorSource( rootEntitySource );
		this.multiTenancySource = interpretMultiTenancySource( rootEntitySource );

		this.caching = Helper.createCaching( entityElement().getCache() );
		this.naturalIdCaching = Helper.createNaturalIdCaching(
				rootEntitySource.jaxbEntityMapping().getNaturalIdCache()
		);

		collectedEntityNames.add( rootEntitySource.getEntityNamingSource().getEntityName() );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public MappingDocument getRootEntityMappingDocument() {
		return rootEntityMappingDocument;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private static IdentifierSource interpretIdentifierSource(RootEntitySourceImpl rootEntitySource) {
		final JaxbHbmSimpleIdType simpleId = rootEntitySource.jaxbEntityMapping().getId();
		final JaxbHbmCompositeIdType compositeId = rootEntitySource.jaxbEntityMapping().getCompositeId();

		if ( simpleId == null && compositeId == null ) {
			throw new MappingException(
					String.format(
							Locale.ROOT,
							"Entity [%s] did not define an identifier",
							rootEntitySource.getEntityNamingSource().getEntityName()
					),
					rootEntitySource.origin()
			);
		}

		if ( simpleId != null ) {
			return new IdentifierSourceSimpleImpl( rootEntitySource );
		}
		else {
			// if we get here, we should have a composite identifier.  Just need
			// to determine if it is aggregated, or non-aggregated...

			if ( compositeId.isMapped() ) {
				if ( StringHelper.isEmpty( compositeId.getClazz() ) ) {
					throw new MappingException(
							"mapped composite identifier must name component class to use.",
							rootEntitySource.origin()
					);
				}
			}

			if ( StringHelper.isEmpty( compositeId.getName() ) ) {
				if ( compositeId.isMapped() && StringHelper.isEmpty( compositeId.getClazz() ) ) {
					throw new MappingException(
							"mapped composite identifier must name component class to use.",
							rootEntitySource.origin()
					);
				}
				return new IdentifierSourceNonAggregatedCompositeImpl( rootEntitySource );
			}
			else {
				if ( compositeId.isMapped() ) {
					throw new MappingException(
							"cannot combine mapped=\"true\" with specified name",
							rootEntitySource.origin()
					);
				}
				return new IdentifierSourceAggregatedCompositeImpl( rootEntitySource );
			}
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private static VersionAttributeSource interpretVersionSource(RootEntitySourceImpl rootEntitySource) {
		final JaxbHbmRootEntityType entityElement = rootEntitySource.jaxbEntityMapping();
		if ( entityElement.getVersion() != null ) {
			return new VersionAttributeSourceImpl(
					rootEntitySource.sourceMappingDocument(),
					rootEntitySource,
					entityElement.getVersion()
			);
		}
		else if ( entityElement.getTimestamp() != null ) {
			return new TimestampAttributeSourceImpl(
					rootEntitySource.sourceMappingDocument(),
					rootEntitySource,
					entityElement.getTimestamp()
			);
		}
		return null;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private static DiscriminatorSource interpretDiscriminatorSource(final RootEntitySourceImpl rootEntitySource) {
		final JaxbHbmEntityDiscriminatorType jaxbDiscriminatorMapping =
				rootEntitySource.jaxbEntityMapping().getDiscriminator();

		if ( jaxbDiscriminatorMapping == null ) {
			return null;
		}

		final RelationalValueSource relationalValueSource = RelationalValueSourceHelper.buildValueSource(
				rootEntitySource.sourceMappingDocument(),
				null,
				new RelationalValueSourceHelper.AbstractColumnsAndFormulasSource() {
					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public XmlElementMetadata getSourceType() {
						return XmlElementMetadata.DISCRIMINATOR;
					}

					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public String getSourceName() {
						return null;
					}

					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public SizeSource getSizeSource() {
						return Helper.interpretSizeSource(
								jaxbDiscriminatorMapping.getLength(),
								(Integer) null,
								null
						);
					}

					@Override
					@Prove(complexity = Complexity.O_N, n = "", count = {})
					public String getFormulaAttribute() {
						return jaxbDiscriminatorMapping.getFormulaAttribute();
					}

					@Override
					@Prove(complexity = Complexity.O_N, n = "", count = {})
					public String getColumnAttribute() {
						return jaxbDiscriminatorMapping.getColumnAttribute();
					}

					private List columnOrFormulas;
					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public List getColumnOrFormulaElements() {
						if ( columnOrFormulas == null ) {
							if ( jaxbDiscriminatorMapping.getColumn() != null ) {
								if ( jaxbDiscriminatorMapping.getFormula() != null ) {
									throw new MappingException(
											String.format(
													Locale.ENGLISH,
													"discriminator mapping [%s] named both <column/> and <formula/>, but only one or other allowed",
													rootEntitySource.getEntityNamingSource().getEntityName()
											),
											rootEntitySource.sourceMappingDocument().getOrigin()
									);
								}
								else {
									columnOrFormulas = Collections.singletonList( jaxbDiscriminatorMapping.getColumn() );
								}
							}
							else {
								if ( jaxbDiscriminatorMapping.getFormula() != null ) {
									columnOrFormulas = Collections.singletonList( jaxbDiscriminatorMapping.getFormula() );
								}
								else {
									columnOrFormulas = Collections.emptyList();
								}
							}
						}
						return columnOrFormulas;
					}

					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public Boolean isNullable() {
						return !jaxbDiscriminatorMapping.isNotNull();
					}
				}
		);

		return new DiscriminatorSource() {
			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public EntityNaming getEntityNaming() {
				return rootEntitySource.getEntityNamingSource();
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public MetadataBuildingContext getBuildingContext() {
				return rootEntitySource.metadataBuildingContext();
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public RelationalValueSource getDiscriminatorRelationalValueSource() {
				return relationalValueSource;
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public String getExplicitHibernateTypeName() {
				return jaxbDiscriminatorMapping.getType();
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public boolean isForced() {
				return jaxbDiscriminatorMapping.isForce();
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public boolean isInserted() {
				return jaxbDiscriminatorMapping.isInsert();
			}
		};
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private static MultiTenancySource interpretMultiTenancySource(final RootEntitySourceImpl rootEntitySource) {
		final JaxbHbmMultiTenancyType jaxbMultiTenancy = rootEntitySource.jaxbEntityMapping().getMultiTenancy();
		if ( jaxbMultiTenancy == null ) {
			return null;
		}

		final RelationalValueSource relationalValueSource = RelationalValueSourceHelper.buildValueSource(
				rootEntitySource.sourceMappingDocument(),
				null,
				new RelationalValueSourceHelper.AbstractColumnsAndFormulasSource() {
					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public XmlElementMetadata getSourceType() {
						return XmlElementMetadata.MULTI_TENANCY;
					}

					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public String getSourceName() {
						return null;
					}

					@Override
					@Prove(complexity = Complexity.O_N, n = "", count = {})
					public String getFormulaAttribute() {
						return jaxbMultiTenancy.getFormulaAttribute();
					}

					@Override
					@Prove(complexity = Complexity.O_N, n = "", count = {})
					public String getColumnAttribute() {
						return jaxbMultiTenancy.getColumnAttribute();
					}
					private List columnOrFormulas;
					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public List getColumnOrFormulaElements() {
						if ( columnOrFormulas == null ) {
							if ( jaxbMultiTenancy.getColumn() != null ) {
								if ( jaxbMultiTenancy.getFormula() != null ) {
									throw new MappingException(
											String.format(
													Locale.ENGLISH,
													"discriminator mapping [%s] named both <column/> and <formula/>, but only one or other allowed",
													rootEntitySource.getEntityNamingSource().getEntityName()
											),
											rootEntitySource.sourceMappingDocument().getOrigin()
									);
								}
								else {
									columnOrFormulas = Collections.singletonList( jaxbMultiTenancy.getColumn() );
								}
							}
							else {
								if ( jaxbMultiTenancy.getFormula() != null ) {
									columnOrFormulas = Collections.singletonList( jaxbMultiTenancy.getColumn() );
								}
								else {
									columnOrFormulas = Collections.emptyList();
								}
							}
						}
						return columnOrFormulas;
					}

					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public Boolean isNullable() {
						return false;
					}
				}
		);

		return new MultiTenancySource() {
			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public RelationalValueSource getRelationalValueSource() {
				return relationalValueSource;
			}

			@Override
			@Prove(complexity = Complexity.O_N, n = "", count = {})
			public boolean isShared() {
				return jaxbMultiTenancy.isShared();
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public boolean bindAsParameter() {
				return jaxbMultiTenancy.isBindAsParam();
			}
		};
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public InheritanceType getHierarchyInheritanceType() {
		return hierarchyInheritanceType;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public RootEntitySourceImpl getRoot() {
		return rootEntitySource;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void processSubclass(SubclassEntitySourceImpl subclassEntitySource) {
		final InheritanceType inheritanceType = Helper.interpretInheritanceType( subclassEntitySource.jaxbEntityMapping() );
		if ( hierarchyInheritanceType == InheritanceType.NO_INHERITANCE ) {
			hierarchyInheritanceType = inheritanceType;
		}
		else if ( hierarchyInheritanceType != inheritanceType ) {
			throw new MappingException( "Mixed inheritance strategies not supported", subclassEntitySource.getOrigin() );
		}

		collectedEntityNames.add( subclassEntitySource.getEntityNamingSource().getEntityName() );
	}


	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected JaxbHbmRootEntityType entityElement() {
		return rootEntitySource.jaxbEntityMapping();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public IdentifierSource getIdentifierSource() {
		return identifierSource;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public VersionAttributeSource getVersionAttributeSource() {
		return versionAttributeSource;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isMutable() {
		return entityElement().isMutable();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isExplicitPolymorphism() {
		return JaxbHbmPolymorphismEnum.EXPLICIT == entityElement().getPolymorphism();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getWhere() {
		return entityElement().getWhere();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getRowId() {
		return entityElement().getRowid();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public OptimisticLockStyle getOptimisticLockStyle() {
		return entityElement().getOptimisticLock();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Caching getCaching() {
		return caching;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Caching getNaturalIdCaching() {
		return naturalIdCaching;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public DiscriminatorSource getDiscriminatorSource() {
		return discriminatorSource;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public MultiTenancySource getMultiTenancySource() {
		return multiTenancySource;
	}

	/**
	 * Package-protected to allow IdentifierSource implementations to access it.
	 *
	 * @param mappingDocument The source mapping document
	 * @param entityNaming The entity naming
	 * @param jaxbGeneratorMapping The identifier generator mapping
	 *
	 * @return The collected information.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	static IdentifierGeneratorDefinition interpretGeneratorDefinition(
			MappingDocument mappingDocument,
			EntityNamingSource entityNaming,
			JaxbHbmGeneratorSpecificationType jaxbGeneratorMapping) {
		if ( jaxbGeneratorMapping == null ) {
			return null;
		}

		final String generatorName = jaxbGeneratorMapping.getClazz();
		IdentifierGeneratorDefinition identifierGeneratorDefinition = mappingDocument.getMetadataCollector()
				.getIdentifierGenerator( generatorName );
		if ( identifierGeneratorDefinition == null ) {
			identifierGeneratorDefinition = new IdentifierGeneratorDefinition(
					entityNaming.getEntityName() + '.' + generatorName,
					generatorName,
					Helper.extractParameters( jaxbGeneratorMapping.getConfigParameters() )
			);
		}
		return identifierGeneratorDefinition;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Set<String> getContainedEntityNames() {
		return collectedEntityNames;
	}
}
