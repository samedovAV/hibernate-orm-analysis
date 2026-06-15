/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.internal.hbm;

import org.hibernate.boot.model.source.spi.AssociationSource;
import org.hibernate.boot.model.source.spi.AttributeSource;
import org.hibernate.boot.model.source.spi.PluralAttributeSource;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Gail Badner
 * @author Steve Ebersole
 */
public abstract class AbstractPluralAssociationElementSourceImpl
		extends AbstractHbmSourceNode implements AssociationSource {
	private final PluralAttributeSource pluralAttributeSource;

	public AbstractPluralAssociationElementSourceImpl(
			MappingDocument mappingDocument,
			PluralAttributeSource pluralAttributeSource) {
		super( mappingDocument );
		this.pluralAttributeSource = pluralAttributeSource;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public AttributeSource getAttributeSource() {
		return pluralAttributeSource;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isMappedBy() {
		return false;
	}
}
