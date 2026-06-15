/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.tuple.internal;

import java.util.Set;

import org.hibernate.Incubating;
import org.hibernate.engine.FetchStyle;
import org.hibernate.engine.FetchTiming;
import org.hibernate.engine.spi.IdentifierValue;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.event.spi.MergeContext;
import org.hibernate.metamodel.mapping.EmbeddableMappingType;
import org.hibernate.metamodel.mapping.NonAggregatedIdentifierMapping;
import org.hibernate.metamodel.mapping.SqlTypedMapping;
import org.hibernate.metamodel.mapping.internal.IdClassEmbeddable;
import org.hibernate.metamodel.mapping.internal.VirtualIdEmbeddable;
import org.hibernate.metamodel.model.domain.DomainType;
import org.hibernate.query.sqm.SqmExpressible;

import jakarta.persistence.metamodel.Attribute;
import jakarta.annotation.Nullable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Christian Beikov
 */
@Incubating
public class AnonymousTupleNonAggregatedEntityIdentifierMapping extends AnonymousTupleEmbeddableValuedModelPart
		implements NonAggregatedIdentifierMapping {

	private final NonAggregatedIdentifierMapping delegate;

	public AnonymousTupleNonAggregatedEntityIdentifierMapping(
			SqmExpressible<?> sqmExpressible,
			SqlTypedMapping[] sqlTypedMappings,
			int selectionIndex,
			String selectionExpression,
			Set<String> compatibleTableExpressions,
			Set<? extends Attribute<?, ?>> attributes,
			DomainType<?> domainType,
			String componentName,
			NonAggregatedIdentifierMapping delegate) {
		super(
				sqmExpressible,
				sqlTypedMappings,
				selectionIndex,
				selectionExpression,
				compatibleTableExpressions,
				attributes,
				domainType,
				componentName,
				delegate,
				-1
		);
		this.delegate = delegate;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Nature getNature() {
		return Nature.VIRTUAL;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getAttributeName() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public IdentifierValue getUnsavedStrategy() {
		return delegate.getUnsavedStrategy();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Object getIdentifier(Object entity) {
		return delegate.getIdentifier( entity );
	}


	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Object getIdentifier(Object entity, MergeContext mergeContext) {
		return delegate.getIdentifier( entity, mergeContext );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void setIdentifier(Object entity, Object id, SharedSessionContractImplementor session) {
		delegate.setIdentifier( entity, id, session );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Object instantiate() {
		return delegate.instantiate();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean hasContainingClass() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public EmbeddableMappingType getMappedIdEmbeddableTypeDescriptor() {
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public EmbeddableMappingType getMappedType() {
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public EmbeddableMappingType getPartMappingType() {
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public VirtualIdEmbeddable getVirtualIdEmbeddable() {
		return delegate.getVirtualIdEmbeddable();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public IdClassEmbeddable getIdClassEmbeddable() {
		return delegate.getIdClassEmbeddable();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public IdentifierValueMapper getIdentifierValueMapper() {
		return delegate.getIdentifierValueMapper();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public FetchStyle getStyle() {
		return FetchStyle.JOIN;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public FetchTiming getTiming() {
		return FetchTiming.IMMEDIATE;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean areEqual(@Nullable Object one, @Nullable Object other, SharedSessionContractImplementor session) {
		return delegate.areEqual( one, other, session );
	}
}
