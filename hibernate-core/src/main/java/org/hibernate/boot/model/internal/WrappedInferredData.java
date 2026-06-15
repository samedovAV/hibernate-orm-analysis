/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.internal;

import org.hibernate.MappingException;
import org.hibernate.boot.spi.AccessType;
import org.hibernate.boot.spi.PropertyData;
import org.hibernate.internal.util.StringHelper;
import org.hibernate.models.spi.ClassDetails;
import org.hibernate.models.spi.MemberDetails;
import org.hibernate.models.spi.TypeDetails;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Emmanuel Bernard
 */
public class WrappedInferredData implements PropertyData {
	private final PropertyData wrappedInferredData;
	private final String propertyName;

	public WrappedInferredData(PropertyData inferredData, String suffix) {
		this.wrappedInferredData = inferredData;
		this.propertyName = StringHelper.qualify( inferredData.getPropertyName(), suffix );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public TypeDetails getClassOrElementType() throws MappingException {
		return wrappedInferredData.getClassOrElementType();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public ClassDetails getClassOrPluralElement() throws MappingException {
		return wrappedInferredData.getClassOrPluralElement();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getClassOrElementName() throws MappingException {
		return wrappedInferredData.getClassOrElementName();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public AccessType getDefaultAccess() {
		return wrappedInferredData.getDefaultAccess();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public MemberDetails getAttributeMember() {
		return wrappedInferredData.getAttributeMember();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public ClassDetails getDeclaringClass() {
		return wrappedInferredData.getDeclaringClass();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public TypeDetails getPropertyType() throws MappingException {
		return wrappedInferredData.getPropertyType();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getPropertyName() throws MappingException {
		return propertyName;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getTypeName() throws MappingException {
		return wrappedInferredData.getTypeName();
	}
}
