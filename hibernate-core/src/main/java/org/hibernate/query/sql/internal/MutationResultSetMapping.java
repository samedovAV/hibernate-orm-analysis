/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sql.internal;

import org.hibernate.engine.spi.LoadQueryInfluencers;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.query.named.NamedResultSetMappingMemento;
import org.hibernate.query.results.spi.LegacyFetchBuilder;
import org.hibernate.query.results.spi.ResultBuilder;
import org.hibernate.query.results.spi.ResultSetMapping;
import org.hibernate.sql.results.jdbc.spi.JdbcValuesMapping;
import org.hibernate.sql.results.jdbc.spi.JdbcValuesMetadata;

import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/// ResultSetMapping implementation for situations where we *know*
/// the native-query is a mutation.
///
/// @author Steve Ebersole
public class MutationResultSetMapping implements ResultSetMapping {
	public static final MutationResultSetMapping INSTANCE = new MutationResultSetMapping();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getMappingIdentifier() {
		return "";
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isDynamic() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getNumberOfResultBuilders() {
		return 0;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<ResultBuilder> getResultBuilders() {
		return List.of();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void visitResultBuilders(BiConsumer<Integer, ResultBuilder> resultBuilderConsumer) {
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void visitLegacyFetchBuilders(Consumer<LegacyFetchBuilder> resultBuilderConsumer) {
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void addResultBuilder(ResultBuilder resultBuilder) {
		throw new UnsupportedOperationException( "MutationQuery cannot define results" );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void addLegacyFetchBuilder(LegacyFetchBuilder fetchBuilder) {
		throw new UnsupportedOperationException( "MutationQuery cannot define results" );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public NamedResultSetMappingMemento toMemento(String name) {
		throw new UnsupportedOperationException( "MutationQuery cannot define results" );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JdbcValuesMapping resolve(JdbcValuesMetadata jdbcResultsMetadata, LoadQueryInfluencers loadQueryInfluencers, SessionFactoryImplementor sessionFactory) {
		throw new UnsupportedOperationException( "MutationQuery cannot define results" );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void addAffectedTableNames(Set<String> affectedTableNames, SessionFactoryImplementor sessionFactory) {
		throw new UnsupportedOperationException( "MutationQuery cannot define results" );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ResultSetMapping cacheKeyInstance() {
		return null;
	}
}
