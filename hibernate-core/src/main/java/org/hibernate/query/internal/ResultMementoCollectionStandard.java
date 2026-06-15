/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.internal;

import java.util.function.Consumer;

import jakarta.persistence.sql.MappingElement;
import jakarta.persistence.sql.ResultSetMapping;
import org.hibernate.SessionFactory;
import org.hibernate.metamodel.mapping.PluralAttributeMapping;
import org.hibernate.spi.NavigablePath;
import org.hibernate.query.named.ModelPartResultMementoCollection;
import org.hibernate.query.results.spi.ResultBuilder;
import org.hibernate.query.results.internal.complete.CompleteResultBuilderCollectionStandard;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class ResultMementoCollectionStandard implements ModelPartResultMementoCollection {
	private final String tableAlias;
	private final NavigablePath navigablePath;
	private final PluralAttributeMapping pluralAttributeDescriptor;

	public ResultMementoCollectionStandard(
			String tableAlias,
			NavigablePath navigablePath,
			PluralAttributeMapping pluralAttributeDescriptor) {
		this.tableAlias = tableAlias;
		this.navigablePath = navigablePath;
		this.pluralAttributeDescriptor = pluralAttributeDescriptor;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public NavigablePath getNavigablePath() {
		return navigablePath;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PluralAttributeMapping getPluralAttributeDescriptor() {
		return pluralAttributeDescriptor;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<?> getResultJavaType() {
		return pluralAttributeDescriptor.getJavaType().getJavaTypeClass();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ResultBuilder resolve(
			Consumer<String> querySpaceConsumer,
			ResultSetMappingResolutionContext context) {
		return new CompleteResultBuilderCollectionStandard(
				tableAlias,
				navigablePath,
				pluralAttributeDescriptor
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <R> boolean canBeTreatedAsResultSetMapping(Class<R> resultType, SessionFactory sessionFactory) {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <R> ResultSetMapping<R> toJpaMapping(SessionFactory sessionFactory) {
		throw new UnsupportedOperationException();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <R> MappingElement<R> toJpaMappingElement(SessionFactory sessionFactory) {
		throw new UnsupportedOperationException();
	}
}
