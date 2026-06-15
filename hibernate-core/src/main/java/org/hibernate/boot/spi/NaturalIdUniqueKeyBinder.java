/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.spi;

import org.hibernate.mapping.Property;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface NaturalIdUniqueKeyBinder {
	/**
	 * Adds an attribute binding.  The attribute is a (top-level) part of the natural-id
	 *
	 * @param attributeBinding The attribute binding that is part of the natural-id
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void addAttributeBinding(Property attributeBinding);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void process();
}
