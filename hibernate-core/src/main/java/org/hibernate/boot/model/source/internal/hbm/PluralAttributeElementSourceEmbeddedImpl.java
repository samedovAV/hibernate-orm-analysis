/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.internal.hbm;

import java.util.List;

import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmCompositeCollectionElementType;
import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmTuplizerType;
import org.hibernate.boot.model.source.spi.AttributePath;
import org.hibernate.boot.model.source.spi.AttributeRole;
import org.hibernate.boot.model.source.spi.EmbeddableMapping;
import org.hibernate.boot.model.source.spi.EmbeddableSource;
import org.hibernate.boot.model.source.spi.NaturalIdMutability;
import org.hibernate.boot.model.source.spi.PluralAttributeElementNature;
import org.hibernate.boot.model.source.spi.PluralAttributeElementSourceEmbedded;
import org.hibernate.boot.model.source.spi.ToolingHintContext;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 * @author Gail Badner
 */
public class PluralAttributeElementSourceEmbeddedImpl
		extends AbstractHbmSourceNode
		implements PluralAttributeElementSourceEmbedded {

	private final EmbeddableSourceImpl embeddableSource;
	private final ToolingHintContext toolingHintContext;

	public PluralAttributeElementSourceEmbeddedImpl(
			MappingDocument mappingDocument,
			final AbstractPluralAttributeSourceImpl pluralAttributeSource,
			final JaxbHbmCompositeCollectionElementType jaxbCompositeElement) {
		super( mappingDocument );

		this.toolingHintContext = Helper.collectToolingHints(
				pluralAttributeSource.getToolingHintContext(),
				jaxbCompositeElement
		);

		this.embeddableSource = new EmbeddableSourceImpl(
				mappingDocument,
				new EmbeddableSourceContainer() {
					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public AttributeRole getAttributeRoleBase() {
						return pluralAttributeSource.getAttributeRole().append( "element" );
					}

					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public AttributePath getAttributePathBase() {
						return pluralAttributeSource.getAttributePath().append( "element" );
					}

					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public ToolingHintContext getToolingHintContextBaselineForEmbeddable() {
						return toolingHintContext;
					}
				},
				new EmbeddableMapping() {
					@Override
					@Prove(complexity = Complexity.O_N, n = "", count = {})
					public String getClazz() {
						return jaxbCompositeElement.getClazz();
					}

					@Override
					@Prove(complexity = Complexity.O_N, n = "", count = {})
					public List<JaxbHbmTuplizerType> getTuplizer() {
						return jaxbCompositeElement.getTuplizer();
					}

					@Override
					@Prove(complexity = Complexity.O_N, n = "", count = {})
					public String getParent() {
						return jaxbCompositeElement.getParent() == null
								? null
								: jaxbCompositeElement.getParent().getName();
					}
				},
				jaxbCompositeElement.getAttributes(),
				false,
				false,
				null,
				NaturalIdMutability.NOT_NATURAL_ID
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PluralAttributeElementNature getNature() {
		return PluralAttributeElementNature.AGGREGATE;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public EmbeddableSource getEmbeddableSource() {
		return embeddableSource;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ToolingHintContext getToolingHintContext() {
		return toolingHintContext;
	}

}
