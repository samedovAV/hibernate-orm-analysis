/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model;

import org.hibernate.dialect.Dialect;
import org.hibernate.query.sqm.function.SqmFunctionRegistry;
import org.hibernate.service.Service;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.spi.TypeConfiguration;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Allows custom function descriptors to be contributed to the eventual
 * {@link SqmFunctionRegistry}, either by a {@link org.hibernate.dialect.Dialect}
 * or by a {@link FunctionContributor}.
 *
 * @see FunctionContributor
 *
 * @author Christian Beikov
 */
public interface FunctionContributions {

	/**
	 * The registry into which the contributions should be made.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmFunctionRegistry getFunctionRegistry();

	/**
	 * Access to type information.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	TypeConfiguration getTypeConfiguration();

	/**
	 * Access to {@linkplain Service services}.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ServiceRegistry getServiceRegistry();

	/**
	 * The {@linkplain Dialect SQL Dialect}.
	 */
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default Dialect getDialect() {
		return getTypeConfiguration().getCurrentBaseSqlTypeIndicators().getDialect();
	}
}
