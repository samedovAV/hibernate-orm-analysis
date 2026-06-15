/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Additional contract describing the source of an identifier mapping whose {@linkplain #getNature() nature} is
 * {@linkplain org.hibernate.id.EntityIdentifierNature#SIMPLE simple}.
 *
 * @author Steve Ebersole
 */
public interface IdentifierSourceSimple extends IdentifierSource {
	/**
	 * Obtain the source descriptor for the identifier attribute.
	 *
	 * @return The identifier attribute source.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SingularAttributeSource getIdentifierAttributeSource();

	/**
	 *  Returns the "unsaved" entity identifier value.
	 *
	 *  @return the "unsaved" entity identifier value
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getUnsavedValue();

}
