/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.named;

import java.util.function.Consumer;

import jakarta.persistence.sql.MemberMapping;
import org.hibernate.SessionFactory;
import org.hibernate.query.internal.ResultSetMappingResolutionContext;
import org.hibernate.query.results.spi.FetchBuilder;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface FetchMemento extends ModelPartReferenceMemento {
	/**
	 * The parent node for the fetch
	 */
	interface Parent extends ModelPartReferenceMemento {
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		Class<?> getResultJavaType();
	}

	/**
	 * Resolve the fetch-memento into the result-graph-node builder
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	FetchBuilder resolve(Parent parent, Consumer<String> querySpaceConsumer, ResultSetMappingResolutionContext context);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default MemberMapping<?> toJpaMemberMapping(Parent parent, SessionFactory sessionFactory) {
		throw new UnsupportedOperationException( "Not implemented yet - " + getClass().getName() );
	}
}
