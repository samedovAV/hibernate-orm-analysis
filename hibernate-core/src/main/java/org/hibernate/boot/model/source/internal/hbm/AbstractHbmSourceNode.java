/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.internal.hbm;

import org.hibernate.boot.jaxb.Origin;
import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmHibernateMapping;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Base class for any and all source objects coming from {@code hbm.xml} parsing.  Defines standard access
 * back to the {@link MappingDocument} object and the services it provides (namely access to
 * {@link HbmLocalMetadataBuildingContext}).
 *
 * @author Steve Ebersole
 */
public abstract class AbstractHbmSourceNode {
	private final MappingDocument sourceMappingDocument;

	protected AbstractHbmSourceNode(MappingDocument sourceMappingDocument) {
		this.sourceMappingDocument = sourceMappingDocument;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected MappingDocument sourceMappingDocument() {
		return sourceMappingDocument;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected HbmLocalMetadataBuildingContext metadataBuildingContext() {
		return sourceMappingDocument;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected Origin origin() {
		return sourceMappingDocument().getOrigin();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected JaxbHbmHibernateMapping mappingRoot() {
		return sourceMappingDocument().getDocumentRoot();
	}
}
