/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.named;

import java.util.function.Consumer;

import jakarta.persistence.sql.MappingElement;
import org.hibernate.SessionFactory;
import org.hibernate.query.internal.ResultSetMappingResolutionContext;
import org.hibernate.query.results.spi.ResultBuilder;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface ResultMementoInstantiation extends ResultMemento {
	class ArgumentMemento {
		private final ResultMemento argumentMemento;

		public ArgumentMemento(ResultMemento argumentMemento) {
			this.argumentMemento = argumentMemento;
		}

		@Prove(complexity = Complexity.O_N, n = "", count = {})
		public ResultBuilder resolve(Consumer<String> querySpaceConsumer, ResultSetMappingResolutionContext context) {
			return argumentMemento.resolve( querySpaceConsumer, context );
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public MappingElement<?> toJpaMapping(SessionFactory sessionFactory) {
			return argumentMemento.toJpaMappingElement( sessionFactory );
		}
	}
}
