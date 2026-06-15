/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Describes a relationship annotated with {@link jakarta.persistence.MapsId}
 *
 * @author Steve Ebersole
 */
public interface MapsIdSource {
	/**
	 * Obtain the {@link jakarta.persistence.MapsId#value()} naming the attribute
	 * within the EmbeddedId mapped by this relationship.
	 *
	 * @return The corresponding id attribute name.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getMappedIdAttributeName();

	/**
	 * The attribute source information
	 *
	 * @return The association attribute information
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SingularAttributeSourceToOne getAssociationAttributeSource();
}
