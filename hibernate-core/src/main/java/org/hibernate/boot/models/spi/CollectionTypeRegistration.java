/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.spi;

import java.util.Map;

import org.hibernate.metamodel.CollectionClassification;
import org.hibernate.models.spi.ClassDetails;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Registration for a {@linkplain org.hibernate.usertype.UserCollectionType}
 *
 * @see org.hibernate.annotations.CollectionTypeRegistration
 * @see org.hibernate.boot.jaxb.mapping.spi.JaxbCollectionUserTypeImpl
 *
 * @author Steve Ebersole
 */
public class CollectionTypeRegistration {
	private final CollectionClassification classification;
	private final ClassDetails userTypeClass;
	private final Map<String,String> parameterMap;

	public CollectionTypeRegistration(
			CollectionClassification classification,
			ClassDetails userTypeClass,
			Map<String, String> parameterMap) {
		this.classification = classification;
		this.userTypeClass = userTypeClass;
		this.parameterMap = parameterMap;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public CollectionClassification getClassification() {
		return classification;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ClassDetails getUserTypeClass() {
		return userTypeClass;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Map<String, String> getParameterMap() {
		return parameterMap;
	}
}
