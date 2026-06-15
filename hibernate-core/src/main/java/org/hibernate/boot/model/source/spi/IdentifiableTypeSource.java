/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.spi;

import java.util.Collection;
import java.util.List;

import org.hibernate.boot.jaxb.Origin;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Common contract between Entity and MappedSuperclass sources.  The
 * terminology is taken from JPA's {@link jakarta.persistence.metamodel.IdentifiableType}
 *
 * @author Steve Ebersole
 */
public interface IdentifiableTypeSource extends AttributeSourceContainer {
	/**
	 * Obtain the origin of this source.
	 *
	 * @return The origin of this source.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Origin getOrigin();

	/**
	 * Get the hierarchy this belongs to.
	 *
	 * @return The hierarchy this belongs to.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EntityHierarchySource getHierarchy();

	/**
	 * Obtain the metadata-building context local to this entity source.
	 *
	 * @return The local binding context
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	LocalMetadataBuildingContext getLocalMetadataBuildingContext();

	/**
	 * Get the name of this type.
	 *
	 * @return The name of this type.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getTypeName();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	IdentifiableTypeSource getSuperType();

	/**
	 * Access the subtype sources for types extending from this type source,
	 *
	 * @return Subtype sources
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Collection<IdentifiableTypeSource> getSubTypes();

	/**
	 * Access to the sources describing JPA lifecycle callbacks.
	 *
	 * @return JPA lifecycle callback sources
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JpaCallbackSource> getJpaCallbackClasses();
}
