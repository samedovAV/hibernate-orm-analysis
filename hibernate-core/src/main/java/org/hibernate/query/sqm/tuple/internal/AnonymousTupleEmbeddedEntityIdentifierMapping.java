/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.tuple.internal;

import java.util.Set;

import org.hibernate.Incubating;
import org.hibernate.engine.spi.IdentifierValue;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.event.spi.MergeContext;
import org.hibernate.metamodel.mapping.CompositeIdentifierMapping;
import org.hibernate.metamodel.mapping.EmbeddableMappingType;
import org.hibernate.metamodel.mapping.SqlTypedMapping;
import org.hibernate.metamodel.mapping.internal.SingleAttributeIdentifierMapping;
import org.hibernate.metamodel.model.domain.DomainType;
import org.hibernate.property.access.spi.PropertyAccess;
import org.hibernate.query.sqm.SqmExpressible;

import jakarta.persistence.metamodel.Attribute;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Christian Beikov
 */
@Incubating
public class AnonymousTupleEmbeddedEntityIdentifierMapping extends AnonymousTupleEmbeddableValuedModelPart
		implements CompositeIdentifierMapping, SingleAttributeIdentifierMapping {

	private final CompositeIdentifierMapping delegate;

	public AnonymousTupleEmbeddedEntityIdentifierMapping(
			SqmExpressible<?> sqmExpressible,
			SqlTypedMapping[] sqlTypedMappings,
			int selectionIndex,
			String selectionExpression,
			Set<String> compatibleTableExpressions,
			Set<? extends Attribute<?, ?>> attributes,
			DomainType<?> domainType,
			CompositeIdentifierMapping delegate) {
		super(
				sqmExpressible,
				sqlTypedMappings,
				selectionIndex,
				selectionExpression,
				compatibleTableExpressions,
				attributes,
				domainType,
				delegate.getAttributeName(),
				delegate,
				-1
		);
		this.delegate = delegate;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Nature getNature() {
		return delegate.getNature();
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
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public PropertyAccess getPropertyAccess() {
		return ((SingleAttributeIdentifierMapping) delegate).getPropertyAccess();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public EmbeddableMappingType getPartMappingType() {
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public int compare(Object value1, Object value2) {
		return super.compare( value1, value2 );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getAttributeName() {
		return getPartName();
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

}
