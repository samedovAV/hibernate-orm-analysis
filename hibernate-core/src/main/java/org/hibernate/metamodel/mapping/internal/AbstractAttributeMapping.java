/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.mapping.internal;

import org.hibernate.metamodel.mapping.AttributeMapping;
import org.hibernate.metamodel.mapping.AttributeMetadata;
import org.hibernate.metamodel.mapping.ForeignKeyDescriptor;
import org.hibernate.metamodel.mapping.ManagedMappingType;
import org.hibernate.metamodel.mapping.MappingType;
import org.hibernate.property.access.spi.PropertyAccess;
import org.hibernate.type.descriptor.java.JavaType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public abstract class AbstractAttributeMapping implements AttributeMapping {
	private final String name;
	private final int fetchableIndex;
	private final int stateArrayPosition;

	private final ManagedMappingType declaringType;
	private final AttributeMetadata attributeMetadata;
	private final PropertyAccess propertyAccess;

	public AbstractAttributeMapping(
			String name,
			int fetchableIndex,
			ManagedMappingType declaringType,
			AttributeMetadata attributeMetadata,
			int stateArrayPosition,
			PropertyAccess propertyAccess) {
		this.name = name;
		this.fetchableIndex = fetchableIndex;
		this.declaringType = declaringType;
		this.attributeMetadata = attributeMetadata;
		this.stateArrayPosition = stateArrayPosition;
		this.propertyAccess = propertyAccess;
	}

	/**
	 * For Hibernate Reactive
	 */
	protected AbstractAttributeMapping(AbstractAttributeMapping original) {
		this(
				original.name,
				original.fetchableIndex,
				original.declaringType,
				original.attributeMetadata,
				original.stateArrayPosition,
				original.propertyAccess
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ManagedMappingType getDeclaringType() {
		return declaringType;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getAttributeName() {
		return name;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public AttributeMetadata getAttributeMetadata() {
		return attributeMetadata;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getStateArrayPosition() {
		return stateArrayPosition;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PropertyAccess getPropertyAccess() {
		return propertyAccess;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getFetchableKey() {
		return fetchableIndex;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public MappingType getPartMappingType() {
		return getMappedType();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JavaType<?> getJavaType() {
		return getMappedType().getMappedJavaType();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setForeignKeyDescriptor(ForeignKeyDescriptor foreignKeyDescriptor){
	}
}
