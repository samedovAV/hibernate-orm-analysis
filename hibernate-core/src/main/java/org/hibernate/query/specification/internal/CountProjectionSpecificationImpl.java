/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.specification.internal;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.EntityHandler;
import jakarta.persistence.Timeout;
import jakarta.persistence.TypedQueryReference;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import org.hibernate.SharedSessionContract;
import org.hibernate.query.SelectionQuery;
import org.hibernate.query.restriction.Restriction;
import org.hibernate.query.specification.QuerySpecification;
import org.hibernate.query.specification.SelectionSpecification;
import org.hibernate.query.specification.SimpleProjectionSpecification;
import org.hibernate.query.spi.JpaTypedQueryReference;
import org.hibernate.query.sqm.tree.select.SqmSelectStatement;

import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyMap;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Gavin King
 */
public class CountProjectionSpecificationImpl<T> implements SimpleProjectionSpecification<T,Long>, JpaTypedQueryReference<Long> {

	private final SelectionSpecification<T> selectionSpecification;

	public CountProjectionSpecificationImpl(SelectionSpecification<T> specification) {
		this.selectionSpecification = specification;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public QuerySpecification<T> restrict(Restriction<? super T> restriction) {
		throw new UnsupportedOperationException( "This is not supported yet!" );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SelectionQuery<Long> createQuery(EntityHandler entityHandler) {
		return entityHandler.unwrap( SharedSessionContract.class )
				.createQuery( buildCriteria( entityHandler.getCriteriaBuilder() ) );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public CriteriaQuery<Long> buildCriteria(CriteriaBuilder builder) {
		var impl = (SelectionSpecificationImpl<T>) selectionSpecification;
		// TODO: handle HQL, existing criteria
		final var tupleQuery =
				(SqmSelectStatement<Long>)
						builder.createQuery( getResultType() );
		final var root = tupleQuery.from( impl.getResultType() );
		// This cast is completely bogus
		final var castStatement = (SqmSelectStatement<T>) tupleQuery;
		impl.getSpecifications().forEach( spec -> spec.accept( castStatement, root ) );
		tupleQuery.select( builder.count( root ) );
		return tupleQuery;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SimpleProjectionSpecification<T,Long> validate(CriteriaBuilder builder) {
		selectionSpecification.validate( builder );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public TypedQueryReference<Long> reference() {
		return this;
	}

	@Override
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getName() {
		return null;
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<Long> getResultType() {
		return Long.class;
	}

	@Override
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getEntityGraphName() {
		return null;
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Map<String, Object> getHints() {
		return emptyMap();
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<Class<?>> getParameterTypes() {
		return List.of();
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<String> getParameterNames() {
		return List.of();
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<Object> getArguments() {
		return List.of();
	}

	@Override
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Timeout getTimeout() {
		return null;
	}
}
