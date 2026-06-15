/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.mapping;

import java.util.List;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Commonality between {@link PersistentClass} and {@link MappedSuperclass},
 * what JPA calls an {@linkplain jakarta.persistence.metamodel.IdentifiableType identifiable type}.
 *
 * @author Steve Ebersole
 */
public interface IdentifiableTypeClass extends TableContainer {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	IdentifiableTypeClass getSuperType();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<IdentifiableTypeClass> getSubTypes();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<Property> getDeclaredProperties();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Component getIdentifierMapper();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Table getImplicitTable();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isVersioned();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Property getVersion();
}
