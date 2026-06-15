/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * @author Steve Ebersole
 * @author Gail Badner
 */
public interface PluralAttributeElementSource {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	PluralAttributeElementNature getNature();
}
