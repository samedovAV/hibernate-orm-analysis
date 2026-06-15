/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.mapping.spi;

import org.hibernate.annotations.NotFoundAction;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface JaxbNotFoundCapable extends JaxbPersistentAttribute {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NotFoundAction getNotFound();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setNotFound(NotFoundAction value);
}
