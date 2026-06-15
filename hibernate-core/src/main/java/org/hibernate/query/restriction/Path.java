/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.restriction;

import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.FetchParent;
import jakarta.persistence.metamodel.SingularAttribute;
import org.hibernate.Incubating;
import org.hibernate.query.range.Range;

import java.util.List;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Allows construction of a {@link Restriction} on a compound path.
 * <p>
 * A compound path is a sequence of attribute references rooted at
 * the root entity type of the query.
 * <pre>
 * SelectionSpecification.create(Book.class)
 *         .restrict(from(Book.class).to(Book_.publisher).to(Publisher_.name)
 *                         .equalTo("Manning"))
 *         .fetch(from(Book.class).to(Book_.publisher))
 *         .createQuery(session)
 *         .getResultList()
 * </pre>
 * A compound path-based restriction has the same semantics as the
 * equivalent implicit join in HQL.
 *
 * @param <X> The root entity type
 * @param <U> The leaf attribute type
 *
 * @see Restriction
 *
 * @author Gavin King
 *
 * @since 7.0
 */
@Incubating
public interface Path<X,U> {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	jakarta.persistence.criteria.Path<U> path(Root<? extends X> root);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Class<U> getType();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default <V> Path<X, V> to(SingularAttribute<? super U, V> attribute) {
		return new PathElement<>( this, attribute );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default <V> Path<X, V> to(String attributeName, Class<V> attributeType) {
		return new NamedPathElement<>( this, attributeName, attributeType );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	static <X> Path<X, X> from(Class<X> type) {
		return new PathRoot<>( type );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default Restriction<X> restrict(Range<? super U> range) {
		return new PathRange<>( this, range );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default Restriction<X> equalTo(U value) {
		return restrict( Range.singleValue( value ) );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default Restriction<X> notEqualTo(U value) {
		return equalTo( value ).negated();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default Restriction<X> in(List<U> values) {
		return restrict( Range.valueList( values ) );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default Restriction<X> notIn(List<U> values) {
		return in( values ).negated();
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default Restriction<X> notNull() {
		return restrict( Range.notNull( getType() ) );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	FetchParent<?, ? extends U> fetch(Root<? extends X> root);
}
