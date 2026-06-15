/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.results.graph;

import org.hibernate.bytecode.enhance.spi.LazyPropertyInitializer;
import org.hibernate.sql.results.jdbc.spi.RowProcessingState;
import org.hibernate.type.descriptor.java.JavaType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Christian Beikov
 */
public class UnfetchedResultAssembler<J>  implements DomainResultAssembler<J> {

	private final JavaType<J> javaType;

	public UnfetchedResultAssembler(JavaType<J> javaType) {
		this.javaType = javaType;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public J assemble(RowProcessingState rowProcessingState) {
		return (J) LazyPropertyInitializer.UNFETCHED_PROPERTY;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JavaType<J> getAssembledJavaType() {
		return javaType;
	}

}
