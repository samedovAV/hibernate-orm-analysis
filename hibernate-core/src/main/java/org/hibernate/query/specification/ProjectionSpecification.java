/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.specification;

import jakarta.persistence.EntityHandler;
import jakarta.persistence.TypedQueryReference;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.metamodel.SingularAttribute;
import org.hibernate.Incubating;
import org.hibernate.query.SelectionQuery;
import org.hibernate.query.restriction.Path;
import org.hibernate.query.specification.internal.ProjectionSpecificationImpl;

import java.util.function.Function;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Allows a {@link SelectionSpecification} to be augmented with the specification
 * of a projection list.
 * <pre>
 * var specification =
 *         SelectionSpecification.create(Book.class)
 *                 .restrict(Restriction.contains(Book_.title, "hibernate", false))
 *                 .sort(Order.desc(Book_.title));
 * var projection = ProjectionSpecification.create(specification);
 * var bookIsbn = projection.select(Book_.isbn);
 * var bookTitle = projection.select(Book_.title);
 * var results = projection.createQuery(session).getResultList();
 * for (var result : results) {
 *     var isbn = bookIsbn.in(result);
 *     var title = bookTitle.in(result);
 *     ...
 * }
 * </pre>
 * <p>
 * A {@code ProjectionSpecification} always results in a query which with result
 * type {@code Object[]}. The {@link #select(SingularAttribute) select()} methods
 * return {@link Element}, allowing easy and typesafe access to the elements of
 * the returned array.
 *
 * @param <T> The result type of the {@link SelectionSpecification}
 *
 * @since 7.2
 *
 * @apiNote This interface marked {@link Incubating} is considered experimental.
 *          Changes to the API defined here are fully expected in future releases.
 *
 * @author Gavin King
 */
@Incubating
public interface ProjectionSpecification<T> extends QuerySpecification<Object[]> {

	/**
	 * Create a new {@code ProjectionSpecification} which augments the given
	 * {@link SelectionSpecification}.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	static <T> ProjectionSpecification<T> create(SelectionSpecification<T> selectionSpecification) {
		return new ProjectionSpecificationImpl<>( selectionSpecification );
	}

	/**
	 * Allows typesafe access to elements of the {@code Object[]}
	 * arrays returned by the query.
	 *
	 * @param <X> The type of the element of the projection list
	 */
	@FunctionalInterface
	interface Element<X> extends Function<Object[],X> {
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		X in(Object[] tuple);

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		default X apply(Object[] tuple) {
			return in(tuple);
		}
	}

	/**
	 * Select the given attribute of the root entity.
	 *
	 * @param attribute An attribute of the root entity
	 * @return An {@link Element} allowing typesafe access to the results
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> Element<X> select(SingularAttribute<T,X> attribute);

	/**
	 * Select the given field or property identified by the given path
	 * from of the root entity.
	 *
	 * @param path A path from the root entity
	 * @return An {@link Element} allowing typesafe access to the results
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> Element<X> select(Path<T,X> path);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SelectionQuery<Object[]> createQuery(EntityHandler entityHandler);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CriteriaQuery<Object[]> buildCriteria(CriteriaBuilder builder);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	TypedQueryReference<Object[]> reference();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ProjectionSpecification<T> validate(CriteriaBuilder builder);
}
