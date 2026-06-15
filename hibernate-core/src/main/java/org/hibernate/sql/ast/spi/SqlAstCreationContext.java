/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.spi;

import org.hibernate.dialect.Dialect;
import org.hibernate.engine.profile.FetchProfile;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.metamodel.spi.MappingMetamodelImplementor;
import org.hibernate.query.sqm.function.SqmFunctionRegistry;
import org.hibernate.type.BindingContext;
import org.hibernate.type.descriptor.WrapperOptions;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * The "context" in which creation of SQL AST occurs. Provides
 * access to stuff generally needed when creating SQL AST nodes
 * <p>
 * Because we would like to be able to render SQL during the
 * startup cycle, before the {@code SessionFactory} is completely
 * initialized, code involved in SQL AST creation and rendering
 * should avoid making use of the {@code SessionFactory}.
 * Instead, use the objects exposed by this creation context.
 *
 * @author Steve Ebersole
 */
public interface SqlAstCreationContext extends BindingContext {
	/**
	 * Avoid calling this method directly, as much as possible.
	 * SQL AST creation should not depend on the existence of
	 * a session factory, so if you need to obtain this object,
	 * there's something wrong with the design.
	 * <p>
	 * Currently this is only called when creating a
	 * {@link org.hibernate.sql.ast.tree.from.TableGroup},
	 * but we will introduce a new sort of creation context
	 * for that, probably.
	 */
	@Deprecated
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SessionFactoryImplementor getSessionFactory();

	/**
	 * The runtime {@link MappingMetamodelImplementor}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	MappingMetamodelImplementor getMappingMetamodel();

	/**
	 * When creating {@link org.hibernate.sql.results.graph.Fetch} references,
	 * defines a limit to how deep we should join for fetches.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Integer getMaximumFetchDepth();

	/**
	 * @see org.hibernate.jpa.spi.JpaCompliance#isJpaQueryComplianceEnabled
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isJpaQueryComplianceEnabled();

	/**
	 * Obtain the definition of a named {@link FetchProfile}.
	 *
	 * @param name The name of the fetch profile
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	FetchProfile getFetchProfile(String name);

	/**
	 * Obtain the {@link SqmFunctionRegistry}.
	 */
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default SqmFunctionRegistry getSqmFunctionRegistry() {
		return getSessionFactory().getQueryEngine().getSqmFunctionRegistry();
	}

	/**
	 * Obtain the {@link Dialect}.
	 */
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default Dialect getDialect() {
		return getSessionFactory().getQueryEngine().getDialect();
	}

	/**
	 * Obtain the "incomplete" {@link WrapperOptions} that would be
	 * returned by {@link SessionFactoryImplementor#getWrapperOptions()}.
	 */
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default WrapperOptions getWrapperOptions() {
		return getSessionFactory().getWrapperOptions();
	}
}
