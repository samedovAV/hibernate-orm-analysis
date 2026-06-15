/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.internal.hbm;

import java.util.Collections;
import java.util.List;

import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmCompositeIndexType;
import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmMapKeyCompositeType;
import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmTuplizerType;
import org.hibernate.boot.model.source.spi.AttributePath;
import org.hibernate.boot.model.source.spi.AttributeRole;
import org.hibernate.boot.model.source.spi.EmbeddableMapping;
import org.hibernate.boot.model.source.spi.EmbeddableSource;
import org.hibernate.boot.model.source.spi.HibernateTypeSource;
import org.hibernate.boot.model.source.spi.NaturalIdMutability;
import org.hibernate.boot.model.source.spi.PluralAttributeIndexNature;
import org.hibernate.boot.model.source.spi.PluralAttributeMapKeySourceEmbedded;
import org.hibernate.boot.model.source.spi.ToolingHintContext;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Gail Badner
 */
public class PluralAttributeMapKeySourceEmbeddedImpl
		extends AbstractHbmSourceNode
		implements PluralAttributeMapKeySourceEmbedded {

	private final EmbeddableSourceImpl embeddableSource;

	public PluralAttributeMapKeySourceEmbeddedImpl(
			MappingDocument mappingDocument,
			AbstractPluralAttributeSourceImpl pluralAttributeSource,
			final JaxbHbmCompositeIndexType jaxbCompositeIndexElement) {
		this(
				mappingDocument,
				pluralAttributeSource,
				new EmbeddableMapping() {
					@Override
					@Prove(complexity = Complexity.O_N, n = "", count = {})
					public String getClazz() {
						return jaxbCompositeIndexElement.getClazz();
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
				},
				jaxbCompositeIndexElement.getAttributes()
		);
	}

	public PluralAttributeMapKeySourceEmbeddedImpl(
			MappingDocument mappingDocument,
			AbstractPluralAttributeSourceImpl pluralAttributeSource,
			final JaxbHbmMapKeyCompositeType jaxbCompositeMapKey) {
		this(
				mappingDocument,
				pluralAttributeSource,
				new EmbeddableMapping() {
					@Override
					@Prove(complexity = Complexity.O_N, n = "", count = {})
					public String getClazz() {
						return jaxbCompositeMapKey.getClazz();
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
				},
				jaxbCompositeMapKey.getAttributes()
		);
	}

	private PluralAttributeMapKeySourceEmbeddedImpl(
			MappingDocument mappingDocument,
			final AbstractPluralAttributeSourceImpl pluralAttributeSource,
			EmbeddableMapping jaxbEmbeddable,
			List attributeMappings) {
		super( mappingDocument );
		this.embeddableSource = new EmbeddableSourceImpl(
				mappingDocument,
				new EmbeddableSourceContainer() {
					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public AttributeRole getAttributeRoleBase() {
						return pluralAttributeSource.getAttributeRole().append( "key" );
					}

					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public AttributePath getAttributePathBase() {
						return pluralAttributeSource.getAttributePath().append( "key" );
					}

					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public ToolingHintContext getToolingHintContextBaselineForEmbeddable() {
						return pluralAttributeSource.getToolingHintContext();
					}
				},
				jaxbEmbeddable,
				attributeMappings,
				false,
				false,
				null,
				NaturalIdMutability.NOT_NATURAL_ID
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PluralAttributeIndexNature getNature() {
		return PluralAttributeIndexNature.AGGREGATE;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public EmbeddableSource getEmbeddableSource() {
		return embeddableSource;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public HibernateTypeSource getTypeInformation() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getXmlNodeName() {
		return null;
	}

}
