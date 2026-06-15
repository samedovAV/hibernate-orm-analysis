/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.internal;

import org.hibernate.query.hql.spi.SqmCreationOptions;
import org.hibernate.query.spi.QueryEngineOptions;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class SqmCreationOptionsStandard implements SqmCreationOptions {
	private final QueryEngineOptions queryEngineOptions;

	public SqmCreationOptionsStandard(QueryEngineOptions queryEngineOptions) {
		this.queryEngineOptions = queryEngineOptions;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean useStrictJpaCompliance() {
		return queryEngineOptions.getJpaCompliance().isJpaQueryComplianceEnabled();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isJsonFunctionsEnabled() {
		return queryEngineOptions.isJsonFunctionsEnabled();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isXmlFunctionsEnabled() {
		return queryEngineOptions.isXmlFunctionsEnabled();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isPortableIntegerDivisionEnabled() {
		return queryEngineOptions.isPortableIntegerDivisionEnabled();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isSafeModeEnabled() { return queryEngineOptions.isSafeModeEnabled(); }
}
