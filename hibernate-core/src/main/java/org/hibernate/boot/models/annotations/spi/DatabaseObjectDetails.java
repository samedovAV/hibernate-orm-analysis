/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.annotations.spi;

import java.lang.annotation.Annotation;

import jakarta.persistence.SequenceGenerator;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Commonality for annotations which represent database objects.
 *
 * @apiNote While they all have names, some use an attribute other than {@code name()} - e.g. {@linkplain SequenceGenerator#sequenceName()}
 *
 * @author Steve Ebersole
 */
public interface DatabaseObjectDetails extends Annotation {
	/**
	 * The catalog in which the object exists
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String catalog();

	/**
	 * Setter for {@linkplain #catalog()}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void catalog(String catalog);

	/**
	 * The schema in which the object exists
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String schema();

	/**
	 * Setter for {@linkplain #schema()}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void schema(String schema);
}
