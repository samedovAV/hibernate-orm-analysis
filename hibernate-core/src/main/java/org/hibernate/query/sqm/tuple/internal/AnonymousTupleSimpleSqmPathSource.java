/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.tuple.internal;

import jakarta.annotation.Nullable;
import org.hibernate.Incubating;
import org.hibernate.metamodel.model.domain.internal.PathHelper;
import org.hibernate.query.sqm.SqmPathSource;
import org.hibernate.query.sqm.tree.domain.SqmBasicValuedSimplePath;
import org.hibernate.query.sqm.tree.domain.SqmPath;
import org.hibernate.query.sqm.tree.domain.SqmDomainType;
import org.hibernate.type.descriptor.java.JavaType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Christian Beikov
 */
@Incubating
public class AnonymousTupleSimpleSqmPathSource<J> implements SqmPathSource<J> {
	private final String localPathName;
	private final SqmDomainType<J> domainType;
	private final BindableType jpaBindableType;

	public AnonymousTupleSimpleSqmPathSource(
			String localPathName,
			SqmDomainType<J> domainType,
			BindableType jpaBindableType) {
		this.localPathName = localPathName;
		this.domainType = domainType;
		this.jpaBindableType = jpaBindableType;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<J> getBindableJavaType() {
		return domainType.getJavaType();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getPathName() {
		return localPathName;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqmDomainType<J> getPathType() {
		return domainType;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public BindableType getBindableType() {
		return jpaBindableType;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public JavaType<J> getExpressibleJavaType() {
		return domainType.getExpressibleJavaType();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqmPathSource<?> findSubPathSource(String name) {
		throw new IllegalStateException( "Basic paths cannot be dereferenced" );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqmPath<J> createSqmPath(SqmPath<?> lhs, @Nullable SqmPathSource<?> intermediatePathSource) {
		return new SqmBasicValuedSimplePath<>(
				PathHelper.append( lhs, this, intermediatePathSource ),
				this,
				lhs,
				lhs.nodeBuilder()
		);
	}
}
