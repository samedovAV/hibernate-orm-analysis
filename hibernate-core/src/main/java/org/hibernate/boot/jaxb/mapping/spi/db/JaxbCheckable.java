/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.mapping.spi.db;

import java.util.List;

import org.hibernate.boot.jaxb.mapping.spi.JaxbCheckConstraintImpl;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface JaxbCheckable {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JaxbCheckConstraintImpl> getCheckConstraints();
}
