/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.model.domain.internal;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.hibernate.annotations.AnyDiscriminator;
import org.hibernate.metamodel.model.domain.SimpleDomainType;
import org.hibernate.metamodel.model.domain.ReturnableType;
import org.hibernate.query.sqm.SqmBindableType;
import org.hibernate.query.sqm.SqmPathSource;
import org.hibernate.query.sqm.tree.domain.SqmDomainType;
import org.hibernate.query.sqm.tree.domain.SqmPath;
import org.hibernate.type.BasicType;
import org.hibernate.type.descriptor.java.JavaType;

import static jakarta.persistence.metamodel.Type.PersistenceType.BASIC;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * {@link SqmPathSource} implementation for {@link AnyDiscriminator}
 *
 */
public class AnyDiscriminatorSqmPathSource<D> extends AbstractSqmPathSource<D>
		implements ReturnableType<D>, SqmBindableType<D> {

	private final BasicType<D> domainType;

	public AnyDiscriminatorSqmPathSource(
			String localPathName,
			SqmPathSource<D> pathModel,
			SimpleDomainType<D> domainType,
			BindableType jpaBindableType) {
		super( localPathName, pathModel, domainType, jpaBindableType );
		this.domainType = (BasicType<D>) domainType; // TODO: don't like this cast!
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqmPath<D> createSqmPath(SqmPath<?> lhs, @Nullable SqmPathSource<?> intermediatePathSource) {
		final var path = lhs.getNavigablePath();
		final var navigablePath =
				intermediatePathSource == null
						? path
						: path.append( intermediatePathSource.getPathName() );
		return new AnyDiscriminatorSqmPath<>( navigablePath, pathModel, lhs, lhs.nodeBuilder() );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqmPathSource<?> findSubPathSource(String name) {
		throw new IllegalStateException( "Entity discriminator cannot be de-referenced" );
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PersistenceType getPersistenceType() {
		return BASIC;
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<D> getJavaType() {
		return getExpressibleJavaType().getJavaTypeClass();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public @Nullable SqmDomainType<D> getSqmType() {
		return getPathType();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public BasicType<D> getPathType() {
		return domainType;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getTypeName() {
		return super.getTypeName();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public JavaType<D> getExpressibleJavaType() {
		return getPathType().getExpressibleJavaType();
	}
}
