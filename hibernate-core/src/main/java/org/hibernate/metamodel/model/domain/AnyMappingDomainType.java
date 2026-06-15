/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.model.domain;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;



/**
 * Models Hibernate's {@link org.hibernate.annotations.Any @Any} mapping
 * (reverse discrimination) as a JPA domain model type.
 *
 * @param <J> The base Java type defined for the {@code any} mapping
 *
 * @author Steve Ebersole
 */
public interface AnyMappingDomainType<J> extends SimpleDomainType<J> {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SimpleDomainType<?> getDiscriminatorType();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SimpleDomainType<?> getKeyType();
}
