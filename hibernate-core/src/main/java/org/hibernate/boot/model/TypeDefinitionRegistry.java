/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model;

import java.util.Map;

import org.hibernate.type.descriptor.java.BasicJavaType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Chris Cranford
 * @author Steve Ebersole
 */
public interface TypeDefinitionRegistry {

	enum DuplicationStrategy {
		KEEP,
		OVERWRITE,
		DISALLOW
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	TypeDefinition resolve(String typeName);
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	TypeDefinition resolveAutoApplied(BasicJavaType<?> jtd);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	TypeDefinitionRegistry register(TypeDefinition typeDefinition);
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	TypeDefinitionRegistry register(TypeDefinition typeDefinition, DuplicationStrategy duplicationStrategy);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Map<String, TypeDefinition> copyRegistrationMap();
}
