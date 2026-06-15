/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.tree.expression;

import jakarta.annotation.Nullable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.function.Consumer;
import jakarta.annotation.Nonnull;
import jakarta.persistence.criteria.Expression;

import org.hibernate.Internal;
import org.hibernate.metamodel.model.domain.ReturnableType;
import org.hibernate.metamodel.model.domain.DomainType;
import org.hibernate.query.criteria.JpaExpression;
import org.hibernate.query.criteria.JpaPredicate;
import org.hibernate.query.spi.QueryEngine;
import org.hibernate.query.sqm.SqmBindableType;
import org.hibernate.query.sqm.tree.SqmCopyContext;
import org.hibernate.query.sqm.tree.predicate.SqmPredicate;
import org.hibernate.query.sqm.tree.select.SqmSelectableNode;
import org.hibernate.type.BasicType;

import static java.util.Arrays.asList;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * The base contract for any kind of expression node in the SQM tree.
 * An expression might be a reference to an attribute, a literal,
 * a function, etc.
 *
 * @param <T> The Java type of the expression
 *
 * @author Steve Ebersole
 */
public interface SqmExpression<T> extends SqmSelectableNode<T>, JpaExpression<T> {
	/**
	 * The expression's type.
	 * <p>
	 * Can change as a result of calls to {@link #applyInferableType}
	 */
	@Override
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmBindableType<T> getNodeType();

	/**
	 * Used to apply type information based on the expression's usage
	 * within the query.
	 *
	 * @apiNote The SqmExpressible type parameter is dropped here because
	 * the inference could technically cause a change in Java type (i.e.
	 * an implicit cast)
	 */
	@Internal
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void applyInferableType(@Nullable SqmBindableType<?> type);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void visitSubSelectableNodes(Consumer<SqmSelectableNode<?>> jpaSelectionConsumer) {
		jpaSelectionConsumer.accept( this );
	}

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default SqmExpression<Long> asLong() {
		return cast( Long.class );
	}

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default SqmExpression<Integer> asInteger() {
		return cast( Integer.class );
	}

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default SqmExpression<Float> asFloat() {
		return cast( Float.class );
	}

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default SqmExpression<Double> asDouble() {
		return cast( Double.class );
	}

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default SqmExpression<BigDecimal> asBigDecimal() {
		return cast( BigDecimal.class );
	}

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default SqmExpression<BigInteger> asBigInteger() {
		return cast( BigInteger.class );
	}

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default SqmExpression<String> asString() {
		return cast( String.class );
	}

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> SqmExpression<X> as(@Nonnull Class<X> type);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate isNull();

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate isNotNull();

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate equalTo(@Nonnull Expression<?> value);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate equalTo(Object value);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate in(@Nonnull Object... values);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate in(@Nonnull Expression<?>... values);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate in(@Nonnull Collection<?> values);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPredicate in(@Nonnull Expression<Collection<?>> values);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<T> copy(SqmCopyContext context);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default <X> SqmExpression<X> castAs(DomainType<X> type) {
		if ( getNodeType() == type ) {
			// safe cast, because we just checked
			@SuppressWarnings("unchecked")
			final SqmExpression<X> castExpression = (SqmExpression<X>) this;
			return castExpression;
		}
		else {
			final QueryEngine queryEngine = nodeBuilder().getQueryEngine();
			final SqmCastTarget<?> target = new SqmCastTarget<>( (ReturnableType<?>) type, nodeBuilder() );
			return queryEngine.getSqmFunctionRegistry().getFunctionDescriptor( "cast" )
					.generateSqmExpression( asList( this, target ), (ReturnableType<X>) type, queryEngine );
		}
	}

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default <X> SqmExpression<X> cast(@Nonnull Class<X> type) {
		final BasicType<X> basicType = nodeBuilder().getTypeConfiguration().getBasicTypeForJavaType( type );
		if ( basicType == null ) {
			throw new IllegalArgumentException( "Couldn't determine basic type for java type: " + type.getName() );
		}
		return castAs( basicType );
	}

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate notEqualTo(@Nonnull Expression<?> value);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate notEqualTo(Object value);
}
