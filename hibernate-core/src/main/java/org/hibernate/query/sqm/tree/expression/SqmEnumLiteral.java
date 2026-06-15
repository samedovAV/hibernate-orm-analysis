/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.tree.expression;

import jakarta.annotation.Nullable;
import jakarta.annotation.Nonnull;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Locale;

import org.hibernate.query.hql.spi.SemanticPathPart;
import org.hibernate.query.hql.spi.SqmCreationState;
import org.hibernate.query.sqm.NodeBuilder;
import org.hibernate.query.sqm.SemanticQueryWalker;
import org.hibernate.query.sqm.SqmBindableType;
import org.hibernate.query.sqm.UnknownPathException;
import org.hibernate.query.sqm.tree.SqmCopyContext;
import org.hibernate.query.sqm.tree.SqmRenderContext;
import org.hibernate.query.sqm.tree.domain.SqmPath;
import org.hibernate.query.sqm.tree.domain.SqmDomainType;
import org.hibernate.type.descriptor.java.EnumJavaType;

import static jakarta.persistence.metamodel.Type.PersistenceType.BASIC;
import static org.hibernate.internal.util.NullnessUtil.castNonNull;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Specialized SQM literal defined by an enum reference.  E.g.
 * {@code ".. where p.sex = Sex.MALE"}
 *
 * @author Steve Ebersole
 */
public class SqmEnumLiteral<E extends Enum<E>> extends SqmLiteral<E> implements SqmBindableType<E>, SemanticPathPart {
	private final EnumJavaType<E> referencedEnumTypeDescriptor;
	private final String enumValueName;

	public SqmEnumLiteral(
			E enumValue,
			EnumJavaType<E> referencedEnumTypeDescriptor,
			String enumValueName,
			NodeBuilder nodeBuilder) {
		super( null, enumValue, nodeBuilder );
		this.referencedEnumTypeDescriptor = referencedEnumTypeDescriptor;
		this.enumValueName = enumValueName;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqmEnumLiteral<E> copy(SqmCopyContext context) {
		final var existing = context.getCopy( this );
		if ( existing != null ) {
			return existing;
		}
		final SqmEnumLiteral<E> expression = context.registerCopy(
				this,
				new SqmEnumLiteral<>(
						getEnumValue(),
						referencedEnumTypeDescriptor,
						enumValueName,
						nodeBuilder()
				)
		);
		copyTo( expression, context );
		return expression;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqmBindableType<E> getExpressible() {
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public @Nonnull SqmBindableType<E> getNodeType() {
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PersistenceType getPersistenceType() {
		return BASIC;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public @Nullable SqmDomainType<E> getSqmType() {
		return null;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public E getEnumValue() {
		return castNonNull( getLiteralValue() );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public EnumJavaType<E> getExpressibleJavaType() {
		return referencedEnumTypeDescriptor;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<E> getJavaType() {
		return referencedEnumTypeDescriptor.getJavaTypeClass();
	}


	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// SemanticPathPart

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SemanticPathPart resolvePathPart(
			String name,
			boolean isTerminal,
			SqmCreationState creationState) {
		throw new UnknownPathException(
				String.format(
						Locale.ROOT,
						"Static enum reference [%s#%s] cannot be de-referenced",
						referencedEnumTypeDescriptor.getTypeName(),
						enumValueName
				)
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqmPath<?> resolveIndexedAccess(
			SqmExpression<?> selector,
			boolean isTerminal,
			SqmCreationState creationState) {
		throw new UnknownPathException(
				String.format(
						Locale.ROOT,
						"Static enum reference [%s#%s] cannot be de-referenced",
						referencedEnumTypeDescriptor.getTypeName(),
						enumValueName
				)
		);
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private Integer ordinalValue() {
		return getExpressibleJavaType().toOrdinal( getEnumValue() );
	}

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqmExpression<Long> asLong() {
		return nodeBuilder().literal( ordinalValue().longValue() );
	}

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqmExpression<Integer> asInteger() {
		return nodeBuilder().literal( ordinalValue() );
	}

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqmExpression<Float> asFloat() {
		return nodeBuilder().literal( ordinalValue().floatValue() );
	}

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqmExpression<Double> asDouble() {
		return nodeBuilder().literal( ordinalValue().doubleValue() );
	}

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqmExpression<BigDecimal> asBigDecimal() {
		throw new UnsupportedOperationException( "Enum literal cannot be cast to BigDecimal" );
	}

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqmExpression<BigInteger> asBigInteger() {
		throw new UnsupportedOperationException( "Enum literal cannot be cast to BigInteger" );
	}

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqmExpression<String> asString() {
		return nodeBuilder().literal( getExpressibleJavaType().toName( getEnumValue() ) );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <X> X accept(SemanticQueryWalker<X> walker) {
		return walker.visitEnumLiteral( this );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void appendHqlString(StringBuilder hql, SqmRenderContext context) {
		hql.append( getEnumValue().getDeclaringClass().getTypeName() );
		hql.append( '.' );
		hql.append( enumValueName );
	}
}
