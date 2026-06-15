/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.mapping.internal;

import org.hibernate.metamodel.CollectionClassification;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * JAXB marshalling for {@link CollectionClassification}
 *
 * @author Steve Ebersole
 */
public class CollectionClassificationMarshalling {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static CollectionClassification fromXml(String name) {
		return name == null ? null : CollectionClassification.valueOf( name );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static String toXml(CollectionClassification classification) {
		return classification == null ? null : classification.name();
	}
}
