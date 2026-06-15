/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.internal.hbm;

import java.util.List;

import org.hibernate.boot.model.source.spi.AttributePath;
import org.hibernate.boot.model.source.spi.AttributeRole;
import org.hibernate.boot.model.source.spi.AttributeSourceContainer;
import org.hibernate.boot.model.source.spi.EmbeddableSource;
import org.hibernate.boot.model.source.spi.EmbeddedAttributeMapping;
import org.hibernate.boot.model.source.spi.HibernateTypeSource;
import org.hibernate.boot.model.source.spi.NaturalIdMutability;
import org.hibernate.boot.model.source.spi.SingularAttributeNature;
import org.hibernate.boot.model.source.spi.SingularAttributeSourceEmbedded;
import org.hibernate.boot.model.source.spi.ToolingHintContext;
import org.hibernate.boot.jaxb.mapping.GenerationTiming;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Common base class for {@code <component/>} and {@code <composite-id/>} mappings.
 *
 * @author Steve Ebersole
 */
public abstract class AbstractSingularAttributeSourceEmbeddedImpl
		extends AbstractHbmSourceNode
		implements SingularAttributeSourceEmbedded {

	private final EmbeddedAttributeMapping jaxbEmbeddedAttributeMapping;
	private final EmbeddableSource embeddableSource;
	private final NaturalIdMutability naturalIdMutability;

	protected AbstractSingularAttributeSourceEmbeddedImpl(
			final MappingDocument sourceMappingDocument,
			final AttributeSourceContainer container,
			final EmbeddedAttributeMapping embeddedAttributeMapping,
			List nestedAttributeMappings,
			boolean isDynamic,
			NaturalIdMutability naturalIdMutability,
			String logicalTableName) {
		this(
				sourceMappingDocument,
				embeddedAttributeMapping,
				new EmbeddableSourceImpl(
						sourceMappingDocument,
						new EmbeddableSourceContainer() {
							final AttributeRole role = container.getAttributeRoleBase().append(
									embeddedAttributeMapping.getName()
							);
							final AttributePath path = container.getAttributePathBase().append(
									embeddedAttributeMapping.getName()
							);
							final ToolingHintContext toolingHintContext = Helper.collectToolingHints(
									sourceMappingDocument.getToolingHintContext(),
									embeddedAttributeMapping
							);

							@Override
							@Prove(complexity = Complexity.O_1, n = "", count = {})
							public AttributeRole getAttributeRoleBase() {
								return role;
							}

							@Override
							@Prove(complexity = Complexity.O_1, n = "", count = {})
							public AttributePath getAttributePathBase() {
								return path;
							}

							@Override
							@Prove(complexity = Complexity.O_1, n = "", count = {})
							public ToolingHintContext getToolingHintContextBaselineForEmbeddable() {
								return toolingHintContext;
							}
						},
						embeddedAttributeMapping.getEmbeddableMapping(),
						nestedAttributeMappings,
						isDynamic,
						embeddedAttributeMapping.isUnique(),
						logicalTableName,
						naturalIdMutability
				),
				naturalIdMutability
		);
	}

	public AbstractSingularAttributeSourceEmbeddedImpl(
			MappingDocument sourceMappingDocument,
			EmbeddedAttributeMapping jaxbEmbeddedAttributeMapping,
			EmbeddableSource embeddableSource,
			NaturalIdMutability naturalIdMutability) {
		super( sourceMappingDocument );
		this.jaxbEmbeddedAttributeMapping = jaxbEmbeddedAttributeMapping;
		this.embeddableSource = embeddableSource;
		this.naturalIdMutability = naturalIdMutability;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public EmbeddableSource getEmbeddableSource() {
		return embeddableSource;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getName() {
		return jaxbEmbeddedAttributeMapping.getName();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isSingular() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isVirtualAttribute() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SingularAttributeNature getSingularAttributeNature() {
		return SingularAttributeNature.COMPOSITE;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public HibernateTypeSource getTypeInformation() {
		// <component/> does not support type information.
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getPropertyAccessorName() {
		return jaxbEmbeddedAttributeMapping.getAccess();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public NaturalIdMutability getNaturalIdMutability() {
		return naturalIdMutability;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public GenerationTiming getGenerationTiming() {
		// todo : is this correct here?
		return null;
	}
}
