/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.named;

import java.util.function.Consumer;

import jakarta.persistence.sql.MappingElement;
import jakarta.persistence.sql.ResultSetMapping;
import org.hibernate.Incubating;
import org.hibernate.SessionFactory;
import org.hibernate.query.internal.ResultSetMappingResolutionContext;
import org.hibernate.query.results.spi.ResultBuilder;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Models a SQL ResultSet mapping generally defined via {@linkplain jakarta.persistence.SqlResultSetMapping annotations}.
 * May also be created via
 * .
 *
 * [1] Or through
 *
 * @since 6.0
 *
 * @author Steve Ebersole
 */
@Incubating
public interface ResultMemento extends ResultMappingMementoNode {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Class<?> getResultJavaType();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ResultBuilder resolve(Consumer<String> querySpaceConsumer, ResultSetMappingResolutionContext context);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default <R> boolean canBeTreatedAsResultSetMapping(Class<R> resultType, SessionFactory sessionFactory) {
		return resultType.isAssignableFrom( getResultJavaType() );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<R> ResultSetMapping<R> toJpaMapping(SessionFactory sessionFactory);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default <R> MappingElement<R> toJpaMappingElement(SessionFactory sessionFactory) {
		throw new UnsupportedOperationException();
	}
}
