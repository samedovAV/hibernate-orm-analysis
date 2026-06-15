/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.mapping.spi.db;

import java.util.List;

import org.hibernate.boot.jaxb.mapping.spi.JaxbIndexImpl;
import org.hibernate.boot.jaxb.mapping.spi.JaxbSchemaAware;
import org.hibernate.boot.jaxb.mapping.spi.JaxbUniqueConstraintImpl;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface JaxbTableMapping extends JaxbSchemaAware, JaxbCheckable, JaxbDatabaseObject {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getComment();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getOptions();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JaxbIndexImpl> getIndexes();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JaxbUniqueConstraintImpl> getUniqueConstraints();
}
