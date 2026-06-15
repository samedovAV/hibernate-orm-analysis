/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.internal.hbm;

import java.util.Collections;
import java.util.List;

import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmCompositeAttributeType;
import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmDynamicComponentType;
import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmNestedCompositeElementType;
import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmToolingHintType;
import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmTuplizerType;
import org.hibernate.boot.model.source.spi.AttributePath;
import org.hibernate.boot.model.source.spi.AttributeRole;
import org.hibernate.boot.model.source.spi.AttributeSourceContainer;
import org.hibernate.boot.model.source.spi.EmbeddableMapping;
import org.hibernate.boot.model.source.spi.EmbeddedAttributeMapping;
import org.hibernate.boot.model.source.spi.NaturalIdMutability;
import org.hibernate.boot.model.source.spi.ToolingHintContext;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * The source information for a singular attribute whose type is composite
 * (embedded in JPA terms).
 *
 * @author Steve Ebersole
 */
class SingularAttributeSourceEmbeddedImpl extends AbstractSingularAttributeSourceEmbeddedImpl {
	private final String xmlNodeName;
	private final boolean insert;
	private final boolean update;
	private final boolean lazy;
	private final boolean optimisticLock;

	public SingularAttributeSourceEmbeddedImpl(
			MappingDocument sourceMappingDocument,
			AttributeSourceContainer parentContainer,
			final JaxbHbmCompositeAttributeType jaxbComponentElement,
			NaturalIdMutability naturalIdMutability,
			String logicalTableName) {
		super(
				sourceMappingDocument,
				parentContainer,
				new EmbeddedAttributeMapping() {
					private final EmbeddableMapping embeddableMapping = new EmbeddableMapping() {
						@Override
						@Prove(complexity = Complexity.O_N, n = "", count = {})
						public String getClazz() {
							return jaxbComponentElement.getClazz();
						}

						@Override
						@Prove(complexity = Complexity.O_N, n = "", count = {})
						public List<JaxbHbmTuplizerType> getTuplizer() {
							return jaxbComponentElement.getTuplizer();
						}

						@Override
						@Prove(complexity = Complexity.O_N, n = "", count = {})
						public String getParent() {
							return jaxbComponentElement.getParent() == null
									? null
									: jaxbComponentElement.getParent().getName();
						}
					};

					@Override
					@Prove(complexity = Complexity.O_N, n = "", count = {})
					public List<JaxbHbmToolingHintType> getToolingHints() {
						return jaxbComponentElement.getToolingHints();
					}

					@Override
					@Prove(complexity = Complexity.O_N, n = "", count = {})
					public String getName() {
						return jaxbComponentElement.getName();
					}

					@Override
					@Prove(complexity = Complexity.O_N, n = "", count = {})
					public String getAccess() {
						return jaxbComponentElement.getAccess();
					}

					@Override
					@Prove(complexity = Complexity.O_N, n = "", count = {})
					public boolean isUnique() {
						return jaxbComponentElement.isUnique();
					}

					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public EmbeddableMapping getEmbeddableMapping() {
						return embeddableMapping;
					}
				},
				jaxbComponentElement.getAttributes(),
				false,
				naturalIdMutability,
				logicalTableName
		);

		this.xmlNodeName = jaxbComponentElement.getNode();
		this.insert = jaxbComponentElement.isInsert();
		this.update = jaxbComponentElement.isUpdate();
		this.lazy = jaxbComponentElement.isLazy();
		this.optimisticLock = jaxbComponentElement.isOptimisticLock();
	}

	public SingularAttributeSourceEmbeddedImpl(
			MappingDocument mappingDocument,
			AttributeSourceContainer parentContainer,
			final JaxbHbmNestedCompositeElementType attributeJaxbMapping,
			NaturalIdMutability naturalIdMutability,
			String logicalTableName) {
		super(
				mappingDocument,
				parentContainer,
				new EmbeddedAttributeMapping() {
					private final EmbeddableMapping embeddableMapping = new EmbeddableMapping() {
						@Override
						@Prove(complexity = Complexity.O_N, n = "", count = {})
						public String getClazz() {
							return attributeJaxbMapping.getClazz();
						}

						@Override
						@Prove(complexity = Complexity.O_N, n = "", count = {})
						public List<JaxbHbmTuplizerType> getTuplizer() {
							return attributeJaxbMapping.getTuplizer();
						}

						@Override
						@Prove(complexity = Complexity.O_N, n = "", count = {})
						public String getParent() {
							return attributeJaxbMapping.getParent() == null
									? null
									: attributeJaxbMapping.getParent().getName();
						}
					};

					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public List<JaxbHbmToolingHintType> getToolingHints() {
						return Collections.emptyList();
					}

					@Override
					@Prove(complexity = Complexity.O_N, n = "", count = {})
					public String getName() {
						return attributeJaxbMapping.getName();
					}

					@Override
					@Prove(complexity = Complexity.O_N, n = "", count = {})
					public String getAccess() {
						return attributeJaxbMapping.getAccess();
					}

					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public boolean isUnique() {
						return false;
					}

					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public EmbeddableMapping getEmbeddableMapping() {
						return embeddableMapping;
					}
				},
				attributeJaxbMapping.getAttributes(),
				false,
				naturalIdMutability,
				logicalTableName
		);

		this.xmlNodeName = attributeJaxbMapping.getNode();
		this.insert = true;
		this.update = true;
		this.lazy = false;
		this.optimisticLock = true;
	}

	public SingularAttributeSourceEmbeddedImpl(
			MappingDocument sourceMappingDocument,
			AttributeSourceContainer parentContainer,
			final JaxbHbmDynamicComponentType jaxbDynamicEmbeddedMapping,
			NaturalIdMutability naturalIdMutability,
			String logicalTableName) {
		super(
				sourceMappingDocument,
				parentContainer,
				new EmbeddedAttributeMapping() {
					private final EmbeddableMapping embeddableMapping = new EmbeddableMapping() {
						@Override
						@Prove(complexity = Complexity.O_1, n = "", count = {})
						public String getClazz() {
							return null;
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
					};

					@Override
					@Prove(complexity = Complexity.O_N, n = "", count = {})
					public boolean isUnique() {
						return jaxbDynamicEmbeddedMapping.isUnique();
					}

					@Override
					@Prove(complexity = Complexity.O_N, n = "", count = {})
					public String getName() {
						return jaxbDynamicEmbeddedMapping.getName();
					}

					@Override
					@Prove(complexity = Complexity.O_N, n = "", count = {})
					public String getAccess() {
						return jaxbDynamicEmbeddedMapping.getAccess();
					}

					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public EmbeddableMapping getEmbeddableMapping() {
						return embeddableMapping;
					}

					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public List<JaxbHbmToolingHintType> getToolingHints() {
						return null;
					}
				},
				jaxbDynamicEmbeddedMapping.getAttributes(),
				true,
				naturalIdMutability,
				logicalTableName
		);

		this.xmlNodeName = jaxbDynamicEmbeddedMapping.getNode();
		this.insert = jaxbDynamicEmbeddedMapping.isInsert();
		this.update = jaxbDynamicEmbeddedMapping.isUpdate();
		this.lazy = false;
		this.optimisticLock = jaxbDynamicEmbeddedMapping.isOptimisticLock();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public XmlElementMetadata getSourceType() {
		return XmlElementMetadata.COMPONENT;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getXmlNodeName() {
		return xmlNodeName;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Boolean isInsertable() {
		return insert;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Boolean isUpdatable() {
		return update;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isBytecodeLazy() {
		return lazy;
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
		return optimisticLock;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public ToolingHintContext getToolingHintContext() {
		return getEmbeddableSource().getToolingHintContext();
	}

}
