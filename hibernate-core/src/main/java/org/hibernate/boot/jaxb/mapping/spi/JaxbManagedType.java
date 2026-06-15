/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.mapping.spi;

import jakarta.persistence.AccessType;
import jakarta.annotation.Nullable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Common interface for JAXB bindings representing entities, mapped-superclasses and embeddables (JPA collective
 * calls these "managed types" in terms of its Metamodel api).
 *
 * @author Strong Liu
 * @author Steve Ebersole
 */
public interface JaxbManagedType {

	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	String getDescription();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setDescription(@Nullable String value);

	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	String getClazz();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setClazz(@Nullable String className);

	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	Boolean isMetadataComplete();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setMetadataComplete(@Nullable Boolean isMetadataComplete);

	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	AccessType getAccess();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setAccess(@Nullable AccessType value);

	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbAttributesContainer getAttributes();
}
