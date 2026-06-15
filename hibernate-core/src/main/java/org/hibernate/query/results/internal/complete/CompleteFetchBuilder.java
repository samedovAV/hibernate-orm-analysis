/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.results.internal.complete;

import java.util.List;

import org.hibernate.query.results.spi.FetchBuilder;
import org.hibernate.sql.results.graph.Fetchable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface CompleteFetchBuilder extends FetchBuilder, ModelPartReference {
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Fetchable getReferencedPart();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<String> getColumnAliases();
}
