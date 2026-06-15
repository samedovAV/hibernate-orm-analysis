/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.spi;

import org.hibernate.Incubating;
import org.hibernate.Internal;
import org.hibernate.boot.registry.classloading.spi.ClassLoaderService;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.query.spi.NativeQueryInterpreter;
import org.hibernate.query.hql.HqlTranslator;
import org.hibernate.query.named.NamedObjectRepository;
import org.hibernate.query.sqm.NodeBuilder;
import org.hibernate.query.sqm.function.SqmFunctionRegistry;
import org.hibernate.query.sqm.sql.SqmTranslatorFactory;
import org.hibernate.type.BindingContext;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Aggregation and encapsulation of the components Hibernate uses
 * to execute queries (HQL, Criteria and native)
 *
 * @author Steve Ebersole
 * @author Gavin King
 */
@Incubating
public interface QueryEngine extends BindingContext {

	/**
	 * The default soft reference count.
	 */
	int DEFAULT_QUERY_PLAN_MAX_COUNT = 2048;

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryInterpreter getNativeQueryInterpreter();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	QueryInterpretationCache getInterpretationCache();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmFunctionRegistry getSqmFunctionRegistry();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NodeBuilder getCriteriaBuilder();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Dialect getDialect();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void close();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void validateNamedQueries();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NamedObjectRepository getNamedObjectRepository();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	HqlTranslator getHqlTranslator();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmTranslatorFactory getSqmTranslatorFactory();

	/**
	 * Avoid use of this, because Hibernate Processor can't do class loading
	 */
	@Internal
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ClassLoaderService getClassLoaderService();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default <R> HqlInterpretation<R> interpretHql(String hql, Class<R> resultType) {
		return getInterpretationCache().resolveHqlInterpretation( hql, resultType, getHqlTranslator() );
	}
}
