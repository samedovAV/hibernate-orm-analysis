/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.mapping.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Common interface for JAXB bindings that understand database schema (tables, sequences, etc).
 *
 * @author Strong Liu
 * @author Steve Ebersole
 */
public interface JaxbSchemaAware {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getSchema();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setSchema(String schema);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getCatalog();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setCatalog(String catalog);
}
