/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.mapping.internal;

import org.hibernate.engine.spi.CascadeStyle;
import org.hibernate.engine.spi.CascadeStyles;
import org.hibernate.mapping.Property;
import org.hibernate.mapping.Value;
import org.hibernate.metamodel.mapping.AttributeMetadata;
import org.hibernate.property.access.spi.PropertyAccess;
import org.hibernate.type.descriptor.java.MutabilityPlan;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

public final class SimpleAttributeMetadata implements AttributeMetadata {

	private final PropertyAccess propertyAccess;
	private final MutabilityPlan<?> mutabilityPlan;
	private final boolean nullable;
	private final boolean insertable;
	private final boolean updateable;
	private final boolean selectable;
	private final boolean includeInOptimisticLocking;
	private final CascadeStyle cascadeStyle;

	public SimpleAttributeMetadata(
			PropertyAccess propertyAccess,
			MutabilityPlan mutabilityPlan,
			Property bootProperty,
			Value value
			) {
		this(
				propertyAccess,
				mutabilityPlan,
				value.isNullable(),
				bootProperty.isInsertable(),
				bootProperty.isUpdatable(),
				bootProperty.isOptimisticLocked(),
				bootProperty.isSelectable()
		);
	}

	public SimpleAttributeMetadata(
			PropertyAccess propertyAccess,
			MutabilityPlan mutabilityPlan,
			boolean nullable,
			boolean insertable,
			boolean updateable,
			boolean includeInOptimisticLocking,
			boolean selectable) {
		this(
				propertyAccess,
				mutabilityPlan,
				nullable,
				insertable,
				updateable,
				includeInOptimisticLocking,
				selectable,
				CascadeStyles.NONE // default - but beware of comment on AttributeMetadata#getCascadeStyle having a TODO
		);
	}

	public SimpleAttributeMetadata(
			PropertyAccess propertyAccess,
			MutabilityPlan mutabilityPlan,
			boolean nullable,
			boolean insertable,
			boolean updateable,
			boolean includeInOptimisticLocking,
			boolean selectable,
			CascadeStyle cascadeStyle) {
		this.propertyAccess = propertyAccess;
		this.mutabilityPlan = mutabilityPlan;
		this.nullable = nullable;
		this.insertable = insertable;
		this.updateable = updateable;
		this.selectable = selectable;
		this.includeInOptimisticLocking = includeInOptimisticLocking;
		this.cascadeStyle = cascadeStyle;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PropertyAccess getPropertyAccess() {
		return propertyAccess;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public MutabilityPlan getMutabilityPlan() {
		return mutabilityPlan;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isNullable() {
		return nullable;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isInsertable() {
		return insertable;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isUpdatable() {
		return updateable;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isSelectable(){
		return selectable;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isIncludedInDirtyChecking() {
		// todo (6.0) : do not believe this is correct
		return updateable;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isIncludedInOptimisticLocking() {
		return includeInOptimisticLocking;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public CascadeStyle getCascadeStyle() {
		return cascadeStyle;
	}

}
