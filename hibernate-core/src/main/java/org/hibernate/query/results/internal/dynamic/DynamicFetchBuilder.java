/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.results.internal.dynamic;

import java.util.List;

import org.hibernate.query.NativeQuery;
import org.hibernate.query.results.spi.FetchBuilder;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface DynamicFetchBuilder extends FetchBuilder, NativeQuery.ReturnProperty {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	DynamicFetchBuilder cacheKeyInstance();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<String> getColumnAliases();
}
