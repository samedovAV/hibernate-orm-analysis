/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.spi;

import org.hibernate.type.ForeignKeyDirection;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Further contract for sources of singular associations ({@code one-to-one} and {@code many-to-one}).
 *
 * @author Steve Ebersole
 */
public interface SingularAttributeSourceToOne
		extends SingularAttributeSource,
			ForeignKeyContributingSource,
			FetchableAttributeSource,
			AssociationSource,
			CascadeStyleSource{

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getReferencedEntityAttributeName();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getReferencedEntityName();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ForeignKeyDirection getForeignKeyDirection();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	FetchCharacteristicsSingularAssociation getFetchCharacteristics();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isUnique();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Boolean isEmbedXml();
}
