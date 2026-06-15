/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.internal;

import java.util.function.Consumer;

import org.hibernate.query.sqm.DiscriminatorSqmPath;
import org.hibernate.query.sqm.spi.BaseSemanticQueryWalker;
import org.hibernate.query.sqm.tree.domain.NonAggregatedCompositeSimplePath;
import org.hibernate.query.sqm.tree.domain.SqmAnyValuedSimplePath;
import org.hibernate.query.sqm.tree.domain.SqmBasicValuedSimplePath;
import org.hibernate.query.sqm.tree.domain.SqmEmbeddedValuedSimplePath;
import org.hibernate.query.sqm.tree.domain.SqmEntityValuedSimplePath;
import org.hibernate.query.sqm.tree.domain.SqmPath;
import org.hibernate.query.sqm.tree.domain.SqmPluralValuedSimplePath;
import org.hibernate.query.sqm.tree.domain.SqmTreatedPath;
import org.hibernate.query.sqm.tree.from.SqmAttributeJoin;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Generic {@link org.hibernate.query.sqm.SemanticQueryWalker} that applies the provided
 * {@link Consumer} to all {@link SqmPath paths} encountered during visitation.
 *
 * @author Marco Belladelli
 */
public class SqmPathVisitor extends BaseSemanticQueryWalker {
	private final Consumer<SqmPath<?>> pathConsumer;

	public SqmPathVisitor(Consumer<SqmPath<?>> pathConsumer) {
		this.pathConsumer = pathConsumer;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object visitBasicValuedPath(SqmBasicValuedSimplePath<?> path) {
		pathConsumer.accept( path );
		return path;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object visitEmbeddableValuedPath(SqmEmbeddedValuedSimplePath<?> path) {
		pathConsumer.accept( path );
		return path;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object visitEntityValuedPath(SqmEntityValuedSimplePath<?> path) {
		pathConsumer.accept( path );
		return path;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object visitAnyValuedValuedPath(SqmAnyValuedSimplePath<?> path) {
		pathConsumer.accept( path );
		return path;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object visitQualifiedAttributeJoin(SqmAttributeJoin<?, ?> path) {
		pathConsumer.accept( path );
		return path;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object visitTreatedPath(SqmTreatedPath<?, ?> path) {
		pathConsumer.accept( path );
		return path;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object visitDiscriminatorPath(DiscriminatorSqmPath<?> path) {
		pathConsumer.accept( path );
		return path;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object visitPluralValuedPath(SqmPluralValuedSimplePath<?> path) {
		pathConsumer.accept( path );
		return path;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object visitNonAggregatedCompositeValuedPath(NonAggregatedCompositeSimplePath<?> path) {
		pathConsumer.accept( path );
		return path;
	}
}
