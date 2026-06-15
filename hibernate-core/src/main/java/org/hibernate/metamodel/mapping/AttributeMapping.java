/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.mapping;

import org.hibernate.generator.Generator;
import org.hibernate.metamodel.mapping.internal.EmbeddedAttributeMapping;
import org.hibernate.property.access.spi.PropertyAccess;
import org.hibernate.sql.results.graph.DatabaseSnapshotContributor;
import org.hibernate.sql.results.graph.Fetchable;
import org.hibernate.type.descriptor.java.JavaType;
import org.hibernate.type.descriptor.java.MutabilityPlan;
import org.hibernate.type.descriptor.java.MutabilityPlanExposer;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Describes an attribute at the mapping model level.
 *
 * @author Steve Ebersole
 */
public interface AttributeMapping
		extends OwnedValuedModelPart, Fetchable, DatabaseSnapshotContributor, PropertyBasedMapping, MutabilityPlanExposer {
	/**
	 * The name of the mapped attribute
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getAttributeName();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default String getPartName() {
		return getAttributeName();
	}

	/**
	 * The attribute's position within the container's state array
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	int getStateArrayPosition();

	/**
	 * Access to AttributeMetadata
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	AttributeMetadata getAttributeMetadata();

	/**
	 * The managed type that declares this attribute
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ManagedMappingType getDeclaringType();

	/**
	 * The getter/setter access to this attribute
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	PropertyAccess getPropertyAccess();

	/**
	 * Convenient access to getting the value for this attribute from the declarer
	 */
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default Object getValue(Object container) {
		return getDeclaringType().getValue( container, getStateArrayPosition() );
	}

	/**
	 * Convenient access to setting the value for this attribute on the declarer
	 */
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default void setValue(Object container, Object value) {
		getDeclaringType().setValue( container, getStateArrayPosition(), value );
	}

	/**
	 * The value generation strategy to use for this attribute.
	 *
	 * @apiNote Only relevant for non-id attributes
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Generator getGenerator();

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default EntityMappingType findContainingEntityMapping() {
		return getDeclaringType().findContainingEntityMapping();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default MutabilityPlan<?> getExposedMutabilityPlan() {
		return getAttributeMetadata().getMutabilityPlan();
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default int compare(Object value1, Object value2) {
		//noinspection unchecked,rawtypes
		return ( (JavaType) getJavaType() ).getComparator().compare( value1, value2 );
	}

	@Override //Overrides multiple interfaces!
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default AttributeMapping asAttributeMapping() {
		return this;
	}

	/**
	 * A utility method to avoid casting explicitly to PluralAttributeMapping
	 *
	 * @return PluralAttributeMapping if this is an instance of PluralAttributeMapping otherwise {@code null}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default PluralAttributeMapping asPluralAttributeMapping() {
		return null;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean isPluralAttributeMapping() {
		return false;
	}

	/**
	 * A utility method to avoid casting explicitly to EmbeddedAttributeMapping
	 *
	 * @return EmbeddedAttributeMapping if this is an instance of EmbeddedAttributeMapping otherwise {@code null}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default EmbeddedAttributeMapping asEmbeddedAttributeMapping(){
		return null;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean isEmbeddedAttributeMapping(){
		return false;
	}

}
